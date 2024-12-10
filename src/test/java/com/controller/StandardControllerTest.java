package com.controller;

import com.example.schoolmanagement.controller.StandardController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardRequestDTO;
import com.example.schoolmanagement.dto.StandardResponseDTO;
import com.example.schoolmanagement.service.StandardService;
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

public class StandardControllerTest {
    private MockMvc mockMvc;

    @Mock
    private StandardService standardService;

    @InjectMocks
    private StandardController standardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(standardController).build();
    }

    @Test
    public void testCreateStandard() throws Exception {

        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setName("10th");
        standardRequestDTO.setTotalStudent(80);
        standardRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        standardRequestDTO.setSchoolId("69459f89-5e3d-450f-a571-1d4b8555edc1");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard Created Successfully");
        when(standardService.create(standardRequestDTO)).thenReturn(responseDTO);
        mockMvc.perform(post("/api/standard/create/").contentType(MediaType.APPLICATION_JSON).
                        content("{\"name\":\"10th\",\"totalStudent\":\"80\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\",\"schoolId\":\"69459f89-5e3d-450f-a571-1d4b8555edc1\"}")).
                andExpect(status().isOk()).
                andExpect(content().json("{\"message\":\"Standard Created Successfully\"}"));
    }

    @Test
    void testRetrieveStandard() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard Retrieved Successfully");
        when(standardService.retrieve()).thenReturn(responseDTO);
        mockMvc.perform(get("/api/standard/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standard Retrieved Successfully\"}"));
    }

    @Test
    void testUpdateStandard() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        StandardResponseDTO standardResponseDTO = new StandardResponseDTO();
        standardResponseDTO.setName("Updated Standard");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Standard Updated Successfully");
        when(standardService.update(id, standardResponseDTO)).thenReturn(responseDTO);
        mockMvc.perform(put("/api/standard/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Standard\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Standard Updated Successfully\"}"));
    }

    @Test
    void testDeleteStandard() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section Deleted Successfully");
        when(standardService.remove(id)).thenReturn(responseDTO);
        mockMvc.perform(delete("/api/standard/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section Deleted Successfully\"}"));
    }
}
