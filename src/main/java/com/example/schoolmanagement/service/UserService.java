package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.AuthRequest;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserRequestDTO;
import com.example.schoolmanagement.dto.UserResponseDTO;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, AuthenticationService authenticationService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final UserRequestDTO userRequestDTO) {
        this.validateEmail(userRequestDTO);
        this.validatePhone(userRequestDTO);
        final User user = User.builder().createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).name(userRequestDTO.getName()).phone(userRequestDTO.getPhone()).email(userRequestDTO.getEmail()).password(passwordEncoder.encode(userRequestDTO.getPassword())).roles(userRequestDTO.getRoles()).build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userRepository.save(user)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    private void validateEmail(final UserRequestDTO userRequestDTO) {
        if (UtilService.emailValidation(userRequestDTO.getEmail())) {
            throw new BadRequestServiceException(Constants.EMAIL_PATTERN);
        }
        final List<User> emailFound = this.userRepository.findByEmail(userRequestDTO.getEmail());
        if (!emailFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.EMAIL);
        }
    }

    private void validatePhone(final UserRequestDTO userRequestDTO) {
        if (UtilService.phoneNumberValidation(userRequestDTO.getPhone())) {
            throw new BadRequestServiceException(Constants.PHONE_PATTERN);
        }
        final List<User> phoneFound = this.userRepository.findByPhone(userRequestDTO.getPhone());
        if (!phoneFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.PHONE);
        }
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.userRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateUser(final UserResponseDTO userResponseDTO, final String id) {
        {
            final User existingUser = this.userRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.User));
            if (userResponseDTO.getName() != null) {
                existingUser.setName(userResponseDTO.getName());
            }
            if (userResponseDTO.getEmail() != null) {
                existingUser.setEmail(userResponseDTO.getEmail());
            }
            if (userResponseDTO.getCreatedBy() != null) {
                existingUser.setCreatedBy(userResponseDTO.getCreatedBy());
            }
            if (userResponseDTO.getUpdatedBy() != null) {
                existingUser.setUpdatedBy(userResponseDTO.getUpdatedBy());
            }
            if (userResponseDTO.getPassword() != null) {
                existingUser.setPassword(userResponseDTO.getPassword());
            }
            if (userResponseDTO.getRoles() != null) {
                existingUser.setRoles(userResponseDTO.getRoles());
            }
            return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userRepository.save(existingUser)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
        }

    }

    @Transactional
    public ResponseDTO removeUser(final String id) {
        if (!this.userRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.userRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO login(final AuthRequest authRequest) {
        final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException(Constants.NOT_FOUND);
        }
    }
}

