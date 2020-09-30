package com.codecool.wallhaven.service;

import com.codecool.wallhaven.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> getFriendsById(long parseLong);

    Optional<User> getUserById(Long id);

}
