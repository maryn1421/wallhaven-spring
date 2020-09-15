package com.codecool.wallhaven.controller;

import com.codecool.wallhaven.Dao.DatabaseManager;
import com.codecool.wallhaven.model.User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    DatabaseManager dbManager;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){
        setupDbManager();
        dbManager.addUser(user);
        return "user";
    }

    private void setupDbManager() {
        dbManager = new DatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }
}
