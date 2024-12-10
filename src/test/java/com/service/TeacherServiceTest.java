package com.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherRequestDTO;
import com.example.schoolmanagement.dto.TeacherResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.TeacherService;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TeacherService teacherService;
    @Mock
    private AuthenticationService authenticationService;

    private static final String SCHOOL_ID = "4c9b40e2-6a57-422d-896c-800b69491209-1";
    private static final String TEACHER_ID = "4979f417-6190-45ec-ade9-5932f0830882";
    private static final String User_ID = "dc0ce98e-9250-4915-8714-600178717a0b";

    @Test
    public void testCreateTeacher() {

        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setSchoolId(SCHOOL_ID);
        teacherRequestDTO.setUserId(User_ID);
        teacherRequestDTO.setName("ram");
        teacherRequestDTO.setGender("Male");
        teacherRequestDTO.setAddress("trichy");
        teacherRequestDTO.setPhone("564-253-8968");
        teacherRequestDTO.setEmail("ram@example.com");
        teacherRequestDTO.setSubject("Math");

        School mockSchool = mock(School.class);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(mockSchool));

        User user = mock(User.class);
        when(userRepository.findById(User_ID)).thenReturn(Optional.of(user));

        Teacher teacher = mock(Teacher.class);
        teacher.setSchool(mockSchool);
        teacher.setPhone(teacherRequestDTO.getPhone());
        teacher.setEmail(teacherRequestDTO.getEmail());
        teacher.setUser(user);
        teacher.setAddress(teacherRequestDTO.getAddress());
        teacher.setSubject(teacherRequestDTO.getSubject());
        teacher.setName(teacherRequestDTO.getName());
        teacher.setGender(teacherRequestDTO.getGender());
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);


        ResponseDTO response = teacherService.createStaff(teacherRequestDTO);

        assertNotNull(response);
        Object data = response.getData();
        assertEquals(data, teacher);
        assertEquals(Constants.CREATED, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue());
    }

    @Test
    void retrieveStudent() {
        Teacher teacher = new Teacher();
        teacher.setName("ram");
        teacher.setEmail("ram@gmail.com");
        teacher.setGender("male");
        teacher.setPhone("524-896-8754");
        teacher.setDateOfBirth("10-05-2000");


        Teacher teacher1 = new Teacher();
        teacher1.setName("raja");
        teacher1.setEmail("raj@gmail.com");
        teacher1.setGender("male");


        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher));

        ResponseDTO responseDTO = teacherService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Teacher teacher = mock(Teacher.class);
        TeacherResponseDTO teacherResponseDTO = mock(TeacherResponseDTO.class);

        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.ofNullable(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        ResponseDTO response = teacherService.updateStaff(teacherResponseDTO, TEACHER_ID);
        assertEquals(response.getData(), teacher);

    }

    @Test
    public void testRemove() {

        when(teacherRepository.existsById(TEACHER_ID)).thenReturn(true);

        ResponseDTO responseDTO = teacherService.remove(TEACHER_ID);
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(TEACHER_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }
}
