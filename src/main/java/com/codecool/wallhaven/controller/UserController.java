package com.codecool.wallhaven.controller;

import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.UserRepository;
import com.codecool.wallhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @GetMapping("/data/{id}")
    public User getUserDataById(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id)).get();
    }
    @GetMapping("/friend/{id}")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id));
    }



    @GetMapping("/username/{email}")
    public String getUsernameByEmail(@PathVariable("email") String email) {
        return userRepository.findByEmail(email).get().getName();
    }


    @GetMapping("/available/email/{email}")
    public boolean isEmailAvailable(@PathVariable("email") String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    @GetMapping("/available/name/{name}")
    public boolean isNameAvailable(@PathVariable("name") String name) {
        Optional<User> username = userRepository.findByName(name);
        return username.isPresent();
    }

    @PostMapping("/update/name/{name}/{id}")
    public String updateName(@PathVariable("name") String name, @PathVariable("id") String id) {
        Optional<User> username = userRepository.findByName(name);
        if (username.isEmpty()) {
            Optional<User> user = userRepository.findById(Long.parseLong(id));
            if (user.isPresent()){
                user.get().setName(name);
                userRepository.save(user.get());
            }
        }
        return "update was successful";
    }

    @PostMapping("/update/email/{email}/{id}")
    public String updateEmail(@PathVariable("email")String email, @PathVariable("id") String id) {
        Optional<User> user1 = userRepository.findByEmail(email);
        if (user1.isEmpty()) {
            Optional<User> user = userRepository.findById(Long.parseLong(id));
            if (user.isPresent()){
                user.get().setEmail(email);
                userRepository.save(user.get());
            }
        }
        return "update was successful";
    }


    @GetMapping("/login/{email}/{password}")
    public boolean login1(@PathVariable("email") String email, @PathVariable("password") String password){
        return userRepository.findByEmailAndAndPassword(email, password).isPresent();
    }

    @GetMapping("/login/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userRepository.findByEmail(email).get();
    }

}
