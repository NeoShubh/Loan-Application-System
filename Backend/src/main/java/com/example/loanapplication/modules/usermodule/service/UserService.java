package com.example.loanapplication.modules.usermodule.service;

import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;
import com.example.loanapplication.modules.usermodule.entity.User;

import java.util.UUID;

public interface UserService {

    UserResponseDTO getUserByEmailID(String emailID);
    UserResponseDTO getUserByUserID(UUID userID);
    UserResponseDTO updateUser(UUID userID, UserRequestDTO userRequestDTO);
    void deleteUserByEmailID(String emailID);
    void deleteUserByUserID(UUID userID);
}
