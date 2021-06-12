package com.example.expense.dto;

public class ExpenseDTO {

    private int expenseId;

    private int amount;

    private String date;

    private String description;

    private String expenseType;

    public ExpenseDTO(){}

    public ExpenseDTO(int expenseId, int amount, String date, String description, String expenseType) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.expenseType = expenseType;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
