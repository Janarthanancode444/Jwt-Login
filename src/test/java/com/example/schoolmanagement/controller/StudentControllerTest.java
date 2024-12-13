package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.controller.StudentController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StudentRequestDTO;
import com.example.schoolmanagement.dto.StudentResponseDTO;
import com.example.schoolmanagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest {
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testCreateSchool() throws Exception {

        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("ram");
        studentRequestDTO.setPhone("856-523-8967");
        studentRequestDTO.setAddress("trichy");
        studentRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        studentRequestDTO.setTeacherId("69459f89-5e3d-450f-a571-1d4b8555edc1");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student created successfully");

        when(studentService.createStudent(studentRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/student/create/").contentType(MediaType.APPLICATION_JSON).
                        content("{\"name\":\"ram\", \"phone\":\"856-523-8967\", \"address\":\"trichy\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\",\"teacherId\":\"69459f89-5e3d-450f-a571-1d4b8555edc1\"}")).
                andExpect(status().isOk()).
                andExpect(content().json("{\"message\":\"Student created successfully\"}"));
    }

    @Test
    public void testRetrieveStudent() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student retrieved successfully");

        when(studentService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/student/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student retrieved successfully\"}"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        String id = "bff48073-2f38-49c7-8156-b76f1dffaad3";
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setName("Updated Student");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student Updated successfully");

        when(studentService.update(studentResponseDTO, id)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/student/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Student\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student Updated successfully\"}"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        String id = "bff48073-2f38-49c7-8156-b76f1dffaad3";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Student deleted successfully");

        when(studentService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/student/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Student deleted successfully\"}"));
    }
}
