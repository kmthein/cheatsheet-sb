package com.cheatsheet.controller;

import com.cheatsheet.dto.TagDTO;
import com.cheatsheet.entity.Tag;
import com.cheatsheet.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("")
    public List<TagDTO> getAllTags() {
        return tagService.getAllTags();
    }
}
