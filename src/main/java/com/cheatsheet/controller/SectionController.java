package com.cheatsheet.controller;

import com.cheatsheet.entity.Section;
import com.cheatsheet.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
