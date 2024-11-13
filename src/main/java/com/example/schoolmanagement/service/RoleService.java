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
        final User user = this.userRepository.findById(roleRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(roleRequestDTO.getUserId());
        final Role role = Role.builder().name(roleRequestDTO.getName()).department(roleRequestDTO.getDepartment()).createdBy(roleRequestDTO.getCreatedBy()).updatedBy(roleRequestDTO.getUpdatedBy()).user(user).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.roleRepository.save(role)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.roleRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final RoleResponseDTO roleResponseDTO, final String id) {
        final Role role = this.roleRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));

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
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.roleRepository.save(role)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.roleRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.IDDOESNOTEXIST);
        }
        this.roleRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}

