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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cheatsheet.service.CheatsheetServiceImpl.convertJsonToList;

@Service
public class BlockServiceImpl implements BlockService {
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads";

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
        blockDTO.setImgUrl(tempBlock.getImgUrl());
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

    @Override
    public ResponseDTO addImageBlock(String title, String note, String id, MultipartFile image) {
        Cheatsheet tempCheatsheet = cheatsheetRepo.findCheatsheetById(Integer.parseInt(id));
        ResponseDTO res = new ResponseDTO();
        try {
            Block tempBlock = new Block();
            tempBlock.setTitle(title);
            tempBlock.setNote(note);
            tempBlock.setContent("[]");
            if(tempCheatsheet != null) {
                tempBlock.setCheatsheet(tempCheatsheet);
            }
            if(image != null && !image.isEmpty()) {
                // Get the original file name
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

                // Ensure the directory exists
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs(); // Create the directory if it doesn't exist
                }

                // Construct the path to save the file
                Path path = Paths.get(UPLOAD_DIR + "/" + fileName);

                // Save the file locally
                Files.write(path, image.getBytes());

                System.out.println("File saved at: " + path.toString());
                tempBlock.setImgUrl("uploads\\" + fileName);
                blockRepo.save(tempBlock);
                res.setMessage("Block with image added successful");
            }
        } catch (IOException e) {
            e.printStackTrace();
            res.setMessage("Block add failed");
        }
        return res;
    }
}
