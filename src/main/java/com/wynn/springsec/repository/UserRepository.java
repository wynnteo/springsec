package com.wynn.springsec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wynn.springsec.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
