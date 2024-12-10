package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionRequestDTO;
import com.example.schoolmanagement.dto.SectionResponseDTO;
import com.example.schoolmanagement.entity.Section;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import com.example.schoolmanagement.util.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final StandardRepository standardRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;

    public SectionService(SectionRepository sectionRepository, UserRepository userRepository, StandardRepository standardRepository, AuthenticationService authenticationService, TeacherRepository teacherRepository) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.standardRepository = standardRepository;
        this.authenticationService = authenticationService;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public ResponseDTO createSection(final SectionRequestDTO sectionRequestDTO) {
        final Standard standard = this.standardRepository.findById(sectionRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        standard.setId(sectionRequestDTO.getStandardId());
        final User user = this.userRepository.findById(sectionRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(sectionRequestDTO.getUserId());
        final Teacher teacher = this.teacherRepository.findById(sectionRequestDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        final Section section = Section.builder().standard(standard).user(user).teacher(teacher).section(sectionRequestDTO.getSection()).createdBy(authenticationService.getCurrentUser()).updatedBy(authenticationService.getCurrentUser()).build();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionRepository.save(section)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieve() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.sectionRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateSection(final SectionResponseDTO sectionResponseDTO, final String id) {
        final Section existingSection = this.sectionRepository.findById(id).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));

        if (sectionResponseDTO.getSection() != null) {
            existingSection.setSection(sectionResponseDTO.getSection());
        }
        if (sectionResponseDTO.getUpdatedBy() != null) {
            existingSection.setUpdatedBy(authenticationService.getCurrentUser());
        }

        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionRepository.save(existingSection)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.sectionRepository.existsById(id)) {
            throw new BadRequestServiceException(Constants.NOT_FOUND);
        }
        this.sectionRepository.deleteById(id);
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
