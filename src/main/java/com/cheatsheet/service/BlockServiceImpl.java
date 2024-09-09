package com.cheatsheet.service;

import com.cheatsheet.dto.BlockDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Block;
import com.cheatsheet.entity.Cheatsheet;
import com.cheatsheet.repository.BlockRepository;
import com.cheatsheet.repository.CheatsheetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlockServiceImpl implements BlockService {
    @Autowired
    private CheatsheetRepository cheatsheetRepo;

    @Autowired
    private BlockRepository blockRepo;

    @Override
    public ResponseDTO addNewBlock(CheatsheetReqDTO cheatsheetDTO) {
        Cheatsheet tempCheatsheet = cheatsheetRepo.findCheatsheetById(cheatsheetDTO.getId());
        ResponseDTO res = new ResponseDTO();
        if(tempCheatsheet == null) {
            res.setStatus("404");
            res.setMessage("Cheatsheet is not found");
            return res;
        } else {
            cheatsheetRepo.save(tempCheatsheet);
            for(BlockDTO blockDTO: cheatsheetDTO.getBlocks()) {
                Optional<Block> tempBlock = blockRepo.findById(blockDTO.getId());
                Block block = null;
                if(tempBlock.isPresent()) {
                    block = blockRepo.findById(blockDTO.getId()).get();
                } else {
                    block = new Block();
                }
                block.setTitle(blockDTO.getTitle());
                try {
                    block.setContent(new ObjectMapper().writeValueAsString(blockDTO.getContent()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                block.setCheatsheet(tempCheatsheet);
                blockRepo.save(block);
            }
            res.setStatus("200");
            res.setMessage("Block added successful");
        }
        return res;
    }
}
