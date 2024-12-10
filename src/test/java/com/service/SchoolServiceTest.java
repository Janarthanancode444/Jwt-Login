package com.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolRequestDTO;
import com.example.schoolmanagement.dto.SchoolResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.SchoolService;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private SchoolService schoolService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testSchoolCreate() {
        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName("Jana");
        schoolRequestDTO.setEmail("jana@gmail.com");
        schoolRequestDTO.setPhone("567-896-4523");
        schoolRequestDTO.setCreatedBy(authenticationService.getCurrentUser());
        schoolRequestDTO.setUpdatedBy(authenticationService.getCurrentUser());
        schoolRequestDTO.setUserId("605");


        School school = new School();
        school.setName(schoolRequestDTO.getName());
        school.setEmail(schoolRequestDTO.getEmail());
        school.setPhone(schoolRequestDTO.getPhone());

        User user = mock(User.class);
        when(userRepository.findById(schoolRequestDTO.getUserId())).thenReturn(Optional.of(user));
        school.setUser(user);
        when(schoolRepository.save(Mockito.any(School.class))).thenReturn(school);

        ResponseDTO responseDTO = schoolService.createSchool(schoolRequestDTO);

        Object data = responseDTO.getData();
        assertEquals(data, school);
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieve() {
        School school1 = new School();
        school1.setName("SRM");
        school1.setEmail("srm@gmail.com");

        School school2 = new School();
        school2.setName("RVB");
        school2.setEmail("rvb@gmail.com");

        when(schoolRepository.findAll()).thenReturn(List.of(school1, school2));

        ResponseDTO responseDTO = schoolService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(schoolRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        School school = mock(School.class);
        SchoolResponseDTO schoolResponseDTO = mock(SchoolResponseDTO.class);

        when(schoolRepository.findById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(Optional.ofNullable(school));
        assertNotNull(school);
        when(schoolRepository.save(school)).thenReturn(school);

        ResponseDTO response = schoolService.update(schoolResponseDTO, "69459f89-5e3d-450f-a571-1d4b8555edc1");
        Object data = response.getData();
        assertEquals(data, school);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());

        //assertEquals(response.getData(), school);

    }

    @Test
    public void testRemove() {

        when(schoolRepository.existsById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(true);

        ResponseDTO responseDTO = schoolService.remove("69459f89-5e3d-450f-a571-1d4b8555edc1");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("69459f89-5e3d-450f-a571-1d4b8555edc1", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }
}
