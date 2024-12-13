package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.controller.PermissionController;
import com.example.schoolmanagement.dto.PermissionRequestDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.PermissionService;
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

public class PermissionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private PermissionController permissionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
    }

    @Test
    public void createPermission() throws Exception {

        PermissionRequestDTO permissionRequestDTO = new PermissionRequestDTO();
        permissionRequestDTO.setTeacherId("69459f89-5e3d-450f-a571-1d4b8555edc1");
        permissionRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Permission Created");


        when(permissionService.createPermission(permissionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/permission/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"dc0ce98e-9250-4915-8714-600178717a0b\", \"teacherId\":\"69459f89-5e3d-450f-a571-1d4b8555edc1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Permission Created\"}"));
    }

    @Test
    public void retrievePermission() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Permission retrieved successfully");

        when(permissionService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/permission/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Permission retrieved successfully\"}"));
    }

    @Test
    public void updatePermission() throws Exception {
        String id = "4979f417-6190-45ec-ade9-5932f0830882";
        PermissionRequestDTO permissionRequestDTO = new PermissionRequestDTO();
        permissionRequestDTO.setUpdatedBy("ram");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Permission updated successfully");

        when(permissionService.update(id, permissionRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/permission/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"updatedBy\":\"ram\"}"))

                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Permission updated successfully\"}"));
    }

    @Test
    public void removePermission() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Permission deleted successfully");

        when(permissionService.remove(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/permission/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Permission deleted successfully\"}"));
    }
}
