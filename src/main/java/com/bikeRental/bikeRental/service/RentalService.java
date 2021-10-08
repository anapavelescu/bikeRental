package com.bikeRental.bikeRental.service;

import com.bikeRental.bikeRental.pojo.Bike;
import com.bikeRental.bikeRental.dao.BikeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    BikeDao bikeDao;

    public List<Bike> getAllBikes(){return (List<Bike>) bikeDao.findAll();}

    public String addBike (String name, Double price, Integer quantity){
        Bike bike = new Bike();
        bike.setName(name);
        bike.setPrice(price);
        bike.setQuantity(quantity);
        bikeDao.save(bike);
        return "The bicycle type " + bike.getName() + " was saved in the rental list.";
    }

    public  String removeBike(Integer id){
        bikeDao.deleteById(id);
        return "This bicycle type was deleted from the rental list.";
    }

    public Bike findById (int id){
        return  bikeDao.findById(id).get();
    }
}

