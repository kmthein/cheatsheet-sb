package com.cheatsheet.controller;

import com.cheatsheet.entity.Section;
import com.cheatsheet.entity.Subsection;
import com.cheatsheet.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SubsectionController {
    @Autowired
    private SubsectionService service;

    @GetMapping("/subsections")
    public List<Subsection> getAllSubsections() {
        return service.getAllSubsections();
    }

    @GetMapping("/subsections/{id}")
    public Subsection getSubsectionById(@PathVariable("id") int id) {
        return service.findById(id);
    }
}
