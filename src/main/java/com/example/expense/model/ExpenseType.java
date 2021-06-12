package com.example.expense.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "expense_type")
public class ExpenseType {

    public final static String idField = "expense_type_id";

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "exp_type_seq")
    @SequenceGenerator(name = "exp_type_seq", allocationSize = 1)
    private int expenseTypeId;

    @Column(name = "expense_type")
    private String expenseType;

    @JsonManagedReference(value = idField)
    @OneToMany(mappedBy = "expenseType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Expense> userExpense;

    public ExpenseType(){}


    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public Set<Expense> getUserExpense() {
        return userExpense;
    }

    public void setUserExpense(Set<Expense> userExpense) {
        this.userExpense = userExpense;
    }


}
