package com.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.StandardRequestDTO;
import com.example.schoolmanagement.dto.StandardResponseDTO;
import com.example.schoolmanagement.entity.School;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.SchoolRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.StandardService;
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
public class StandardServiceTest {
    @Mock
    private StandardRepository standardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @InjectMocks
    private StandardService standardService;
    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void createStandard() {
        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setName("6th");
        standardRequestDTO.setTotalStudent(56);
        standardRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        standardRequestDTO.setSchoolId("4979f417-6190-45ec-ade9-5932f0830882");

        Standard standard = new Standard();
        standard.setName(standardRequestDTO.getName());
        standard.setTotalStudent(standardRequestDTO.getTotalStudent());
        User user = mock(User.class);
        when(userRepository.findById(standardRequestDTO.getUserId())).thenReturn(Optional.of(user));
        standard.setUser(user);
        School school = mock(School.class);
        when(schoolRepository.findById(standardRequestDTO.getSchoolId())).thenReturn(Optional.of(school));
        standard.setSchool(school);

        when(standardRepository.save(Mockito.any(Standard.class))).thenReturn(standard);

        ResponseDTO responseDTO = standardService.create(standardRequestDTO);

        Object data = responseDTO.getData();
        assertEquals(data, standard);
        assertEquals(Constants.REQUEST, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieveStandard() {
        Standard standard = new Standard();
        standard.setName("10th");
        standard.setSchool(new School());
        standard.setTotalStudent(10);
        standard.setUser(new User());

        Standard standard1 = new Standard();
        standard1.setName("RVB");
        standard1.setTotalStudent(20);
        standard1.setUser(new User());
        standard1.setSchool(new School());

        when(standardRepository.findAll()).thenReturn(List.of(standard, standard1));

        ResponseDTO responseDTO = standardService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(standardRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Standard standard = mock(Standard.class);
        StandardResponseDTO standardResponseDTO = mock(StandardResponseDTO.class);

        when(standardRepository.findById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(Optional.ofNullable(standard));
        assertNotNull(standard);
        when(standardRepository.save(standard)).thenReturn(standard);

        ResponseDTO response = standardService.update("69459f89-5e3d-450f-a571-1d4b8555edc1", standardResponseDTO);
        Object data = response.getData();
        assertEquals(data, standard);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());
    }

    @Test
    public void testRemove() {

        when(standardRepository.existsById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(true);

        ResponseDTO responseDTO = standardService.remove("69459f89-5e3d-450f-a571-1d4b8555edc1");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("69459f89-5e3d-450f-a571-1d4b8555edc1", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());

    }

}
