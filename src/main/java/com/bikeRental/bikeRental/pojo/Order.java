package com.bikeRental.bikeRental.pojo;

import com.bikeRental.bikeRental.pojo.OrderLines;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="order_rental")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="order_id")

    private List<OrderLines> orderLines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderLines> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLines> orderLines) {
        this.orderLines = orderLines;
    }
}
