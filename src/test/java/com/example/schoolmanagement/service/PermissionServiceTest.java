package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.PermissionRequestDTO;
import com.example.schoolmanagement.dto.PermissionResponseDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.entity.Permission;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.PermissionRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.PermissionService;
import com.example.schoolmanagement.util.AuthenticationService;
import com.example.schoolmanagement.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {
    @InjectMocks
    private PermissionService permissionService;
    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void createPremissionTest() {

        PermissionRequestDTO permissionRequestDTO = new PermissionRequestDTO();
        permissionRequestDTO.setTeacherId("69459f89-5e3d-450f-a571-1d4b8555edc1");
        permissionRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.findById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(Optional.of(teacher));
        User user = mock(User.class);
        when(userRepository.findById("dc0ce98e-9250-4915-8714-600178717a0b")).thenReturn(Optional.of(user));
        Permission permission = new Permission();
        permission.setTeacher(teacher);
        permission.setUser(user);
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);
        ResponseDTO response = permissionService.createPermission(permissionRequestDTO);

        assertNotNull(response);
        Object data = response.getData();
        assertEquals(data, permission);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.CREATED.name(), response.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieve() {
        Teacher teacher = mock(Teacher.class);
        User user = mock(User.class);
        Permission permission = new Permission();
        permission.setUser(user);
        permission.setTeacher(teacher);


        when(permissionRepository.findAll()).thenReturn(List.of(permission));

        ResponseDTO responseDTO = permissionService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(1, ((List<?>) responseDTO.getData()).size());
        verify(permissionRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Permission permission = mock(Permission.class);
        PermissionResponseDTO permissionResponseDTO = mock(PermissionResponseDTO.class);

        when(permissionRepository.findById("dc0ce98e-9250-4915-8714-600178717a0b")).thenReturn(Optional.ofNullable(permission));
        assertNotNull(permission);
        when(permissionRepository.save(permission)).thenReturn(permission);

        ResponseDTO response = permissionService.update("dc0ce98e-9250-4915-8714-600178717a0b", permissionResponseDTO);
        assertEquals(response.getData(), permission);
        Object data = response.getData();
        assertEquals(data, permission);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());


    }

    @Test
    public void testRemove() {

        when(permissionRepository.existsById("201234")).thenReturn(true);

        ResponseDTO responseDTO = permissionService.remove("201234");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("201234", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

}
