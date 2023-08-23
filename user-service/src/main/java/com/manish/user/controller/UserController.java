package com.manish.user.controller;

import com.manish.user.dto.TokenResponseDTO;
import com.manish.user.dto.UserLoginRequestDTO;
import com.manish.user.dto.UserRegisterRequestDTO;
import com.manish.user.dto.UserUpdateRequestDTO;
import com.manish.user.entity.User;
import com.manish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/add")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequestDTO requestDTO) {
        log.info("|| registerUser is called in UserController class ||");
        return userService.registerUser(requestDTO);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody UserLoginRequestDTO requestDTO) {
        log.info("|| loginUser is called in UserController class ||");
        return userService.loginUser(requestDTO);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NORMAL')")
    public ResponseEntity<User> getUserByUserId(@RequestParam String userId) {
        log.info("|| getUser is called in UserController class ||");
        return userService.getUserByUserId(userId);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('NORMAL')")
    public ResponseEntity<String> updateUserByUserId(@RequestParam String userId,
                                                     @RequestBody UserUpdateRequestDTO requestDTO) {
        log.info("|| updateUserByUserId is called in UserController class ||");
        return userService.updateUserByUserId(userId, requestDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('NORMAL')")
    public ResponseEntity<String> deleteUserByUserId(@RequestParam String userId){
        log.info("|| deleteUserByUserId is called in UserController class ||");
        return userService.deleteUserByUserId(userId);
    }
}
