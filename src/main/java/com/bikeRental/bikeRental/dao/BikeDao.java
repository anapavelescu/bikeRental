package com.bikeRental.bikeRental.dao;

import com.bikeRental.bikeRental.pojo.Bike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeDao extends CrudRepository<Bike, Integer> {
}
