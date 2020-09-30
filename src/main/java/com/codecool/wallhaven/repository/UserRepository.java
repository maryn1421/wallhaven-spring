package com.codecool.wallhaven.repository;

import com.codecool.wallhaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> getFriendsById(long id);

    Optional<User> getUserById(Long id);
}
