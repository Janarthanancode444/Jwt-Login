package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SubjectRequestDTO;
import com.example.schoolmanagement.dto.SubjectResponseDTO;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.Subject;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.SubjectService;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @Mock
    private StandardRepository standardRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private SubjectRepository subjectRepository;

    @Test
    public void createSubject() {
        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setName("Tamil");
        subjectRequestDTO.setStandardId("7cfb59be-2749-4ede-9246-60a148401d21");
        subjectRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");

        Subject subject = new Subject();
        subject.setName(subjectRequestDTO.getName());
        User user = mock(User.class);
        when(userRepository.findById(subjectRequestDTO.getUserId())).thenReturn(Optional.of(user));
        subject.setUser(user);
        Standard standard = mock(Standard.class);
        when(standardRepository.findById(subjectRequestDTO.getStandardId())).thenReturn(Optional.of(standard));
        subject.setStandard(standard);
        when(subjectRepository.save(Mockito.any(Subject.class))).thenReturn(subject);
        ResponseDTO responseDTO = subjectService.createSubject(subjectRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, subject);
        assertEquals(Constants.REQUEST, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieve() {
        Subject subject = new Subject();
        subject.setName("Tamil");
        subject.setStandard(new Standard());
        subject.setUser(new User());

        Subject subject1 = new Subject();
        subject1.setName("English");
        subject1.setStandard(new Standard());
        subject1.setUser(new User());
        when(subjectRepository.findAll()).thenReturn(List.of(subject, subject1));

        ResponseDTO responseDTO = subjectService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Subject subject = mock(Subject.class);
        SubjectResponseDTO subjectResponseDTO = mock(SubjectResponseDTO.class);

        when(subjectRepository.findById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(Optional.ofNullable(subject));
        assertNotNull(subject);
        when(subjectRepository.save(subject)).thenReturn(subject);

        ResponseDTO response = subjectService.updateSubject("69459f89-5e3d-450f-a571-1d4b8555edc1", subjectResponseDTO);
        Object data = response.getData();
        assertEquals(data, subject);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());
    }

    @Test
    public void testRemove() {

        when(subjectRepository.existsById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(true);

        ResponseDTO responseDTO = subjectService.remove("69459f89-5e3d-450f-a571-1d4b8555edc1");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("69459f89-5e3d-450f-a571-1d4b8555edc1", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

}
