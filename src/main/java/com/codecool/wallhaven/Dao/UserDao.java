package com.codecool.wallhaven.Dao;

import com.codecool.wallhaven.model.User;

import java.util.List;

public interface UserDao {
    void addUser( User user);
    User getUSer();
    void deleteUser(User user);
    void updateUser(User uSer);

    List<User> getAllUser();


    boolean checkLogin(String email, String password);
}