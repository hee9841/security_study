package com.example.security.repository;


import com.example.security.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Integer> {
    Boolean existsByUsername(String username);

    //username를 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    Member findByUsername(String userName);
}
