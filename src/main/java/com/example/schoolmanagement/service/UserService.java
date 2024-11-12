package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserRequestDTO;
import com.example.schoolmanagement.dto.UserResponseDTO;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional

    public ResponseDTO create(final UserRequestDTO userRequestDTO) {
        final User user = User.builder().build();
        user.setCreatedAt(userRequestDTO.getCreatedAt());
        user.setCreatedBy(userRequestDTO.getCreatedBy());
        user.setUpdatedAt(userRequestDTO.getUpdatedAt());
        user.setUpdatedBy(userRequestDTO.getUpdatedBy());
        user.setName(userRequestDTO.getName());
        user.setPhone(userRequestDTO.getPhone());
        user.setEmail(userRequestDTO.getEmail());
        user.setRoles(userRequestDTO.getRoles());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userRepository.save(user)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    @Transactional
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
}

