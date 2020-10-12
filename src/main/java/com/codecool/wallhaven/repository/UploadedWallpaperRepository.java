package com.codecool.wallhaven.repository;

import com.codecool.wallhaven.model.Favorite;
import com.codecool.wallhaven.model.UploadedWallpaper;
import com.codecool.wallhaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadedWallpaperRepository extends JpaRepository<UploadedWallpaper, Long> {

    List<UploadedWallpaper> findAllByUserId(User user);
}
