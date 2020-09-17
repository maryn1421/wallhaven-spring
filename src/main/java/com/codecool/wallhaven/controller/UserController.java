package com.codecool.wallhaven.controller;

import com.codecool.wallhaven.Dao.DatabaseManager;
import com.codecool.wallhaven.model.User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    DatabaseManager dbManager;


    @GetMapping("/login/{email}/{password}")
    public boolean isLoginValid(@PathVariable("email") String email, @PathVariable("password") String password) {
        setupDbManager();
        System.out.println(email);
        System.out.println(password);
        return dbManager.checkLogin(email, password);

    }

    @GetMapping("/users/{id}")
    public List<User> getAllUser(@PathVariable("id") String id) {
        setupDbManager();
        int userId = Integer.parseInt(id);
        List<User> friends = dbManager.getFriendsById(userId);
        System.out.println(friends);
        List<User> allUser = dbManager.getAllUser();
        System.out.println(allUser);
        List<User> returnUsers = new ArrayList<>();
        //User activeUser = dbManager.getUserById(userId);
        /*
        allUser.forEach(user -> {
            if (user.getId() == Integer.parseInt(id)) {
                allUser.remove(user);
            }
        });
        friends.forEach(friend -> {
            int actualId = friend.getId();
            allUser.forEach(user -> {
                if (user.getId() == actualId) {
                    allUser.remove(user);
                }
            });

        });
                 */

        List<Integer> friendIds = new ArrayList<>();
        friends.forEach(friend -> {
            friendIds.add(friend.getId());
        });
        allUser.forEach(user -> {
            if (!friendIds.contains(user.getId()) && user.getId() != userId) {
                returnUsers.add(user);
            }
        });

        return returnUsers;
    }


    @GetMapping("/friendlist")
    public List<User> getFriendList() {
        setupDbManager();
        return dbManager.getFriendsList();
    }

    @PostMapping("/addFriend/{userId}/{friendId}")
    public void addFriend(@PathVariable String userId, @PathVariable String friendId) {
        System.out.println("ui:  " + userId);
        System.out.println("fi:  " + friendId);
        setupDbManager();
        //List<User> friends = dbManager.getFriendsById(Integer.parseInt(userId));
        dbManager.addFriend(Integer.parseInt(userId), Integer.parseInt(friendId));
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        setupDbManager();
        dbManager.addUser(user);
        return "user";
    }

    @GetMapping("/id/{email}")
    public String getIdByEmail(@PathVariable("email") String email) {
        setupDbManager();
        System.out.println(email);
        return dbManager.getIdByEmail(email);
    }

    @GetMapping("/username/{email}")
    public String getUsernameByEmail(@PathVariable("email") String email) {
        setupDbManager();
        return dbManager.getUsernameByEmail(email);
    }

    private void setupDbManager() {
        dbManager = new DatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }


    @GetMapping("/favorite/{id}/{wallpaper_id}")
    public boolean isWallpaperFavorite(@PathVariable("id") int id, @PathVariable("wallpaper_id") String wallpaperId) {
        setupDbManager();
        return dbManager.isWallpaperFavorite(id, wallpaperId);
    }

    @PostMapping("/addfavorite/{id}/{wallpaper_id}")
    public String addToFavorite(@PathVariable("id") int id, @PathVariable("wallpaper_id") String wallpaperId) {
        setupDbManager();
        dbManager.addFavorite(id, wallpaperId);
        return "asd";
    }
}
