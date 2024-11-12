package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectRequestDTO;
import com.example.schoolmanagement.dto.SubjectResponseDTO;
import com.example.schoolmanagement.entity.Subject;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectService(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseDTO createSubject(final SubjectRequestDTO subjectRequestDTO) {
        final User user = this.userRepository.findById(subjectRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(subjectRequestDTO.getUserId());
        final Subject subject = Subject.builder().build();
        subject.setName(subjectRequestDTO.getName());
        subject.setCreatedBy(subjectRequestDTO.getCreatedBy());
        subject.setUpdatedBy(subjectRequestDTO.getUpdatedBy());
        subject.setUser(user);
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

        if (subjectResponseDTO.getCreatedBy() != null) {
            existingSubject.setCreatedBy(subjectResponseDTO.getCreatedBy());
        }
        if (subjectResponseDTO.getUpdatedBy() != null) {
            existingSubject.setUpdatedBy(subjectResponseDTO.getUpdatedBy());
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
