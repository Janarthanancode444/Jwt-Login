package com.service;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SectionRequestDTO;
import com.example.schoolmanagement.dto.SectionResponseDTO;
import com.example.schoolmanagement.entity.Section;
import com.example.schoolmanagement.entity.Standard;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repository.SectionRepository;
import com.example.schoolmanagement.repository.StandardRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.service.SectionService;
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
public class SectionServiceTest {
    @Mock
    private StandardRepository standardRepository;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private SectionRepository sectionRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private SectionService sectionService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void createSectionTest() {
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setSection("A");
        sectionRequestDTO.setStandardId("7cfb59be-2749-4ede-9246-60a148401d21");
        sectionRequestDTO.setUserId("dc0ce98e-9250-4915-8714-600178717a0b");
        sectionRequestDTO.setTeacherId("f5d4f862-d04c-470e-a34b-178edb793a7d");
        Section section = new Section();
        section.setSection(sectionRequestDTO.getSection());
        User user = mock(User.class);
        when(userRepository.findById(sectionRequestDTO.getUserId())).thenReturn(Optional.of(user));
        section.setUser(user);
        Standard standard = mock(Standard.class);
        when(standardRepository.findById(sectionRequestDTO.getStandardId())).thenReturn(Optional.of(standard));
        section.setStandard(standard);
        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.findById(sectionRequestDTO.getTeacherId())).thenReturn(Optional.of(teacher));
        section.setTeacher(teacher);
        when(sectionRepository.save(Mockito.any(Section.class))).thenReturn(section);
        ResponseDTO responseDTO = sectionService.createSection(sectionRequestDTO);
        Object data = responseDTO.getData();
        assertEquals(data, section);
        assertEquals(Constants.REQUEST, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.CREATED.name(), responseDTO.getStatusValue().toUpperCase());
    }

    @Test
    public void retrieve() {
        Section section = new Section();
        section.setSection("A");
        section.setStandard(new Standard());
        section.setTeacher(new Teacher());
        section.setUser(new User());

        Section section1 = new Section();
        section1.setSection("B");
        section1.setStandard(new Standard());
        section1.setTeacher(new Teacher());
        section1.setUser(new User());
        when(sectionRepository.findAll()).thenReturn(List.of(section, section1));

        ResponseDTO responseDTO = sectionService.retrieve();

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);
        assertEquals(2, ((List<?>) responseDTO.getData()).size());
        verify(sectionRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {

        Section section = mock(Section.class);
        SectionResponseDTO sectionResponseDTO = mock(SectionResponseDTO.class);

        when(sectionRepository.findById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(Optional.ofNullable(section));
        assertNotNull(section);
        when(sectionRepository.save(section)).thenReturn(section);

        ResponseDTO response = sectionService.updateSection(sectionResponseDTO, "69459f89-5e3d-450f-a571-1d4b8555edc1");
        Object data = response.getData();
        assertEquals(data, section);
        assertEquals(Constants.REQUEST, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(HttpStatus.OK.name(), response.getStatusValue().toUpperCase());
    }

    @Test
    public void testRemove() {

        when(sectionRepository.existsById("69459f89-5e3d-450f-a571-1d4b8555edc1")).thenReturn(true);

        ResponseDTO responseDTO = sectionService.remove("69459f89-5e3d-450f-a571-1d4b8555edc1");
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals("69459f89-5e3d-450f-a571-1d4b8555edc1", responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }
}
