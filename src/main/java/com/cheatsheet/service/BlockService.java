package com.cheatsheet.service;

import com.cheatsheet.dto.BlockDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface BlockService {
    BlockDTO getBlockById(int id);
    ResponseDTO addNewBlock(CheatsheetReqDTO cheatsheetReqDTO);
    ResponseDTO addImageBlock(String title, String note, String id, MultipartFile image);
}
