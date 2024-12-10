package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.dto.SchoolRequestDTO;
import com.example.schoolmanagement.dto.SchoolResponseDTO;
import com.example.schoolmanagement.service.SchoolService;
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
@RequestMapping("/api/school")
public class SchoolController {
    private final SchoolService schoolService;

    public SchoolController(final SchoolService schoolService) {
        this.schoolService = schoolService;
    }


    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SchoolRequestDTO schoolRequestDTO) {
        return this.schoolService.createSchool(schoolRequestDTO);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.schoolService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@RequestBody final SchoolResponseDTO responseSchoolDTO, @PathVariable final String id) {
        return this.schoolService.update(responseSchoolDTO, id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable(value = "id") final String id) {
        return this.schoolService.remove(id);
    }

    @GetMapping("/search")
    public ResponseDTO searchSchool(@RequestParam(required = false) String search, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false, defaultValue = "name") String sortField, @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return this.schoolService.searchSchool(search, page, size, sortField, sortDirection);
    }
}
