package com.controller;

import com.example.schoolmanagement.controller.RoleController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.RoleRequestDTO;
import com.example.schoolmanagement.dto.RoleResponseDTO;
import com.example.schoolmanagement.service.RoleService;
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

public class RoleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void createRole() throws Exception {

        RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
        roleRequestDTO.setName("abe");
        roleRequestDTO.setDepartment("Tamil");
        roleRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Role Successfully");


        when(roleService.createRole(roleRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/role/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"abe\", \"department\":\"Tamil\",\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Role Successfully\"}"));
    }

    @Test
    public void retrieveRole() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Role retrieved successfully");

        when(roleService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/role/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Role retrieved successfully\"}"));
    }

    @Test
    public void updateRole() throws Exception {
        String id = "1";
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setName("Updated Role");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Role updated successfully");

        when(roleService.update(roleResponseDTO, id)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/role/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Role\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Role updated successfully\"}"));
    }

    @Test
    public void testDeleteRole() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Role deleted successfully");

        when(roleService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/role/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Role deleted successfully\"}"));
    }
}
