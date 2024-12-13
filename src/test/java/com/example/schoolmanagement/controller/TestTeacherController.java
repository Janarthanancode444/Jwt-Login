package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.controller.TeacherController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherRequestDTO;
import com.example.schoolmanagement.dto.TeacherResponseDTO;
import com.example.schoolmanagement.service.TeacherService;
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

public class TestTeacherController {
    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    public void createTeacher() throws Exception {

        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("abe");
        teacherRequestDTO.setPhone("123-587-9685");
        teacherRequestDTO.setAddress("chennai");
        teacherRequestDTO.setEmail("abc@gmail.com");
        teacherRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        teacherRequestDTO.setSchoolId("4979f417-6190-45ec-ade9-5932f0830882");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Created Teacher successfully");
        when(teacherService.createStaff(teacherRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/teacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"abe\",\"phone\":\"123-587-9685\",\"address\":\"chennai\",\"email\":\"abc@gmail.com\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\",\"schoolId\":\"4979f417-6190-45ec-ade9-5932f0830882\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Created Teacher successfully\"}"));
    }

    @Test
    void testRetrieveTeachers() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher retrieved successfully");

        when(teacherService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/teacher/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher retrieved successfully\"}"));
    }

    @Test
    void testUpdateTeacher() throws Exception {
        String id = "4979f417-6190-45ec-ade9-5932f0830882";
        TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO();
        teacherResponseDTO.setName("Updated Teacher");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher updated successfully");

        when(teacherService.updateStaff(teacherResponseDTO, id)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/teacher/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Teacher\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher updated successfully\"}"));
    }

    @Test
    void testDeleteTeacher() throws Exception {
        String id = "4979f417-6190-45ec-ade9-5932f0830882";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Teacher deleted successfully");

        when(teacherService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/teacher/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Teacher deleted successfully\"}"));
    }
}
