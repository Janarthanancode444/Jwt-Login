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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseDTO createStudent(final StudentRequestDTO studentRequestDTO) {
        final User user = this.userRepository.findById(studentRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException("User not found"));
        user.setId(studentRequestDTO.getUserId());
        final Student student = new Student();
        final Teacher teacher = this.teacherRepository.findById(studentRequestDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceException("Teacher not found"));
        teacher.setId(studentRequestDTO.getTeacherId());
        student.setName(studentRequestDTO.getName());
        student.setAddress(studentRequestDTO.getAddress());
        student.setPhone(studentRequestDTO.getPhone());
        student.setGender(studentRequestDTO.getGender());
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setFathersName(studentRequestDTO.getFathersName());
        student.setMothersName(studentRequestDTO.getMothersName());
        student.setCreatedBy(studentRequestDTO.getCreatedBy());
        student.setUpdatedBy(studentRequestDTO.getUpdatedBy());
        student.setEmail(studentRequestDTO.getEmail());
        student.setUser(user);
        student.setTeacher(teacher);
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.studentRepository.save(student)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO update(final StudentResponseDTO studentResponseDTO, final String id) {
        final Student existingStudent = this.studentRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("student not found"));

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
            existingStudent.setUpdatedBy(studentResponseDTO.getUpdatedBy());
        }
        if (studentResponseDTO.getCreatedBy() != null) {
            existingStudent.setCreatedBy(studentResponseDTO.getCreatedBy());
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
            throw new BadRequestServiceException("Student Id not found");
        }
        this.studentRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

}
