package com.cheatsheet.service;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Cheatsheet;

import java.util.List;

public interface CheatsheetService {
    ResponseDTO addCheatsheet(CheatsheetReqDTO cheatsheetDTO);
    ResponseDTO updateCheatsheet(int id, CheatsheetReqDTO cheatsheetDTO);
    List<CheatsheetDTO> findAllCheatsheets();
    List<Cheatsheet> getCheatsheetBySection(int sectionId);
    CheatsheetDTO findCheatsheetById(int id);
    ResponseDTO deleteCheatsheetById(int id);
    List<CheatsheetDTO> findCheatsheetsByUserId(int id);
}
