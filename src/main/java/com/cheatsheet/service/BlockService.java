package com.cheatsheet.service;

import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;

public interface BlockService {
    ResponseDTO addNewBlock(CheatsheetReqDTO cheatsheetReqDTO);
}
