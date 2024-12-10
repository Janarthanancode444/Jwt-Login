package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentRequestDTO;
import com.example.schoolmanagement.dto.StudentResponseDTO;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationService authenticationService;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, TeacherRepository teacherRepository, AuthenticationService authenticationService) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO createStudent(final StudentRequestDTO studentRequestDTO) {
        final User user = this.userRepository.findById(studentRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(studentRequestDTO.getUserId());
        final Teacher teacher = this.teacherRepository.findById(studentRequestDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        teacher.setId(studentRequestDTO.getTeacherId());
        this.validateEmail(studentRequestDTO);
        this.validatePhone(studentRequestDTO);
        final Student student = Student.builder().name(studentRequestDTO.getName()).address(studentRequestDTO.getAddress()).phone(studentRequestDTO.getPhone()).gender(studentRequestDTO.getGender()).dateOfBirth(studentRequestDTO.getDateOfBirth()).fathersName(studentRequestDTO.getFathersName()).mothersName(studentRequestDTO.getMothersName()).createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).email(studentRequestDTO.getEmail()).user(user).teacher(teacher).build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.studentRepository.save(student)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    private void validateEmail(final StudentRequestDTO studentRequestDTO) {
        if (UtilService.emailValidation(studentRequestDTO.getEmail())) {
            throw new BadRequestServiceException(Constants.EMAIL_PATTERN);
        }
        final List<Student> emailFound = this.studentRepository.findByEmail(studentRequestDTO.getEmail());
        if (!emailFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.EMAIL);
        }
    }

    private void validatePhone(final StudentRequestDTO studentRequestDTO) {
        if (UtilService.phoneNumberValidation(studentRequestDTO.getPhone())) {
            throw new BadRequestServiceException(Constants.PHONE_PATTERN);
        }
        final List<Student> phoneFound = this.studentRepository.findByPhone(studentRequestDTO.getPhone());
        if (!phoneFound.isEmpty()) {
            throw new BadRequestServiceException(Constants.PHONE);
        }
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final StudentResponseDTO studentResponseDTO, final String id) {
        final Student existingStudent = this.studentRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));

        if (studentResponseDTO.getName() != null) {
            existingStudent.setName(studentResponseDTO.getName());
        }
        if (studentResponseDTO.getAddress() != null) {
            existingStudent.setAddress(studentResponseDTO.getAddress());
        }
        if (studentResponseDTO.getPhone() != null) {
            existingStudent.setPhone(studentResponseDTO.getPhone());
        }
        if (studentResponseDTO.getEmail() != null) {
            existingStudent.setEmail(studentResponseDTO.getEmail());
        }
        if (studentResponseDTO.getDateOfBirth() != null) {
            existingStudent.setDateOfBirth(studentResponseDTO.getDateOfBirth());
        }
        if (studentResponseDTO.getGender() != null) {
            existingStudent.setGender(studentResponseDTO.getGender());
        }
        if (studentResponseDTO.getUpdatedBy() != null) {
            existingStudent.setUpdatedBy(authenticationService.getCurrentUser());
        }
        if (studentResponseDTO.getFathersName() != null) {
            existingStudent.setFathersName(studentResponseDTO.getFathersName());
        }
        if (studentResponseDTO.getMothersName() != null) {
            existingStudent.setMothersName(studentResponseDTO.getMothersName());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.studentRepository.save(existingStudent)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.studentRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.studentRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
