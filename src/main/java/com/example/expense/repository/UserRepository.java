package com.example.expense.repository;

import com.example.expense.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(String id);

    User findByUserEmailAndUserPassword(String email, String password);

    @Query("SELECT COUNT(u) FROM User u WHERE u.userEmail=:name")
    int countByUserEmail(@Param("name") String email);
}
