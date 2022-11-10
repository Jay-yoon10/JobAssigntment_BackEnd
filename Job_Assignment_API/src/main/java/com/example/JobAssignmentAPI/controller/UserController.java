package com.example.JobAssignmentAPI.controller;

import com.example.JobAssignmentAPI.dto.ResponseDTO;
import com.example.JobAssignmentAPI.dto.UserDTO;
import com.example.JobAssignmentAPI.model.User;
import com.example.JobAssignmentAPI.security.security.TokenProvider;
import com.example.JobAssignmentAPI.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UserDTO userDTO){
        try {
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();

            User signUpUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(signUpUser.getEmail())
                    .id(signUpUser.getId())
                    .username(signUpUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        User user = userService.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword(),passwordEncoder);

        if(user != null){
            // Generating token
            final String token = tokenProvider.create(user);

            final UserDTO responseUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }
        else
        {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
