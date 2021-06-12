package com.example.expense.repository;

import com.example.expense.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer> {


    @Query("SELECT t from ExpenseType t WHERE t.expenseType LIKE :pattern% ORDER BY t.expenseType ASC")
    List<ExpenseType> findByExpenseType(@Param("pattern") String pattern);
}
