package com.codecool.wallhaven.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text")
    private String name;


    @Column(columnDefinition = "text")
    private String password;

    @Column(columnDefinition = "text")
    private String email;


    @ElementCollection
    private List<Long> friends;


    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

}