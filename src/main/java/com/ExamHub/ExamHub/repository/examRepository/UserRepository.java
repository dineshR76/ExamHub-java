package com.ExamHub.ExamHub.repository.examRepository;

import com.ExamHub.ExamHub.entity.examEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username,String password);
    Optional<User> findOneByUsername(String email);
}
