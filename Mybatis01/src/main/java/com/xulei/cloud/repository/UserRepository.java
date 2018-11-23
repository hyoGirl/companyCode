package com.xulei.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xulei.cloud.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    Long deleteById(Long id);
}