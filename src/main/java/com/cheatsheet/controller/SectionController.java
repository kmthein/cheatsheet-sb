package com.cheatsheet.controller;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.SectionDTO;
import com.cheatsheet.entity.Section;
import com.cheatsheet.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sections")
@CrossOrigin
public class SectionController {
    @Autowired
    private SectionService service;

    @GetMapping("")
    public List<SectionDTO> getAllSections() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Object getSectionById(@PathVariable("id") int id) {
        SectionDTO sectionDTO = service.findById(id);
        if(sectionDTO == null) {
            ResponseDTO res = new ResponseDTO("Section not found");
            return res;
        }
        return sectionDTO;
    }

    @PostMapping("")
    public ResponseDTO addSection(@RequestBody SectionDTO sectionDTO) {
        ResponseDTO res = service.addNewSection(sectionDTO);
        return res;
    }

    @PutMapping("{id}")
    public ResponseDTO updateSection(@PathVariable int id, @RequestBody SectionDTO sectionDTO) {
        ResponseDTO res = service.updateSection(id, sectionDTO);
        return res;
    }

    @DeleteMapping("{id}")
    public ResponseDTO deleteSection(@PathVariable int id) {
        ResponseDTO res = service.deleteSection(id);
        return res;
    }
}
