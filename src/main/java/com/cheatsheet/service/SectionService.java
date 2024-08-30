package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.SectionDTO;
import com.cheatsheet.entity.Section;

import java.util.List;
import java.util.Optional;

public interface SectionService {
    List<Section> findAll();

    Optional<Section> findById(int id);

    ResponseDTO addNewSection(SectionDTO sectionDTO);

    ResponseDTO updateSection(int id, SectionDTO sectionDTO);

    ResponseDTO deleteSection(int id);
}
