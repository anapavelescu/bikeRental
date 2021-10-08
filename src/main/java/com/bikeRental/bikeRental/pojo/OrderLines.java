package com.bikeRental.bikeRental.pojo;

import javax.persistence.*;

@Entity
@Table
public class OrderLines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    private int bikeId;
    private int quantity;
    private double price;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Order getOrder() {return order;}
    public void setOrder(Order order) {this.order = order;}

    public int getBikeId() {return bikeId;}
    public void setBikeId(int carId) {this.bikeId = carId;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
}
