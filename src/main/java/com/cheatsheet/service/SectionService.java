package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.SectionDTO;

import java.util.List;

public interface SectionService {
    List<SectionDTO> findAll();

    SectionDTO findById(int id);

    ResponseDTO addNewSection(SectionDTO sectionDTO);

    ResponseDTO updateSection(int id, SectionDTO sectionDTO);

    ResponseDTO deleteSection(int id);
}
