package com.cheatsheet.service;

import com.cheatsheet.dto.BlockDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;

public interface BlockService {
    BlockDTO getBlockById(int id);
    ResponseDTO addNewBlock(CheatsheetReqDTO cheatsheetReqDTO);
}
