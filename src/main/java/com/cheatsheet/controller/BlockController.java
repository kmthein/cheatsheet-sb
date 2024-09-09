package com.cheatsheet.controller;

import com.cheatsheet.dto.BlockDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public BlockDTO getBlockById(@PathVariable int id) {
        return blockService.getBlockById(id);
    }
}
