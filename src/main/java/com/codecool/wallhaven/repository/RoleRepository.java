package com.codecool.wallhaven.repository;

import com.codecool.wallhaven.model.ERole;
import com.codecool.wallhaven.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

