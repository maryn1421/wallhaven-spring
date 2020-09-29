package com.codecool.wallhaven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.Size;
import javax.persistence.*;
import java.util.ArrayList;
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