package com.bikeRental.bikeRental.pojo;

public class BikeCart extends Bike {

    private int cartQuantity;
    private double cartSum;

    public int getCartQuantity() {return cartQuantity;}

    public void setCartQuantity(int cartQuantity) {this.cartQuantity = cartQuantity;}

    public double getCartSum() {return cartSum;}

    public void setCartSum(double cartSum) {this.cartSum = cartSum;}
}
