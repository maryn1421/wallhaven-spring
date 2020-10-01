package com.codecool.wallhaven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@Builder
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


}