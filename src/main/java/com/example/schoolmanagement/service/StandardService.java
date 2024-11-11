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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service

public class StandardService {
    private final StandardRepository standardRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public StandardService(final StandardRepository standardRepository, SchoolRepository schoolRepository, UserRepository userRepository) {
        this.standardRepository = standardRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseDTO create(final StandardRequestDTO standardRequestDTO) {
        final User user = this.userRepository.findById(standardRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException("user not found"));
        user.setId(standardRequestDTO.getUserId());
        final School school = this.schoolRepository.findById(standardRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException("School not found"));
        school.setId(standardRequestDTO.getSchoolId());
        final Standard standard = new Standard();
        standard.setName(standardRequestDTO.getName());
        standard.setTotalStudent(standardRequestDTO.getTotalStudent());
        standard.setSchool(school);
        standard.setCreatedBy(standardRequestDTO.getCreatedBy());
        standard.setUpdatedBy(standardRequestDTO.getUpdatedBy());
        standard.setUser(user);
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.standardRepository.save(standard)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final String id, final StandardResponseDTO schoolClassResponse) {
        final Standard standard = this.standardRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Class not found"));

        if (schoolClassResponse.getName() != null) {
            standard.setName(schoolClassResponse.getName());
        }
        if (schoolClassResponse.getTotalStudent() != 0) {
            standard.setTotalStudent(schoolClassResponse.getTotalStudent());
        }
        if (schoolClassResponse.getUpdatedBy() != null) {
            standard.setUpdatedBy(schoolClassResponse.getUpdatedBy());
        }
        if (schoolClassResponse.getCreatedBy() != null) {
            standard.setCreatedBy(schoolClassResponse.getCreatedBy());
        }

        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.standardRepository.save(standard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.standardRepository.existsById(id)) {
            throw new BadRequestServiceException("Class Id not found");
        }
        this.standardRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
