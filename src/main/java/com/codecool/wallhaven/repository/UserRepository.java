package com.codecool.wallhaven.repository;

import com.codecool.wallhaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    List<User> getFriendsById(long id);

    Optional<User> findByEmailAndAndPassword(String email, String password);

    Optional<User> getUserById(Long id);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}
