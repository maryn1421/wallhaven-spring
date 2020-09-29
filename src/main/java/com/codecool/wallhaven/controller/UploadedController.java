package com.codecool.wallhaven.controller;


import com.codecool.wallhaven.model.Favorite;
import com.codecool.wallhaven.model.UploadedWallpaper;
import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.FavoriteRepository;
import com.codecool.wallhaven.repository.UploadedWallpaperRepository;
import com.codecool.wallhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UploadedController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UploadedWallpaperRepository uploadedWallpaperRepository;


    @GetMapping("/uploaded/{id}")
    public List<UploadedWallpaper> getUploaded(@PathVariable("id") String id) {
       Optional<User> user =  userRepository.findById(Long.parseLong(id));
       if (user.isPresent()) {
            return uploadedWallpaperRepository.findAllByUserId(user.get());
       }
       else {
           return new ArrayList<>();
       }
    }

    @PostMapping("/addwallpaper/{id}")
    public String addPicture(@PathVariable("id") String id, @RequestBody String image){
        image = image.replace("\"{", "");
        image = image.replace("}", "");
        image = image.replace("{\"image\":", "");
        image = image.replace("\"", "");
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if (user.isPresent()) {
            UploadedWallpaper uploadedWallpaper = UploadedWallpaper.builder().Link(image).userId(user.get()).build();
            uploadedWallpaperRepository.save(uploadedWallpaper);
        }
        return "post was successfully";
    }

}
