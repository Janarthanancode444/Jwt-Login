package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardRequestDTO;
import com.example.schoolmanagement.dto.StandardResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service

public class StandardService {
    private final StandardRepository standardRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final AuthenticationService authenticationService;

    public StandardService(StandardRepository standardRepository, SchoolRepository schoolRepository, UserRepository userRepository, AuthenticationService authenticationService) {
        this.standardRepository = standardRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final StandardRequestDTO standardRequestDTO) {
        final User user = this.userRepository.findById(standardRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(standardRequestDTO.getUserId());
        final School school = this.schoolRepository.findById(standardRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        school.setId(standardRequestDTO.getSchoolId());
        final Standard standard = Standard.builder().name(standardRequestDTO.getName()).totalStudent(standardRequestDTO.getTotalStudent()).school(school).createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).user(user).build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.standardRepository.save(standard)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final StandardResponseDTO schoolClassResponse) {
        final Standard standard = this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));

        if (schoolClassResponse.getName() != null) {
            standard.setName(schoolClassResponse.getName());
        }
        if (schoolClassResponse.getTotalStudent() != 0) {
            standard.setTotalStudent(schoolClassResponse.getTotalStudent());
        }
        if (schoolClassResponse.getUpdatedBy() != null) {
            standard.setUpdatedBy(authenticationService.getCurrentUser());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.standardRepository.save(standard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.standardRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.standardRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
