package com.codecool.wallhaven.controller;


import com.codecool.wallhaven.model.Favorite;
import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.FavoriteRepository;
import com.codecool.wallhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FavoriteController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile/favourites/{id}")
    public List<Favorite> getFavouritesByUserID(@PathVariable("id") String id) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        return user.map(value -> favoriteRepository.findAllByUserId(value)).orElse(new ArrayList<>());
    }

    @PostMapping("/addfavorite/{id}/{wallpaper_id}")
    public String addToFavorite(@PathVariable("id") String id, @PathVariable("wallpaper_id") String wallpaperId) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if (user.isPresent()) {
            Favorite favorite = Favorite.builder().userId(user.get()).wallpaperId(wallpaperId).build();
            favoriteRepository.save(favorite);
            return "process finished successfully";
        }
        return "process couldnt finish successfully";
    }


    @GetMapping("/favorite/{id}/{wallpaper_id}")
    public boolean isWallpaperFavorite(@PathVariable("id") String  id, @PathVariable("wallpaper_id") String wallpaperId) {
        Optional<User> byId = userRepository.findById(Long.parseLong(id));
        if (byId.isPresent()) {
            Optional<Favorite> favorite = favoriteRepository.findByWallpaperIdAndUserId(wallpaperId, byId.get());
            return favorite.isPresent();
        }
        else return false;
    }

}
