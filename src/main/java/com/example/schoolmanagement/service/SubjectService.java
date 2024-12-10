package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectRequestDTO;
import com.example.schoolmanagement.dto.SubjectResponseDTO;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.Subject;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final StandardRepository standardRepository;
    private final AuthenticationService authenticationService;

    public SubjectService(SubjectRepository subjectRepository, UserRepository userRepository, AuthenticationService authenticationService, StandardRepository standardRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO createSubject(final SubjectRequestDTO subjectRequestDTO) {
        final User user = this.userRepository.findById(subjectRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        final Standard standard = this.standardRepository.findById(subjectRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        final Subject subject = Subject.builder().name(subjectRequestDTO.getName()).standard(standard).createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).user(user).build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.subjectRepository.save(subject)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.subjectRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateSubject(final String id, final SubjectResponseDTO subjectResponseDTO) {
        final Subject existingSubject = this.subjectRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        if (subjectResponseDTO.getName() != null) {
            existingSubject.setName(subjectResponseDTO.getName());
        }
        if (subjectResponseDTO.getUpdatedBy() != null) {
            existingSubject.setUpdatedBy(authenticationService.getCurrentUser());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.subjectRepository.save(existingSubject)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.subjectRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.IDDOESNOTEXIST);
        }
        this.subjectRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
