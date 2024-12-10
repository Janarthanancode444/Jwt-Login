package com.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.UserRequestDTO;
import com.example.schoolmanagement.dto.UserResponseDTO;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.UserService;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testUser() {
        User user = new User();
        user.setName("ram");
        user.setEmail("ram@gmail.com");
        user.setPhone("457-896-2564");
        user.setPassword(passwordEncoder.encode("ram"));
        user.setRoles("Admin");
        user.setCreatedBy(authenticationService.getCurrentUser());
        user.setUpdatedBy(authenticationService.getCurrentUser());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName(user.getName());
        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setPhone(user.getPhone());
        userRequestDTO.setPassword(user.getPassword());
        userRequestDTO.setRoles(user.getRoles());
        userRequestDTO.setCreatedBy(user.getCreatedBy());
        userRequestDTO.setUpdatedBy(user.getUpdatedBy());
        ResponseDTO responseDTO = userService.create(userRequestDTO);

        Object data = responseDTO.getData();
        assertEquals(data, user);
        assertEquals(Constants.REQUEST, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieveUser() {
        User user = new User();
        user.setName("ram");
        user.setEmail("ram@gmail.com");
        user.setRoles("admin");

        User user1 = new User();
        user1.setName("raja");
        user1.setEmail("raj@gmail.com");
        user1.setRoles("super_admin");

        when(userRepository.findAll()).thenReturn(List.of(user, user1));

        ResponseDTO responseDTO = userService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        User user = mock(User.class);
        UserResponseDTO userResponseDTO = mock(UserResponseDTO.class);

        when(userRepository.findById("dc0ce98e-9250-4915-8714-600178717a0b")).thenReturn(Optional.ofNullable(user));
        assertNotNull(user);
        when(userRepository.save(user)).thenReturn(user);

        ResponseDTO response = userService.updateUser(userResponseDTO, "dc0ce98e-9250-4915-8714-600178717a0b");
        assertEquals(response.getData(), user);

    }

    @Test
    public void testRemove() {

        when(userRepository.existsById("dc0ce98e-9250-4915-8714-600178717a0b")).thenReturn(true);

        ResponseDTO responseDTO = userService.removeUser("dc0ce98e-9250-4915-8714-600178717a0b");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("dc0ce98e-9250-4915-8714-600178717a0b", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

}
