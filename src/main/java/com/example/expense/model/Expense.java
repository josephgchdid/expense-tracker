package com.example.expense.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "expense")
public class Expense {

    public final static String idField = "expense_id";

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "exp_seq")
    @SequenceGenerator(name = "exp_seq", allocationSize = 1)
    private int expenseId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "description")
    private String description;

    @JsonBackReference(value = User.idField)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = User.idField)
    private User user;

    @JsonBackReference(value = ExpenseType.idField)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ExpenseType.idField)
    private ExpenseType expenseType;

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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", expenseType=" + expenseType +
                '}';
    }
}
