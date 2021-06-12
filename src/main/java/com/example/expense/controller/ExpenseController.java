package com.example.expense.controller;

import com.example.expense.model.Expense;
import com.example.expense.request.NewExpenseRequest;
import com.example.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/type/search")
    public ResponseEntity<?> findTypeBySearch(
            @NonNull @RequestParam(name = "pattern") String pattern){
        return expenseService.searchExpenseType(pattern);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewExpense(@NonNull @RequestBody NewExpenseRequest expense){
        return expenseService.addNewExpense(
                expense.getUserId(),
                expense.getExpenseTypeId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getPaymentDate()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateExpense(@NonNull @RequestParam(name = "id") int id,@NonNull @RequestBody Expense expenseRequest){
        return  expenseService.updateExpense(id, expenseRequest);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteExpense(@NonNull @RequestParam(name = "id") int id){
        return expenseService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserExpenses(@PathVariable String id,
                                              @RequestParam(name = "sd") String startDate,
                                              @RequestParam(name = "ed") String endDate){
        return expenseService.findUserExpenses(id, startDate, endDate);
    }

    @GetMapping("/sum/{id}")
    public ResponseEntity<?> findExpensesSum(@PathVariable String id,
                                              @RequestParam(name = "sd") String startDate,
                                              @RequestParam(name = "ed") String endDate){
        return expenseService.getExpensesSum(id, startDate, endDate);
    }
}
