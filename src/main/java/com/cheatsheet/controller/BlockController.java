package com.cheatsheet.controller;

import com.cheatsheet.dto.BlockDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/blocks")
@CrossOrigin
public class BlockController {
    @Autowired
    private BlockService blockService;

    @PostMapping("")
    public ResponseDTO addNewBlock(@RequestBody CheatsheetReqDTO cheatsheetReqDTO) {
        return blockService.addNewBlock(cheatsheetReqDTO);
    }

    @PostMapping("image")
    public ResponseDTO addImageBlock(@RequestPart("title") String title,
                                     @RequestPart(value = "note", required = false) String note,
                                     @RequestPart("id") String id,
                                     @RequestPart(value = "image", required = false) MultipartFile image) {
        return blockService.addImageBlock(title, note, id, image);
    }

    @GetMapping("{id}")
    public BlockDTO getBlockById(@PathVariable int id) {
        return blockService.getBlockById(id);
    }
}
