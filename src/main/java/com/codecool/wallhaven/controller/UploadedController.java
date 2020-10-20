package com.codecool.wallhaven.controller;


import com.codecool.wallhaven.model.Favorite;
import com.codecool.wallhaven.model.UploadedWallpaper;
import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.FavoriteRepository;
import com.codecool.wallhaven.repository.UploadedWallpaperRepository;
import com.codecool.wallhaven.repository.UserRepository;
import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UploadedController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UploadedWallpaperRepository uploadedWallpaperRepository;


    @GetMapping("/uploaded/{id}")
    public List<UploadedWallpaper> getUploaded(@PathVariable("id") String id) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if (user.isPresent()) {
            return uploadedWallpaperRepository.findAllByUserId(user.get());
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<byte[]> getImage1(@PathVariable String name) throws IOException {
        File img = new File("src/main/resources/images/" + name);
        if (!img.exists()) {
            img = new File("src/main/resources/images/notfound.jpg");
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadwallpaper/{id}")
    public String uploadWallpaper(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        String absolutePathDirectory = "src/main/resources/images/";

        File absolutePathFile = new File(absolutePathDirectory);
        String absolutePath = absolutePathFile.getAbsolutePath();

        if (!file.isEmpty()) {
            try {
                String filePath = absolutePath + "/" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");

                File dest = new File(filePath);
                file.transferTo(dest.toPath());

                Optional<User> user = userRepository.findById(Long.parseLong(id));
                if (user.isPresent()) {
                    UploadedWallpaper uploadedWallpaper = UploadedWallpaper.builder().Link(file.getOriginalFilename().replace(" ", "_")).userId(user.get()).build();
                    uploadedWallpaperRepository.save(uploadedWallpaper);
                }
                return "success";
            } catch (Exception ex) {
                return "Exception error";
            }
        } else {
            return "Error";
        }
    }


}
