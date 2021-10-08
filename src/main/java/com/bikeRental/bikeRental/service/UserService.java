package com.bikeRental.bikeRental.service;

import com.bikeRental.bikeRental.pojo.User;
import com.bikeRental.bikeRental.dao.UserDao;
import com.bikeRental.bikeRental.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void save(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userDao.save(user);
    }

    public void checkPassword (String password, String password2) throws UserException {
        if(!password.equals(password2)){
            throw new UserException("Parolele nu corespund.");
        }
    }

    public List<User> getUserByEmail(String email){
        return  userDao.findByEmail(email);
    }
}
