package com.controller;

import com.example.schoolmanagement.controller.UserController;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserRequestDTO;
import com.example.schoolmanagement.dto.UserResponseDTO;
import com.example.schoolmanagement.service.UserService;
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

public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("abe");
        userRequestDTO.setPhone("123");
        userRequestDTO.setRoles("admin");
        userRequestDTO.setEmail("abc@gmail.com");


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User created successfully");


        when(userService.create(userRequestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"abe\", \"phone\":\"123\",\"email\":\"abc@gmail.com\",\"roles\":\"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"User created successfully\"}"));
    }

    @Test
    public void testRetrieveUser() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User retrieved successfully");

        when(userService.retrieve()).thenReturn(responseDTO);

        mockMvc.perform(get("/api/user/retrieve/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"User retrieved successfully\"}"));
    }

    @Test
    public void testUpdateUSer() throws Exception {
        String id = "1";
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName("Updated User");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User updated successfully");

        when(userService.updateUser(userResponseDTO, id)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated User\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"User updated successfully\"}"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String id = "1";
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("User deleted successfully");

        when(userService.removeUser(id)).thenReturn(responseDTO);

        mockMvc.perform(delete("/api/user/remove/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"User deleted successfully\"}"));
    }
}
