package com.bikeRental.bikeRental.dao;

import com.bikeRental.bikeRental.pojo.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Integer> {
}
