package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolUserRecord;
import com.example.schoolmanagement.dto.TeacherRequestDTO;
import com.example.schoolmanagement.dto.TeacherResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.UtilService;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final AuthenticationService authenticationService;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, SchoolRepository schoolRepository, AuthenticationService authenticationService) {

        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO createStaff(final TeacherRequestDTO teacherRequestDTO) {
        final User user = this.userRepository.findById(teacherRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(teacherRequestDTO.getUserId());
        final School schoolId = this.schoolRepository.findById(teacherRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        schoolId.setId(teacherRequestDTO.getSchoolId());
        this.validateEmail(teacherRequestDTO);
        this.validatePhone(teacherRequestDTO);
        final Teacher teacher = Teacher.builder().school(schoolId).name(teacherRequestDTO.getName()).dateOfBirth(teacherRequestDTO.getDateOfBirth()).gender(teacherRequestDTO.getGender()).address(teacherRequestDTO.getAddress()).phone(teacherRequestDTO.getPhone()).email(teacherRequestDTO.getEmail()).createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).subject(teacherRequestDTO.getSubject()).user(user).build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.teacherRepository.save(teacher)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    private void validateEmail(final TeacherRequestDTO teacherRequestDTO) {
        if (UtilService.emailValidation(teacherRequestDTO.getEmail())) {
            throw new BadRequestServiceException(Constants.EMAIL_PATTERN);
        }
        final List<Teacher> emailFound = this.teacherRepository.findByEmail(teacherRequestDTO.getEmail());
        if (!emailFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.EMAIL);
        }
    }

    private void validatePhone(final TeacherRequestDTO teacherRequestDTO) {
        if (UtilService.phoneNumberValidation(teacherRequestDTO.getPhone())) {
            throw new BadRequestServiceException(Constants.PHONE_PATTERN);
        }
        final List<Teacher> phoneFound = this.teacherRepository.findByPhone(teacherRequestDTO.getPhone());
        if (!phoneFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.PHONE);
        }
    }

    @Transactional
    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateStaff(final TeacherResponseDTO staffResponseDTO, final String id) {
        final Teacher existingTeacher = this.teacherRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        if (staffResponseDTO.getName() != null) {
            existingTeacher.setName(staffResponseDTO.getName());
        }
        if (staffResponseDTO.getAddress() != null) {
            existingTeacher.setAddress(staffResponseDTO.getAddress());
        }
        if (staffResponseDTO.getDateOfBirth() != null) {
            existingTeacher.setDateOfBirth(staffResponseDTO.getDateOfBirth());
        }
        if (staffResponseDTO.getPhone() != null) {
            existingTeacher.setPhone(staffResponseDTO.getPhone());
        }
        if (staffResponseDTO.getEmail() != null) {
            existingTeacher.setEmail(staffResponseDTO.getEmail());
        }
        if (staffResponseDTO.getGender() != null) {
            existingTeacher.setGender(staffResponseDTO.getGender());
        }
        if (staffResponseDTO.getSubject() != null) {
            existingTeacher.setSubject(staffResponseDTO.getSubject());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.teacherRepository.save(existingTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.teacherRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.teacherRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO search(final String school, final String subject, final String range, final Boolean lesserThan) {
        final List<Tuple> result = this.teacherRepository.search(school, subject, range, lesserThan);
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(result.stream()
                .map(tuple -> new SchoolUserRecord(
                        tuple.get("teacherName", String.class),
                        tuple.get("teacherSchool", String.class),
                        tuple.get("teacherHandlingSection", String.class),
                        tuple.get("teacherId", String.class)
                ))
                .collect(Collectors.toList())).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
