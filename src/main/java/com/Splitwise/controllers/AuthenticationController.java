package com.Splitwise.controllers;

import com.Splitwise.dto.UserDTO;
import com.Splitwise.dto.UserResponseDTO;
import com.Splitwise.entity.User;
import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.ServiceRespVO;
import com.Splitwise.repo.UserRepo;
import com.Splitwise.security.JWTHelper;
import com.Splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user") // Base URL for your authentication APIs
public class AuthenticationController {

    @Autowired
    private UserService userService; // Service for user-related operations

    @Autowired
    private JWTHelper jwtUtil; // Utility for generating JWTs

    @Autowired
    private AuthenticationManager authenticationManager; // For authentication

    @Autowired
    private PasswordEncoder passwordEncoder; // For password encoding
    @Autowired
    UserRepo userRepo;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        // Check if the user already exists (optional, but recommended)
        if (userService.userExists(userDTO.getUserName(), userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        // Convert UserDTO to User entity
        User user = new User(userDTO);

        // Encode the password
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Save the user to the database
        userService.saveUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ServiceRespVO login(@RequestBody UserDTO request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );

        // Generate JWT token
        String jwt = jwtUtil.generateToken(request.getUserName());
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setUserName(request.getUserName());
        userResponseDTO.setId(userRepo.findByUserName(request.getUserName()).getId());
        userResponseDTO.setJwt(jwt);

        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(userResponseDTO);
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;


    }
}
