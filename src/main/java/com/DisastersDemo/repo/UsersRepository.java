package com.DisastersDemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DisastersDemo.entity.User;

public interface UsersRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
