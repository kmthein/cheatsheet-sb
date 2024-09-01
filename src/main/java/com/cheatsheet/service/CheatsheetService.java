package com.cheatsheet.service;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Cheatsheet;

import java.util.List;

public interface CheatsheetService {
    ResponseDTO addCheatsheet(CheatsheetDTO cheatsheetDTO);
    ResponseDTO updateCheatsheet(int id, CheatsheetDTO cheatsheetDTO);
    List<CheatsheetDTO> findAllCheatsheets();
    List<Cheatsheet> getCheatsheetBySection(int sectionId);
    CheatsheetDTO findCheatsheetById(int id);
    ResponseDTO deleteCheatsheetById(int id);
}
