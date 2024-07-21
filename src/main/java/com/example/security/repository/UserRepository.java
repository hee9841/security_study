package com.example.security.repository;


import com.example.security.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Integer> {
    Boolean existsByUsername(String username);
}
