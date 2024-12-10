package com.controller;

import com.example.schoolmanagement.controller.MarkController;
import com.example.schoolmanagement.dto.MarkRequestDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.MarkService;
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

public class MarkControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MarkService markService;

    @InjectMocks
    private MarkController markController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(markController).build();
    }

    @Test
    public void testCreateMark() throws Exception {

        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setMark(78);
        markRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        markRequestDTO.setStudentId("69459f89-5e3d-450f-a571-1d4b8555edc1");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark Created Successfully");
        when(markService.createMark(markRequestDTO)).thenReturn(responseDTO);
        mockMvc.perform(post("/api/mark/create/").contentType(MediaType.APPLICATION_JSON).
                        content("{\"mark\":\"78\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\",\"studentId\":\"69459f89-5e3d-450f-a571-1d4b8555edc1\"}")).
                andExpect(status().isOk()).
                andExpect(content().json("{\"message\":\"Mark Created Successfully\"}"));
    }
    @Test
    void testRetrieveMark() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark Retrieved Successfully");
        when(markService.retrieve()).thenReturn(responseDTO);
        mockMvc.perform(get("/api/mark/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Mark Retrieved Successfully\"}"));
    }
    @Test
    void testUpdateMark() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        MarkRequestDTO markRequestDTO = new MarkRequestDTO();
        markRequestDTO.setMark(89);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark Updated Successfully");
        when(markService.updateMark(id,markRequestDTO)).thenReturn(responseDTO);
        mockMvc.perform(put("/api/mark/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mark\":\"89\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Mark Updated Successfully\"}"));
    }
    @Test
    void testDeleteMark() throws Exception {
        String id = "7cfb59be-2749-4ede-9246-60a148401d21";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Mark Deleted Successfully");
        when(markService.remove(id)).thenReturn(responseDTO);
        mockMvc.perform(delete("/api/mark/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Mark Deleted Successfully\"}"));
    }
}
