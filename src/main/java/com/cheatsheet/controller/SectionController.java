package com.cheatsheet.controller;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Section;
import com.cheatsheet.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SectionController {
    @Autowired
    private SectionService service;

    @GetMapping("/sections")
    public List<Section> getAllSections() {
        return service.findAll();
    }

    @GetMapping("/sections/{id}")
    public Optional<Section> getSectionById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping("/sections")
    public ResponseDTO addSection(@RequestBody Section section) {
        ResponseDTO res = service.addNewSection(section);
        return res;
    }

    @PutMapping("/sections/{id}")
    public ResponseDTO updateSection(@PathVariable int id, @RequestBody Section section) {
        ResponseDTO res = service.updateSection(id, section);
        return res;
    }

    @DeleteMapping("/sections/{id}")
    public ResponseDTO deleteSection(@PathVariable int id) {
        ResponseDTO res = service.deleteSection(id);
        return res;
    }
}
