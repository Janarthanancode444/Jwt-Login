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
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Transactional

    public ResponseDTO create(final UserRequestDTO userRequestDTO) {
        final User user = new User();
        user.setCreatedAt(userRequestDTO.getCreatedAt());
        user.setCreatedBy(userRequestDTO.getCreatedBy());
        user.setUpdatedAt(userRequestDTO.getUpdatedAt());
        user.setUpdatedBy(userRequestDTO.getUpdatedBy());
        user.setName(userRequestDTO.getName());
        user.setPhone(userRequestDTO.getPhone());
        user.setEmail(userRequestDTO.getEmail());
        return new ResponseDTO(Constants.SUCCESS, this.userRepository.save(user), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO retrieve() {
        return new ResponseDTO(Constants.SUCCESS, this.userRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateUser(final UserResponseDTO userResponseDTO, final String id) {
        {
            final User existingUser = this.userRepository.findById(id)
                    .orElseThrow(() -> new BadRequestServiceException("User not found"));

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
            return new ResponseDTO(Constants.SUCCESS, this.userRepository.save(existingUser), HttpStatus.OK.getReasonPhrase());
        }

    }

    @Transactional
    public ResponseDTO removeUser(final String id) {
        if (!this.userRepository.existsById(id)) {
            throw new BadRequestServiceException("User not found");
        }
        this.userRepository.deleteById(id);
        return new ResponseDTO(Constants.DELETED, id, HttpStatus.OK.getReasonPhrase());
    }

}

