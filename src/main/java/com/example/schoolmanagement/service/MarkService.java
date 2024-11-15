package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.MarkRequestDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.entity.Mark;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.Subject;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.MarkRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MarkService {
    private final MarkRepository markRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;


    public MarkService(MarkRepository markRepository, StudentRepository studentRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.markRepository = markRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public ResponseDTO createMark(final MarkRequestDTO markRequestDTO) {
        final User user = this.userRepository.findById(markRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(markRequestDTO.getUserId());
        final Student student = this.studentRepository.findById(markRequestDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        student.setId(markRequestDTO.getStudentId());
        final Subject subject = this.subjectRepository.findById(markRequestDTO.getSubjectId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        subject.setId(markRequestDTO.getSubjectId());
        final Mark mark = Mark.builder().subject(subject).student(student).user(user).createdBy(markRequestDTO.getCreatedBy()).updatedBy(markRequestDTO.getUpdatedBy()).mark(markRequestDTO.getMark()).build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.markRepository.save(mark)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.markRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateMark(final String id, final MarkRequestDTO markRequestDTO) {
        final Mark existingMark = this.markRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.NOT_FOUND));

        if (markRequestDTO.getUpdatedBy() != null) {
            existingMark.setUpdatedBy(markRequestDTO.getUpdatedBy());
        }
        if (markRequestDTO.getCreatedBy() != null) {
            existingMark.setCreatedBy(markRequestDTO.getCreatedBy());
        }
        if (markRequestDTO.getMark() != 0) {
            existingMark.setMark(markRequestDTO.getMark());
        }
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.markRepository.save(existingMark)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.markRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.markRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO findByMark(final Integer mark) {
        return new ResponseDTO(Constants.SUCCESS, this.markRepository.findByMarkLessThan(mark), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO OrderByMarkAsc() {
        return new ResponseDTO(Constants.SUCCESS, this.markRepository.findAllOrderByMarkAsc(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO OrderByMarkDesc() {
        return new ResponseDTO(Constants.SUCCESS, this.markRepository.findAllOrderByMarkDesc(), HttpStatus.OK.getReasonPhrase());
    }
}

