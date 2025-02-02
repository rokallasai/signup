package com.example.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;
import java.util.List;


@Repository
public interface userRepository extends JpaRepository<User,Integer> {

	Optional<User> findByEmail(String email);
	
	Optional<User>  findByPasswordResetKey(String passwordResetKey);
}
