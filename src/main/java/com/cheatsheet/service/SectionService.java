package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Section;

import java.util.List;
import java.util.Optional;

public interface SectionService {
    List<Section> findAll();

    Optional<Section> findById(int id);

    ResponseDTO addNewSection(Section section);

    ResponseDTO updateSection(int id, Section section);

    ResponseDTO deleteSection(int id);
}
