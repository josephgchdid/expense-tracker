package com.example.expense.helper;

//Expense summary object
public class ExpenseData {

    private int sum ;

    private int count;

    public ExpenseData(int sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public void increase(int sum){
        this.sum += sum;
        this.count++;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
