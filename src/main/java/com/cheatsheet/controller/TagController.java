package com.cheatsheet.controller;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.TagCountDTO;
import com.cheatsheet.dto.TagDTO;
import com.cheatsheet.entity.Tag;
import com.cheatsheet.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("section/{sectionName}")
    public List<TagCountDTO> getTagsBySection(@PathVariable("sectionName") String sectionName) {
        return tagService.getTopTagsBySection(sectionName);
    }

    @GetMapping("{tagName}/cheatsheets")
    public List<CheatsheetDTO> getCheatsheetsByTagId(@PathVariable("tagName") String tagName) {
        return tagService.getCheatsheetsByTag(tagName);
    }
}
