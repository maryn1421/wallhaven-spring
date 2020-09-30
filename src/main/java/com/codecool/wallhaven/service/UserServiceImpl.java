package com.codecool.wallhaven.service;

import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public List<User> getFriendsById(long parseLong) {
        return userRepository.getFriendsById(parseLong);
    }
}
