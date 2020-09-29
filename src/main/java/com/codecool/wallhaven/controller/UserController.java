package com.codecool.wallhaven.controller;

import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.UserRepository;
import com.codecool.wallhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//nb
//separate
//debugger, use logger
//calletupdb in constructor
//use di
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @GetMapping("/users/{id}")
    public List<User> getAvailableUsers(@PathVariable("id") String id) {
        User byId = userRepository.findById(Long.parseLong(id)).get();
        List<Long> friendIds = byId.getFriends();
        List<User> friends = new ArrayList<>();

        friendIds.forEach(id1 -> {
            Optional<User> fri = userRepository.findById(id1);
            fri.ifPresent(friends::add);
        });


        List<User> allUser = userRepository.findAll();
        List<User> suggested = new ArrayList<>();
        allUser.forEach(user -> {
            if (!friends.contains(user) && Long.parseLong(id) != user.getId()) {
                suggested.add(user);
            }
        });
        return suggested;

    }


    @PostMapping("/test")
    public String test(@RequestBody User user) {
        userRepository.save(user);
        return "halika";
    }

    @PostMapping("/addFriend/{userId}/{friendId}")
    public void addFriend(@PathVariable String userId, @PathVariable String friendId) {
        System.out.println(userId);
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        Optional<User> friend = userRepository.findById(Long.parseLong(friendId));

        if (user.isPresent() && friend.isPresent()) {
            user.get().getFriends().add(friend.get().getId());
            userRepository.save(user.get());
        }
    }

    @GetMapping("/test1")
    public String test1() {
        Optional<User> user = userRepository.findById(Long.parseLong("11"));
        user.ifPresent(value -> System.out.println(value.getFriends()));
        return "fine";
    }

    @GetMapping("/test2")
    public String test2() {
        Optional<User> user = userRepository.findById(Long.parseLong("11"));
        Optional<User> user2 = userRepository.findById(Long.parseLong("8"));
     //   user.ifPresent(value -> value.getFriends().add(user2.get()));
        return "fine";
    }

    @GetMapping("/login/{email}/{password}")
    public boolean isLoginValid(@PathVariable("email") String email, @PathVariable("password") String password) {
        User user = userService.findByUsername(email);
        return user.getPassword().equals(password);
    }



    @GetMapping("/friends/{id}")
    public List<User> getFriends(@PathVariable("id") String id) {
        Optional<User> byId = userRepository.findById(Long.parseLong(id));
        if (byId.isPresent()) {
            List<Long> friendIds = byId.get().getFriends();
            List<User> friends = new ArrayList<>();

            friendIds.forEach(id1 -> {
                Optional<User> fri = userRepository.findById(id1);
                fri.ifPresent(friends::add);
            });
           return friends;
        } else {
            return new ArrayList<>();
        }
    }



    @GetMapping("/alluser")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "user";

    }

    @GetMapping("/profile/{id}")
    public List<User> getFriendsById(@PathVariable("id") String id) {
        return userService.getFriendsById(Long.parseLong(id));
    }

    @GetMapping("/friend/{id}")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id));
    }

/*


    @GetMapping("/username/{email}")
    public String getUsernameByEmail(@PathVariable("email") String email) {
        return userRepository.findByEmail(email).getName();}



    }

         */

    /*
    @GetMapping("/id/{email}")
    public String getIdByEmail(@PathVariable("email") String email) {
        setupDbManager();
        return dbManager.getIdByEmail(email);
    }














    */
}
