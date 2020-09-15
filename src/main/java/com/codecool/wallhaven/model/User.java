package com.codecool.wallhaven.model;

public class User extends BaseModel {
     private String password;
     private String email;

    public User(String name, String password, String email) {
        super(name);
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
