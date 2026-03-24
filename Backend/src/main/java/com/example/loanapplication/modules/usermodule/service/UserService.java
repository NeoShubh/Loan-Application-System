package com.example.loanapplication.modules.usermodule.service;

import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;
import com.example.loanapplication.modules.usermodule.entity.User;

import java.util.UUID;

public interface UserService {

    UserResponseDTO getUserByEmailID(String emailID);
    UserResponseDTO getUserByUserID(String userID);
    UserResponseDTO updateUser(String userID, UserRequestDTO userRequestDTO);
    void deleteUserByEmailID(String emailID);
    void deleteUserByUserID(String userID);
    boolean isUserAvailable(String userID);
}
