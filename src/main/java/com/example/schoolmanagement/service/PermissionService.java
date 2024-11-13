package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.PermissionRequestDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.entity.Permission;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.PermissionRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    public PermissionService(PermissionRepository permissionRepository, UserRepository userRepository, TeacherRepository teacherRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public ResponseDTO createPermission(final PermissionRequestDTO permissionRequestDTO) {
        final User user = this.userRepository.findById(permissionRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(permissionRequestDTO.getUserId());
        final Teacher teacher = this.teacherRepository.findById(permissionRequestDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        teacher.setId(permissionRequestDTO.getTeacherId());
        final Permission permission = Permission.builder()
                .createdBy(permissionRequestDTO.getCreatedBy())
                .updatedBy(permissionRequestDTO.getUpdatedBy())
                .teacher(teacher)
                .user(user)
                .build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.permissionRepository.save(permission)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.permissionRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final PermissionRequestDTO permissionRequestDTO) {
        final Permission existingSchool = this.permissionRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));

        if (permissionRequestDTO.getUpdatedBy() != null) {
            existingSchool.setUpdatedBy(permissionRequestDTO.getUpdatedBy());
        }
        if (permissionRequestDTO.getCreatedBy() != null) {
            existingSchool.setCreatedBy(permissionRequestDTO.getCreatedBy());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.permissionRepository.save(existingSchool)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.permissionRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.IDDOESNOTEXIST);
        }
        this.permissionRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
