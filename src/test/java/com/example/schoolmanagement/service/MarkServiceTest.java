package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.MarkRequestDTO;
import com.example.schoolmanagement.dto.MarkResponseDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.entity.Mark;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.Subject;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.MarkRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.SubjectRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.MarkService;
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
public class MarkServiceTest {
    @Mock
    private MarkRepository markRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private MarkService markService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SubjectRepository subjectRepository;

    @Test
    public void crateMark() {
        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setMark(80);
        markRequestDTO.setSubjectId("7cfb59be-2749-4ede-9246-60a148401d21");
        markRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        markRequestDTO.setStudentId("fc45ffa0-885f-40eb-9c3c-479feec75471");

        Mark mark = new Mark();
        mark.setMark(markRequestDTO.getMark());
        User user = mock(User.class);
        when(userRepository.findById(markRequestDTO.getUserId())).thenReturn(Optional.of(user));
        mark.setUser(user);
        Subject subject = mock(Subject.class);
        when(subjectRepository.findById(markRequestDTO.getSubjectId())).thenReturn(Optional.of(subject));
        mark.setSubject(subject);
        Student student = mock(Student.class);
        when(studentRepository.findById(markRequestDTO.getStudentId())).thenReturn(Optional.of(student));
        mark.setStudent(student);
        when(markRepository.save(Mockito.any(Mark.class))).thenReturn(mark);
        ResponseDTO responseDTO = markService.createMark(markRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, mark);
        assertEquals(Constants.REQUEST, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieve() {
        Mark mark = new Mark();
        mark.setMark(89);
        mark.setSubject(new Subject());
        mark.setUser(new User());
        mark.setStudent(new Student());

        Mark mark1 = new Mark();
        mark1.setMark(59);
        mark1.setSubject(new Subject());
        mark1.setUser(new User());
        mark1.setStudent(new Student());
        when(markRepository.findAll()).thenReturn(List.of(mark, mark1));

        ResponseDTO responseDTO = markService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(markRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Mark mark = mock(Mark.class);
        MarkResponseDTO markResponseDTO = mock(MarkResponseDTO.class);

        when(markRepository.findById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(Optional.ofNullable(mark));
        assertNotNull(mark);
        when(markRepository.save(mark)).thenReturn(mark);

        ResponseDTO response = markService.updateMark("69459f89-5e3d-450f-a571-1d4b8555edc1", markResponseDTO);
        Object data = response.getData();
        assertEquals(data, mark);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());
    }

    @Test
    public void testRemove() {

        when(markRepository.existsById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(true);

        ResponseDTO responseDTO = markService.remove("69459f89-5e3d-450f-a571-1d4b8555edc1");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("69459f89-5e3d-450f-a571-1d4b8555edc1", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }


}
