package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.RoleRequestDTO;
import com.example.schoolmanagement.dto.RoleResponseDTO;
import com.example.schoolmanagement.entity.Role;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.RoleRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseDTO createRole(final RoleRequestDTO roleRequestDTO) {
        final User user = this.userRepository.findById(roleRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException("User not found"));
        user.setId(roleRequestDTO.getUserId());
        final Role role = new Role();
        role.setName(roleRequestDTO.getName());
        role.setDepartment(roleRequestDTO.getDepartment());
        role.setCreatedAt(roleRequestDTO.getCreatedAt());
        role.setCreatedBy(roleRequestDTO.getCreatedBy());
        role.setUpdatedAt(roleRequestDTO.getUpdatedAt());
        role.setUpdatedBy(roleRequestDTO.getUpdatedBy());
        role.setUser(user);
        return new ResponseDTO(Constants.CREATED, this.roleRepository.save(role), HttpStatus.CREATED.getReasonPhrase());


    }

    public ResponseDTO retrieve() {
        return new ResponseDTO(Constants.SUCCESS, this.roleRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final RoleResponseDTO roleResponseDTO, final String id) {
        final Role role = this.roleRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Role Id not found"));

        if (roleResponseDTO.getName() != null) {
            role.setName(roleResponseDTO.getName());
        }
        if (roleResponseDTO.getName() != null) {
            role.setName(roleResponseDTO.getName());
        }
        if (roleResponseDTO.getName() != null) {
            role.setName(roleResponseDTO.getName());
        }
        if (roleResponseDTO.getDepartment() != null) {
            role.setDepartment(roleResponseDTO.getDepartment());
        }
        if (roleResponseDTO.getCreatedBy() != null) {
            role.setCreatedBy(roleResponseDTO.getCreatedBy());
        }
        if (roleResponseDTO.getUpdatedBy() != null) {
            role.setUpdatedBy(roleResponseDTO.getUpdatedBy());
        }
        return new ResponseDTO(Constants.SUCCESS, this.roleRepository.save(role), HttpStatus.OK.getReasonPhrase());
    }


    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.roleRepository.existsById(id)) {
            throw new BadRequestServiceException("Role Id not found");
        }
        this.roleRepository.deleteById(id);
        return new ResponseDTO("Successfully deleted.", id, HttpStatus.OK.getReasonPhrase());
    }

}

