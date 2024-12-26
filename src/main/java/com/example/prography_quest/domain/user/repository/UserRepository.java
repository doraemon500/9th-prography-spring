package com.example.prography_quest.domain.user.repository;

import com.example.prography_quest.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByOrderById(Pageable pageable);
}