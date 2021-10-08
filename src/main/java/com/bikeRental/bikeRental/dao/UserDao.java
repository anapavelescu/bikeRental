package com.bikeRental.bikeRental.dao;

import com.bikeRental.bikeRental.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    List<User> findByEmail(String email);
}
