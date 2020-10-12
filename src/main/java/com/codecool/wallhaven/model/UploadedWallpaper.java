package com.codecool.wallhaven.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uploaded_wallpaper")
public class UploadedWallpaper {



    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    private String Link;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User userId;
}
