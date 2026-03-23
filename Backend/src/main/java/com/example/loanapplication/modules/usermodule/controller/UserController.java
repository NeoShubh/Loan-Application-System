package com.example.loanapplication.modules.usermodule.controller;

import com.example.loanapplication.modules.usermodule.dto.UserRequestDTO;
import com.example.loanapplication.modules.usermodule.dto.UserResponseDTO;
import com.example.loanapplication.modules.usermodule.entity.User;
import com.example.loanapplication.modules.usermodule.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<UserResponseDTO> getUserByEmailID ( @RequestParam String email){
        UserResponseDTO userResponseDTO = userService.getUserByEmailID(email);
        return ResponseEntity.ok(userResponseDTO);
    }


    @GetMapping("/{userID}")
    ResponseEntity<UserResponseDTO> getUserByUserID ( @PathVariable UUID userID){
        UserResponseDTO userResponseDTO = userService.getUserByUserID(userID);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{userID}")
    ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userID,@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUser(userID,userRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping
    ResponseEntity<?> deleteUserByEmailID(@RequestParam String email){
        userService.deleteUserByEmailID(email);
        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok("User deleted successfully.");
    }

    @DeleteMapping("/{userID}")
    ResponseEntity<?> deleteUserByUserID(@PathVariable UUID userID){
        userService.deleteUserByUserID(userID);
        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok("User deleted successfully.");
    }
}