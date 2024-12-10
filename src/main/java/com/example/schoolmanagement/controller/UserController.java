package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.AuthRequest;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserRequestDTO;
import com.example.schoolmanagement.dto.UserResponseDTO;
import com.example.schoolmanagement.service.JwtService;
import com.example.schoolmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final UserRequestDTO userRequestDTO) {
        return this.userService.create(userRequestDTO);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieve() {
        return this.userService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateUser(@RequestBody final UserResponseDTO userResponseDTO, @PathVariable final String id) {
        return this.userService.updateUser(userResponseDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.userService.removeUser(id);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody final AuthRequest authRequest) {
        return this.userService.login(authRequest);
    }
}
