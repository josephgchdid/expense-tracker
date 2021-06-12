package com.example.expense.repository;

import com.example.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("SELECT e FROM Expense e WHERE e.user.userId=:userId")
    List<Expense> findByUser(@Param("userId") String userId);

    @Query("SELECT e FROM Expense e WHERE " +
            "e.paymentDate BETWEEN :startDate AND :endDate AND e.user.userId=:userId"
    )
    List<Expense> getAllBetweenDates( @Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
