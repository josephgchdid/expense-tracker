package com.example.expense.service;

import com.example.expense.dto.UserDTO;
import com.example.expense.helper.PasswordHelper;
import com.example.expense.model.ErrorMessage;
import com.example.expense.model.User;
import com.example.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordHelper passwordHelper = new PasswordHelper();

    public ResponseEntity<?> signUpUser(User user){

        try {

            String userEmail = user.getUserEmail().trim().toLowerCase();

            if(userRepository.countByUserEmail(userEmail) != 0){
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Email " + userEmail + " already exists", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            String password = user.getUserPassword();

            if(!passwordHelper.isMinimumLength(password)){
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Password must be at least " + passwordHelper.MIN_PASSWORD_LENGTH + " characters long", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            if(!passwordHelper.containsOneUpper(password)) {
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Password must be have at least one upper case letter", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            if(!passwordHelper.containsOneLower(password)) {
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Password must be have at least one lower case letter", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            if(!passwordHelper.containsOneNumber(password)) {
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Password must be have at least one  number", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            password = passwordHelper.encrypt(password);

            user.setUserName(user.getUserName().trim().toLowerCase());
            user.setUserEmail(userEmail);
            user.setUserPassword(password);



            String userId = userRepository.save(user).getUserId();

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setUserEmail(userEmail);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch (Exception e){

            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new ErrorMessage(
                            "Something went wrong with our server. could not create user", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> loginUser(String userName, String password){
        try {
            User userToFind = userRepository.findByUserEmailAndUserPassword(
                    userName.trim().toLowerCase(), passwordHelper.encrypt(password));

            if(userToFind == null){
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "Incorrect username or password", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }


            UserDTO employeeDTO = new UserDTO(
                    userToFind.getUserId(),
                    userToFind.getUserName(),
                    userToFind.getUserEmail()
            );
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new ErrorMessage(
                            "Something went wrong with our server. could not login", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> findUserById(String id) {

        try {
            User user = userRepository.findByUserId(id);

            if(user == null){
                return new ResponseEntity<>(
                        new ErrorMessage(
                                "User does not exist", HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }


            UserDTO employeeDTO = new UserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getUserEmail()
            );

            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new ErrorMessage(
                            "Something went wrong with our server. could not lookup user", HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST);
        }

    }
}
