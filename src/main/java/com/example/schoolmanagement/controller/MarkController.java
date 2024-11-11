package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.MarkRequestDTO;
import com.example.schoolmanagement.dto.ResponseDTO;
import com.example.schoolmanagement.service.MarkService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mark")
public class MarkController {

    private final MarkService markService;

    public MarkController(final MarkService markService) {
        this.markService = markService;
    }

    @PostMapping("/create")
    public ResponseDTO createMark(@RequestBody final MarkRequestDTO markRequestDTO) {
        return this.markService.createMark(markRequestDTO);

    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieve() {
        return this.markService.retrieve();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateMark(@PathVariable(value = "id") final String id, @RequestBody final MarkRequestDTO markRequestDTO) {
        return this.markService.updateMark(id, markRequestDTO);

    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.markService.remove(id);
    }

    @GetMapping("/search/{mark}")
    public ResponseDTO findByMark(@PathVariable final Integer mark) {
        return this.markService.findByMark(mark);
    }

    @GetMapping("/search/ase")
    public ResponseDTO findAllOrderByMarkAsc() {
        return this.markService.OrderByMarkAsc();
    }

    @GetMapping("/search/desc")
    public ResponseDTO findAllOrderByMarkDesc() {
        return this.markService.OrderByMarkDesc();
    }


}
