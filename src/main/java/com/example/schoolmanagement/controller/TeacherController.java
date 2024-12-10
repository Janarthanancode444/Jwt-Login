package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.TeacherRequestDTO;
import com.example.schoolmanagement.dto.TeacherResponseDTO;
import com.example.schoolmanagement.service.TeacherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(final TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create")
    public ResponseDTO createStaff(@RequestBody final TeacherRequestDTO teacherRequestDTO) {
        return this.teacherService.createStaff(teacherRequestDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.teacherService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateStaff(@RequestBody final TeacherResponseDTO staffResponseDTO, @PathVariable(value = "id") final String id) {
        return this.teacherService.updateStaff(staffResponseDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable(value = "id") final String id) {
        return this.teacherService.remove(id);
    }

    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(required = false) final String school, @RequestParam(required = false) final String subject, @RequestParam(required = false) final String range, @RequestParam(required = false) Boolean lesserThan) {
        return this.teacherService.search(school, subject, range, lesserThan);
    }
}

