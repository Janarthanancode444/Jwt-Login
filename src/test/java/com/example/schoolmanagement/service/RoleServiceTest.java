package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.RoleRequestDTO;
import com.example.schoolmanagement.entity.Role;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.RoleRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.RoleService;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private RoleService roleService;
    @Mock
    private AuthenticationService authenticationService;

    @Test

    public void testUser() {
        Role role = new Role();
        role.setName("ram");
        role.setDepartment("tamil");
        User user = new User();
        when(userRepository.findById("45875496")).thenReturn(Optional.of(user));
        role.setUser(user);
        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(role);
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
        roleRequestDTO.setName(user.getName());
        roleRequestDTO.setDepartment(role.getDepartment());
        roleRequestDTO.setUserId("45875496");
        ResponseDTO responseDTO = roleService.createRole(roleRequestDTO);

        Object data = responseDTO.getData();
        assertEquals(data, role);
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieveUser() {
        Role role = new Role();
        role.setName("ram");
        role.setDepartment("tamil");

        Role role1 = new Role();
        role1.setName("raja");
        role1.setDepartment("raj@gmail.com");
        when(roleRepository.findAll()).thenReturn(List.of(role, role1));

        ResponseDTO responseDTO = roleService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(roleRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testRemove() {
        when(roleRepository.existsById("dc0ce98e-9250-4915-8714-600178717a0b")).thenReturn(true);
        ResponseDTO responseDTO = roleService.remove("dc0ce98e-9250-4915-8714-600178717a0b");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("dc0ce98e-9250-4915-8714-600178717a0b", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }
}
