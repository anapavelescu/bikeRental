package com.bikeRental.bikeRental.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.util.HashMap;

@SessionScope
@Component
public class UserSession {
    private int id;
    private HashMap<Integer, Integer> rentalCart = new HashMap<>();

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public HashMap<Integer, Integer> getRentalCart() {
        return rentalCart;
    }
    public void setRentalCart(HashMap<Integer, Integer> rentalCart) {
        this.rentalCart = rentalCart;
    }

    public void addToCart(int id){
        if(rentalCart.get(id) != null){
            int currentQuantity = rentalCart.get(id);
            int newQuantity = currentQuantity + 1;
            rentalCart.put(id, newQuantity);
        }
        else{
            rentalCart.put(id, 1);
        }


    }
}
