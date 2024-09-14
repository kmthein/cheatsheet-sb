package com.cheatsheet.service;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.TagCountDTO;
import com.cheatsheet.dto.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTags();
    List<TagCountDTO> getTopTagsBySection(String sectionName);
    List<CheatsheetDTO> getCheatsheetsByTag(String tagName);
}
