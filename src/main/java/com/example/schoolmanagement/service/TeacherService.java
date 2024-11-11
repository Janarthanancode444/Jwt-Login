package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherRequestDTO;
import com.example.schoolmanagement.dto.TeacherResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, SchoolRepository schoolRepository) {

        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public ResponseDTO createStaff(final TeacherRequestDTO teacherRequestDTO) {
        final User user = this.userRepository.findById(teacherRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException("User not found"));
        user.setId(teacherRequestDTO.getUserId());
        final School school = this.schoolRepository.findById(teacherRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceException("School not found"));
        final Teacher teacher = new Teacher();
        school.setId(teacherRequestDTO.getSchoolId());
        teacher.setName(teacherRequestDTO.getName());
        teacher.setDateOfBirth(teacherRequestDTO.getDateOfBirth());
        teacher.setGender(teacherRequestDTO.getGender());
        teacher.setAddress(teacherRequestDTO.getAddress());
        teacher.setPhone(teacherRequestDTO.getPhone());
        teacher.setEmail((teacherRequestDTO.getEmail()));
        teacher.setCreatedBy(teacherRequestDTO.getCreatedBy());
        teacher.setUpdatedBy(teacherRequestDTO.getUpdatedBy());
        teacher.setSubject(teacherRequestDTO.getSubject());
        teacher.setUser(user);
        teacher.setSchool(school);
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.teacherRepository.save(teacher)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateStaff(final TeacherResponseDTO staffResponseDTO, final String id) {
        final Teacher existingTeacher = this.teacherRepository.findById(id).orElseThrow(() -> new BadRequestServiceException("Teacher not found"));

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
            throw new BadRequestServiceException("Teacher Id not found");
        }
        this.teacherRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
