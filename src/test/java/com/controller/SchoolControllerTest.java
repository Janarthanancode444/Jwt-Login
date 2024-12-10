package com.controller;

import com.example.schoolmanagement.controller.SchoolController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolRequestDTO;
import com.example.schoolmanagement.dto.SchoolResponseDTO;
import com.example.schoolmanagement.service.SchoolService;
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

public class SchoolControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private SchoolController schoolController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    public void testCreateSchool() throws Exception {

        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName("abe");
        schoolRequestDTO.setPhone("123-587-9685");
        schoolRequestDTO.setAddress("chennai");
        schoolRequestDTO.setEmail("abc@gmail.com");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Created School successfully");


        when(schoolService.createSchool(schoolRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/school/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"abe\", \"phone\":\"123-587-9685\",\"email\":\"abc@gmail.com\",\"address\":\"chennai\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Created School successfully\"}"));
    }

    @Test
    public void retrieveSchool() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School retrieved successfully");

        when(schoolService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/school/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School retrieved successfully\"}"));
    }

    @Test
    public void testUpdateSchool() throws Exception {
        String id = "1";
        SchoolResponseDTO schoolResponseDTO = new SchoolResponseDTO();
        schoolResponseDTO.setName("Updated School");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School updated successfully");

        when(schoolService.update(schoolResponseDTO, id)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/school/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated School\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School updated successfully\"}"));
    }

    @Test
    public void testDeleteSchool() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("School deleted successfully");

        when(schoolService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/school/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"School deleted successfully\"}"));
    }
}
