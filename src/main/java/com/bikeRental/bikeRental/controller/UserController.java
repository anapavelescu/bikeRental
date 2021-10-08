package com.bikeRental.bikeRental.controller;

import com.bikeRental.bikeRental.dao.*;
import com.bikeRental.bikeRental.exceptions.UserException;
import com.bikeRental.bikeRental.pojo.*;
import com.bikeRental.bikeRental.security.UserSession;
import com.bikeRental.bikeRental.service.RentalService;
import com.bikeRental.bikeRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserSession userSession;

    @Autowired
    RentalService rentalService;

    @Autowired
    OrderDao orderDao;

    List<Bike> bikeList;

    @GetMapping("/register-form")
    public ModelAndView registerAction(@RequestParam("email") String email,
                                       @RequestParam ("password") String password,
                                       @RequestParam ("password-again") String password2){
        ModelAndView modelAndView = new ModelAndView("register");
        List<User> users = userService.getUserByEmail(email);
        if(users.size() > 0){
            modelAndView.addObject("message", "This email address is already registered.");
            return modelAndView;
        }
        try{
            userService.checkPassword(password, password2);
            userService.save(email, password);
            return  new ModelAndView("redirect:index.html");
        }
        catch (UserException e){
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping ("/register")
    public ModelAndView register (){
        return  new ModelAndView("register");
    }


    @GetMapping ("/login")
    public ModelAndView login (@RequestParam ("email") String email,
                               @RequestParam ("password") String password){
        ModelAndView modelAndView=new ModelAndView("index");
        List<User> userList = userService.getUserByEmail(email);
        if(userList.size() == 0){
            modelAndView.addObject("message", "The username or password you have entered is invalid. Please try again.");
        }
        if(userList.size() > 1){
            modelAndView.addObject("message", "The username or password you have entered is invalid. Please try again.");
        }
        if(userList.size() == 1){
            User userFromDatabase = userList.get(0);
            if(!userFromDatabase.getPassword().equals(password)){
                modelAndView.addObject("message", "The username or password you have entered is invalid. Please try again.");
            } else if(userFromDatabase.getEmail().equals("ana@test.com"))  {
                modelAndView = new ModelAndView("redirect:/admin/bikes");
            } else {
                userSession.setId(userFromDatabase.getId());
                modelAndView = new ModelAndView("redirect:/dashboard");
            }
        }

        return modelAndView;
    }

    @GetMapping ("/dashboard")
    public ModelAndView dashboard (){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        int id = userSession.getId();
        if(id==0){
            return  new ModelAndView("redirect:/index.html");
        }
        int itemsCart = 0;
        for(int quantity : userSession.getRentalCart().values()){
            itemsCart += quantity;
        }
        bikeList = rentalService.getAllBikes();
        modelAndView.addObject("bikeList", bikeList);
        modelAndView.addObject("items", itemsCart);
        return modelAndView;
    }

    @PostMapping("/addToCart")
    public ModelAndView addToCart (@RequestParam ("bikeId") Integer id){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        int itemsCart = 0;
        userSession.addToCart(id);
        for (int quantity : userSession.getRentalCart().values()){
            itemsCart+=quantity;
        }
        modelAndView.addObject("bikeList", bikeList);
        modelAndView.addObject("items", itemsCart);
        return  modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        userSession.setId(0);
        return new ModelAndView("redirect:index.html");
    }

    //@RequestParam ("bikeId") Integer id,@RequestParam ("time") Integer time

    @GetMapping ("/show-cart")
    public ModelAndView showCart() {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<BikeCart> bikeCarts = new ArrayList<>();
        double totalCartSum = 0;

        for (Map.Entry<Integer, Integer> entry : userSession.getRentalCart().entrySet()) {
            int bikeId = entry.getKey();
            int quantity = entry.getValue();
            Bike bike = rentalService.findById(bikeId);

            BikeCart bikeCart = new BikeCart();
            bikeCart.setId(bikeId);
            bikeCart.setName(bike.getName());
            bikeCart.setCartQuantity(quantity);
            bikeCart.setCartSum(quantity * bike.getPrice());

            totalCartSum += bikeCart.getCartSum();
            bikeCarts.add(bikeCart);
        }

        modelAndView.addObject("cartList", bikeCarts);
        modelAndView.addObject("totalSum", totalCartSum);
        return  modelAndView;
    }

    @GetMapping("/buy-cart")
    public ModelAndView modelAndView(){
        ModelAndView modelAndView=new ModelAndView("order");
        List<OrderLines> orderLines=new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry:userSession.getRentalCart().entrySet()){
            int bikeId = entry.getKey();
            int quantity = entry.getValue();
            Bike bike = rentalService.findById(bikeId);

            OrderLines orderLines1 = new OrderLines();
            orderLines1.setBikeId(bikeId);
            orderLines1.setQuantity(quantity);
            orderLines1.setPrice(bike.getPrice()*quantity);
            orderLines.add(orderLines1);
        }
        Order order = new Order();
        order.setUserId(userSession.getId());
        order.setOrderLines(orderLines);
        orderDao.save(order);
        userSession.getRentalCart().clear();
        return modelAndView;
    }
}
