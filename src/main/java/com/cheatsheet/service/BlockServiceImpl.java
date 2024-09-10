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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cheatsheet.service.CheatsheetServiceImpl.convertJsonToList;

@Service
public class BlockServiceImpl implements BlockService {
    @Autowired
    private CheatsheetRepository cheatsheetRepo;

    @Autowired
    private BlockRepository blockRepo;

    @Override
    public BlockDTO getBlockById(int id) {
        Block tempBlock = blockRepo.getBlockById(id);
        BlockDTO blockDTO = new BlockDTO();
        blockDTO.setId(tempBlock.getId());
        blockDTO.setTitle(tempBlock.getTitle());
        blockDTO.setNote(tempBlock.getNote());
        blockDTO.setContent(convertJsonToList(tempBlock.getContent()));
        return blockDTO;
    }

    @Override
    public ResponseDTO addNewBlock(CheatsheetReqDTO cheatsheetDTO) {
        Cheatsheet tempCheatsheet = cheatsheetRepo.findCheatsheetById(cheatsheetDTO.getId());
        ResponseDTO res = new ResponseDTO();
        Boolean isEdit = false;
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
                    isEdit = true;
                } else {
                    block = new Block();
                }
                block.setTitle(blockDTO.getTitle());
                block.setNote(blockDTO.getNote());
                try {
                    block.setContent(new ObjectMapper().writeValueAsString(blockDTO.getContent()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                block.setCheatsheet(tempCheatsheet);
                blockRepo.save(block);
            }
            if(isEdit) {
                res.setMessage("Block updated successful");
            } else {
                res.setMessage("Block added successful");
            }
            res.setStatus("200");
        }
        return res;
    }
}
