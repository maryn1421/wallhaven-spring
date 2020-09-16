package com.codecool.wallhaven.Dao;

import com.codecool.wallhaven.model.User;

import java.util.List;

public interface UserDao {
    void addUser( User user);
    void addFriend(int userId, int friendId);
    User getUSer();
    void deleteUser(User user);
    void updateUser(User uSer);

    List<User> getAllUser();
    List<User> getFriendsById(int userId);

    boolean checkLogin(String email, String password);

    User getUserById(int id);
    String getIdByEmail(String email);
}