package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionRequestDTO;
import com.example.schoolmanagement.dto.SectionResponseDTO;
import com.example.schoolmanagement.entity.Section;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.BadRequestServiceException;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final StandardRepository standardRepository;
    private final UserRepository userRepository;

    public SectionService(SectionRepository sectionRepository, UserRepository userRepository, StandardRepository standardRepository) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.standardRepository = standardRepository;
    }

    @Transactional
    public ResponseDTO createSection(final SectionRequestDTO sectionRequestDTO) {
        final Standard standard = this.standardRepository.findById(sectionRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceException(Constants.IDDOESNOTEXIST));
        standard.setId(sectionRequestDTO.getStandardId());
        final User user = this.userRepository.findById(sectionRequestDTO.getUserId()).orElseThrow(() -> new BadRequestServiceException(Constants.User));
        user.setId(sectionRequestDTO.getUserId());
        final Section section = Section.builder().build();
        section.setSection(sectionRequestDTO.getSection());
        section.setStandard(standard);
        section.setUser(user);
        section.setCreatedAt(sectionRequestDTO.getCreatedAt());
        section.setCreatedBy(sectionRequestDTO.getCreatedBy());
        section.setUpdatedAt(sectionRequestDTO.getUpdatedAt());
        section.setUpdatedBy(sectionRequestDTO.getUpdatedBy());
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
        if (sectionResponseDTO.getCreatedBy() != null) {
            existingSection.setCreatedBy(sectionResponseDTO.getCreatedBy());
        }
        if (sectionResponseDTO.getUpdatedBy() != null) {
            existingSection.setUpdatedBy(sectionResponseDTO.getUpdatedBy());
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
