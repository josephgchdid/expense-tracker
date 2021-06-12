package com.example.expense.service;

import com.example.expense.dto.DataDTO;
import com.example.expense.dto.ExpenseDTO;
import com.example.expense.helper.DateHelper;
import com.example.expense.helper.ExpenseData;
import com.example.expense.model.ErrorMessage;
import com.example.expense.model.Expense;
import com.example.expense.model.ExpenseType;
import com.example.expense.model.User;
import com.example.expense.repository.ExpenseRepository;
import com.example.expense.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public ResponseEntity<?> searchExpenseType(String pattern){

        try {
            List<DataDTO> returnList = new ArrayList<>();
            List<ExpenseType> resultList = expenseTypeRepository.findByExpenseType(pattern);

            for(ExpenseType type : resultList){
                returnList.add(new DataDTO(type.getExpenseTypeId(), type.getExpenseType()));
            }

            return new ResponseEntity<>(returnList, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new ErrorMessage("could not search list of types", HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ResponseEntity<?> addNewExpense(String userId, int expenseTypeId, int amount, String description, String paymentDate){

        try{

            if(amount <= 0) {
                return new ResponseEntity<>(
                        new ErrorMessage("Amount cannot be less than or equal to zero", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST
                );
            }

            Expense expense = new Expense();

            User user = new User();
            user.setUserId(userId);

            ExpenseType expenseType = new ExpenseType();

            expenseType.setExpenseTypeId(expenseTypeId);

            expense.setUser(user);

            expense.setExpenseType(expenseType);

            expense.setDescription(description);

            expense.setAmount(amount);

            expense.setPaymentDate(DateHelper.stringToDate(paymentDate));

            int expenseId = expenseRepository.save(expense).getExpenseId();

            return new ResponseEntity<>(new DataDTO(expenseId, "created new expense"), HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new ErrorMessage("could not add new expense", HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ResponseEntity<?> findUserExpenses(String userId, String startDate, String endDate){

        try{

            Date dateStart = DateHelper.stringToDate(startDate);
            Date dateEnd = DateHelper.stringToDate(endDate);

            if(dateStart.after(dateEnd)){
                return new ResponseEntity<>(
                        new ErrorMessage("Start date cannot be after end date", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST
                );
            }

            List<ExpenseDTO> returnList = new ArrayList<>();
            List<Expense> resultList = expenseRepository.getAllBetweenDates(userId,dateStart , dateEnd );

            for(Expense expense : resultList){

                returnList.add(new ExpenseDTO(
                        expense.getExpenseId(),
                        expense.getAmount(),
                        DateHelper.dateToString(expense.getPaymentDate()),
                        expense.getDescription(),
                        expense.getExpenseType().getExpenseType()
                ));
            }
            return new ResponseEntity<>(returnList, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());

            return new ResponseEntity<>(
                    new ErrorMessage("could not find expenses for user", HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateExpense(int recordId, Expense expense){

        try{
            Optional<Expense> expenseToFind = expenseRepository.findById(recordId);

            if(expenseToFind.isEmpty()){
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Could not find record", HttpStatus.BAD_REQUEST
                        ), HttpStatus.BAD_REQUEST);
            }

            expense.setExpenseId(expenseToFind.get().getExpenseId());

            expenseRepository.save(expense);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());

            return new ResponseEntity<>(
                    new ErrorMessage("could not update expenses for user", HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteById(int id){

        try{
            expenseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());

            return new ResponseEntity<>(
                    new ErrorMessage("could not delete expenses for user", HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getExpensesSum(String userId, String startDate, String endDate){

        try{

            Date dateStart = DateHelper.stringToDate(startDate);
            Date dateEnd = DateHelper.stringToDate(endDate);

            if(dateStart.after(dateEnd)){
                return new ResponseEntity<>(
                        new ErrorMessage("Start date cannot be after end date", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST
                );
            }

            HashMap<String, ExpenseData> expenseSum = new HashMap<>();

            List<Expense> list = expenseRepository.getAllBetweenDates(userId, dateStart, dateEnd);

            list.forEach(element -> {

                String type =  element.getExpenseType().getExpenseType();

                if(expenseSum.containsKey(type))
                    expenseSum.get(type).increase(element.getAmount()) ;
                else
                  expenseSum.put(type, new ExpenseData(element.getAmount(), 1));

            });


            return new ResponseEntity<>(expenseSum, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());

            return new ResponseEntity<>(
                    new ErrorMessage("could not calculate expenses for user", HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
