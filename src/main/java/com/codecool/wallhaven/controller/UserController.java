package com.codecool.wallhaven.controller;

import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.UserRepository;
import com.codecool.wallhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String test(@RequestBody User user) {
        userRepository.save(user);
        return "halika";
    }

    @PostMapping("/addFriend/{userId}/{friendId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addFriend(@PathVariable String userId, @PathVariable String friendId) {
        System.out.println(userId);
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        Optional<User> friend = userRepository.findById(Long.parseLong(friendId));

        if (user.isPresent() && friend.isPresent()) {
            user.get().getFriends().add(friend.get().getId());
            userRepository.save(user.get());
        }
    }

    @PostMapping("/removeFriend/{userId}/{friendId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void removeFriend(@PathVariable String userId, @PathVariable String friendId) {
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        Optional<User> friend = userRepository.findById(Long.parseLong(friendId));

        if(user.isPresent() && friend.isPresent()) {
            user.get().getFriends().remove(friend.get().getId());
            userRepository.save(user.get());
        }
    }

    @GetMapping("/friends/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "user";

    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getFriendsById(@PathVariable("id") String id) {
        return userService.getFriendsById(Long.parseLong(id));
    }

    @GetMapping("/data/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User getUserDataById(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id)).get();
    }
    @GetMapping("/friend/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id));
    }



    @GetMapping("/username/{email}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String getUsernameByEmail(@PathVariable("email") String email) {
        return userRepository.findByEmail(email).get().getName();
    }


    @GetMapping("/available/email/{email}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public boolean isEmailAvailable(@PathVariable("email") String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    @GetMapping("/available/name/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public boolean isNameAvailable(@PathVariable("name") String name) {
        Optional<User> username = userRepository.findByName(name);
        return username.isPresent();
    }

    @PostMapping("/update/name/{name}/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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

    @PostMapping("/update/password/{newPassword}/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String updatePassword(@PathVariable String newPassword, @PathVariable String id) {
        Optional<User> user1 = userRepository.findById(Long.parseLong(id));
            if (user1.isPresent()){
                user1.get().setPassword(encoder.encode(newPassword));
                userRepository.save(user1.get());
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
