package com.codecool.wallhaven.model;

public class User extends BaseModel {
    String password;
    public User(String name, String password, String email) {
        super(name);
    }
}
