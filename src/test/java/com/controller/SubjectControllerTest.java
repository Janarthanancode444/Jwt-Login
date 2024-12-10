package com.controller;

import com.example.schoolmanagement.controller.StandardController;
import com.example.schoolmanagement.controller.SubjectController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardRequestDTO;
import com.example.schoolmanagement.dto.StandardResponseDTO;
import com.example.schoolmanagement.dto.SubjectRequestDTO;
import com.example.schoolmanagement.dto.SubjectResponseDTO;
import com.example.schoolmanagement.service.StandardService;
import com.example.schoolmanagement.service.SubjectService;
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

public class SubjectControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    public void testCreateSubject() throws Exception {

        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setName("Tamil");
        subjectRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        subjectRequestDTO.setStandardId("69459f89-5e3d-450f-a571-1d4b8555edc1");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject Created Successfully");
        when(subjectService.createSubject(subjectRequestDTO)).thenReturn(responseDTO);
        mockMvc.perform(post("/api/subject/create/").contentType(MediaType.APPLICATION_JSON).
                        content("{\"name\":\"Tamil\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\",\"standardId\":\"69459f89-5e3d-450f-a571-1d4b8555edc1\"}")).
                andExpect(status().isOk()).
                andExpect(content().json("{\"message\":\"Subject Created Successfully\"}"));
    }
    @Test
    void testRetrieveSubject() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject Retrieved Successfully");
        when(subjectService.retrieve()).thenReturn(responseDTO);
        mockMvc.perform(get("/api/subject/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject Retrieved Successfully\"}"));
    }
    @Test
    void testUpdateSubject() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        SubjectResponseDTO subjectResponseDTO = new SubjectResponseDTO();
        subjectResponseDTO.setName("English-II");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject Updated Successfully");
        when(subjectService.updateSubject(id,subjectResponseDTO)).thenReturn(responseDTO);
        mockMvc.perform(put("/api/subject/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Subject\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject Updated Successfully\"}"));
    }

    @Test
    void testDeleteSubject() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Subject Deleted Successfully");
        when(subjectService.remove(id)).thenReturn(responseDTO);
        mockMvc.perform(delete("/api/subject/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Subject Deleted Successfully\"}"));
    }

}
