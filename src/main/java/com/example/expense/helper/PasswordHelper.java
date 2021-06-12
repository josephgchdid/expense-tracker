package com.example.expense.helper;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHelper {

    private  final String SALT = "581d40e6f4ec4884d2d135ea83c487207de5d4718992e3d8ba2c0cc213555ecb";
    private  final String upperCaseChars = "(.*[A-Z].*)";
    private  final String lowerCaseChars = "(.*[a-z].*)";
    private  final String numbers = "(.*[0-9].*)";
    public final int MIN_PASSWORD_LENGTH = 10;

    public PasswordHelper(){}

    public  String encrypt(String password){
        password = password.trim().toLowerCase() + SALT;
        return DigestUtils.sha256Hex(password);
    }

    public boolean isMinimumLength(String password){

        return password.length() > MIN_PASSWORD_LENGTH ;

    }

    public boolean containsOneUpper(String password){
        return password.matches(upperCaseChars);
    }

    public  boolean containsOneLower(String password){
        return  password.matches(lowerCaseChars) ;
    }

    public boolean containsOneNumber(String password){
        return password.matches(numbers);
    }
}
