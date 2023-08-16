package com.manish.user.controller;

import com.manish.user.dto.UserRegisterRequestDTO;
import com.manish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequestDTO requestDTO){
        log.info("|| registerUser is called in UserController class ||");

        return userService.registerUser(requestDTO);
    }
}
