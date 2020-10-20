package com.codecool.wallhaven.repository;

import com.codecool.wallhaven.model.Favorite;
import com.codecool.wallhaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserId(User userId);
    Optional<Favorite> findByWallpaperIdAndUserId(String wallpaperId, User userId);
}
