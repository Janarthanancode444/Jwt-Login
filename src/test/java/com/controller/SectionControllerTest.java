package com.controller;

import com.example.schoolmanagement.controller.SectionController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionRequestDTO;
import com.example.schoolmanagement.dto.SectionResponseDTO;
import com.example.schoolmanagement.service.SectionService;
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

public class SectionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    private SectionController sectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sectionController).build();
    }

    @Test
    public void testCreateSection() throws Exception {

        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setSection("a");
        sectionRequestDTO.setStandardId("7cfb59be-2749-4ede-9246-60a148401d21");
        sectionRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        sectionRequestDTO.setTeacherId("69459f89-5e3d-450f-a571-1d4b8555edc1");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section Created Successfully");

        when(sectionService.createSection(sectionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/section/create/").contentType(MediaType.APPLICATION_JSON).
                        content("{\"section\":\"a\",\"standardId\":\"7cfb59be-2749-4ede-9246-60a148401d21\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\",\"teacherId\":\"69459f89-5e3d-450f-a571-1d4b8555edc1\"}")).
                andExpect(status().isOk()).
                andExpect(content().json("{\"message\":\"Section Created Successfully\"}"));
    }

    @Test
    void testRetrieveSection() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section Retrieved Successfully");
        when(sectionService.retrieve()).thenReturn(responseDTO);
        mockMvc.perform(get("/api/section/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section Retrieved Successfully\"}"));
    }

    @Test
    void testUpdateSection() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        SectionResponseDTO sectionResponseDTO = new SectionResponseDTO();
        sectionResponseDTO.setSection("Updated Section");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section Updated Successfully");
        when(sectionService.updateSection(sectionResponseDTO, id)).thenReturn(responseDTO);
        mockMvc.perform(put("/api/section/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Section\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section Updated Successfully\"}"));
    }

    @Test
    void testDeleteSection() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Section Deleted Successfully");
        when(sectionService.remove(id)).thenReturn(responseDTO);
        mockMvc.perform(delete("/api/section/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Section Deleted Successfully\"}"));
    }
}
