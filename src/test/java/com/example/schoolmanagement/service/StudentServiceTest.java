package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentRequestDTO;
import com.example.schoolmanagement.dto.StudentResponseDTO;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.StudentService;
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
public class StudentServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private StudentService studentService;

    private static final String TEACHER_ID = "4c9b40e2-6a57-422d-896c-800b69491209-1";
    private static final String User_ID = "dc0ce98e-9250-4915-8714-600178717a0b";

    @Test
    public void studentCreate() {

        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setTeacherId(TEACHER_ID);
        studentRequestDTO.setUserId(User_ID);
        studentRequestDTO.setName("mani");
        studentRequestDTO.setGender("Male");
        studentRequestDTO.setAddress("trichy");
        studentRequestDTO.setPhone("564-253-8968");
        studentRequestDTO.setEmail("ram@example.com");
        studentRequestDTO.setDateOfBirth("01-20-2000");
        studentRequestDTO.setFathersName("RAM");
        studentRequestDTO.setMothersName("priya");

        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.of(teacher));

        User user = mock(User.class);
        when(userRepository.findById(User_ID)).thenReturn(Optional.of(user));

        Student student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setTeacher(teacher);
        student.setUser(user);
        student.setPhone(studentRequestDTO.getPhone());
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setFathersName(studentRequestDTO.getFathersName());
        student.setMothersName(studentRequestDTO.getMothersName());
        student.setAddress(studentRequestDTO.getAddress());
        student.setGender(studentRequestDTO.getGender());
        student.setEmail(studentRequestDTO.getEmail());
        when(studentRepository.save(any(Student.class))).thenReturn(student);


        ResponseDTO response = studentService.createStudent(studentRequestDTO);

        assertNotNull(response);
        Object data = response.getData();
        assertEquals(data, student);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieveStudent() {
        Student student = new Student();
        student.setName("ram");
        student.setEmail("ram@gmail.com");
        student.setGender("male");
        student.setPhone("524-896-8754");
        student.setDateOfBirth("10-05-2000");


        Student student1 = new Student();
        student1.setName("raja");
        student1.setEmail("raj@gmail.com");
        student1.setGender("male");


        when(studentRepository.findAll()).thenReturn(List.of(student, student1));

        ResponseDTO responseDTO = studentService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Student student = mock(Student.class);
        StudentResponseDTO studentResponseDTO = mock(StudentResponseDTO.class);

        when(studentRepository.findById("2021")).thenReturn(Optional.ofNullable(student));
        assertNotNull(student);
        when(studentRepository.save(student)).thenReturn(student);

        ResponseDTO response = studentService.update(studentResponseDTO, "2021");
        assertEquals(response.getData(), student);
        Object data = response.getData();
        assertEquals(data, student);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());


    }

    @Test
    public void testRemove() {

        when(studentRepository.existsById("201234")).thenReturn(true);

        ResponseDTO responseDTO = studentService.remove("201234");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("201234", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

}
