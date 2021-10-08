package com.bikeRental.bikeRental.controller;

import com.bikeRental.bikeRental.pojo.Bike;
import com.bikeRental.bikeRental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class AdminController {

    @Autowired
    RentalService bikeService;

    @GetMapping("/admin/bikes")
    public ModelAndView viewBikes(){
        ModelAndView modelAndView = new ModelAndView("admin/bikes");

        List<Bike> bikeList = bikeService.getAllBikes();
        modelAndView.addObject("bikes", bikeList);
        return modelAndView;
    }

    @PostMapping("/admin/bikes")
    @ResponseBody
    public String addBike(@RequestParam("name") String name,
                          @RequestParam("price") Double price,
                          @RequestParam("quantity") Integer quantity
    ){
        return bikeService.addBike(name, price, quantity);
    }

    @DeleteMapping("/admin/bikes/{id}")
    @ResponseBody
    public String removeBike(@PathVariable("id") Integer id){
        return bikeService.removeBike(id);
    }
}
