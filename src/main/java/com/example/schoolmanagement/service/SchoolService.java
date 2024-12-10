package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolRequestDTO;
import com.example.schoolmanagement.dto.SchoolResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;

    public SchoolService(SchoolRepository schoolRepository, UserRepository userRepository, AuthenticationService authenticationService, StandardRepository standardRepository, SubjectRepository subjectRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.standardRepository = standardRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public ResponseDTO createSchool(final SchoolRequestDTO schoolRequestDTO) {
        final User user = this.userRepository.findById(schoolRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        this.validateSchoolDTO(schoolRequestDTO);
        this.validatePhoneSchoolDTO(schoolRequestDTO);
        final School school = School.builder().name(schoolRequestDTO.getName()).address(schoolRequestDTO.getAddress()).phone(schoolRequestDTO.getPhone()).email(schoolRequestDTO.getEmail()).user(user).createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.schoolRepository.save(school)).statusValue(HttpStatus.CREATED.name()).build();
    }

    private void validateSchoolDTO(final SchoolRequestDTO schoolRequestDTO) {
        if (UtilService.emailValidation(schoolRequestDTO.getEmail())) {
            throw new BadRequestServiceException(Constants.EMAIL_PATTERN);
        }
        final List<School> emailFound = this.schoolRepository.findByEmail(schoolRequestDTO.getEmail());
        if (!emailFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.EMAIL);
        }
    }

    private void validatePhoneSchoolDTO(final SchoolRequestDTO schoolRequestDTO) {
        if (UtilService.phoneNumberValidation(schoolRequestDTO.getPhone())) {
            throw new BadRequestServiceException(Constants.PHONE_PATTERN);
        }
        final List<School> phoneFound = this.schoolRepository.findByPhone(schoolRequestDTO.getPhone());
        if (!phoneFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.PHONE);
        }
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.schoolRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final SchoolResponseDTO responseSchoolDTO, final String id) {
        final School existingSchool = this.schoolRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));

        if (responseSchoolDTO.getName() != null) {
            existingSchool.setName(responseSchoolDTO.getName());
        }
        if (responseSchoolDTO.getAddress() != null) {
            existingSchool.setAddress(responseSchoolDTO.getAddress());
        }
        if (responseSchoolDTO.getEmail() != null) {
            existingSchool.setEmail(responseSchoolDTO.getEmail());
        }
        if (responseSchoolDTO.getPhone() != null) {
            existingSchool.setPhone(responseSchoolDTO.getPhone());
        }
        if (responseSchoolDTO.getUpdatedBy() != null) {
            existingSchool.setUpdatedBy(authenticationService.getCurrentUser());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.schoolRepository.save(existingSchool)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.schoolRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.IDDOESNOTEXIST);
        }
        this.schoolRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO searchSchool(final String search, final Integer page, final Integer size, final String sortField, final String sortDirection) {
        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 5, sort);
        final Page<School> school = this.schoolRepository.searchSchool(search, pageable);
        if (school.isEmpty()) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(school.map(this::convertToSchoolRequestDTO)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    private SchoolRequestDTO convertToSchoolRequestDTO(final School school) {
        final SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName(school.getName());
        schoolRequestDTO.setAddress(school.getAddress());
        schoolRequestDTO.setEmail(school.getEmail());
        schoolRequestDTO.setPhone(school.getPhone());
        return schoolRequestDTO;
    }
}


