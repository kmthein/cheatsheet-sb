package com.cheatsheet.controller;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.CheatsheetReqDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Cheatsheet;
import com.cheatsheet.service.CheatsheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CheatsheetController {
    @Autowired
    private CheatsheetService cheatsheetService;

    @PostMapping("/cheatsheets/name")
    public ResponseDTO addNewCheatsheet(@RequestBody CheatsheetReqDTO cheatsheetReqDTO) {
        return cheatsheetService.addNewCheatsheet(cheatsheetReqDTO);
    }

    @PostMapping("/cheatsheets")
    public ResponseDTO addCheatsheet(@RequestBody CheatsheetReqDTO cheatsheetDTO) {
        return cheatsheetService.addCheatsheet(cheatsheetDTO);
    }

    @PutMapping ("/cheatsheets/{id}")
    public ResponseDTO updateCheatsheet(@PathVariable("id") Integer id, @RequestBody CheatsheetReqDTO cheatsheetDTO) {
        return cheatsheetService.updateCheatsheet(id, cheatsheetDTO);
    }

    @GetMapping("/cheatsheets")
    public List<CheatsheetDTO> getAllCheatsheets() {
        return cheatsheetService.findAllCheatsheets();
    }

    @GetMapping("/cheatsheets/{id}")
    public CheatsheetDTO getCheatsheetById(@PathVariable("id") int id) {
        return cheatsheetService.findCheatsheetById(id);
    }

    @DeleteMapping("/cheatsheets/{id}")
    public ResponseDTO deleteCheatsheetById(@PathVariable("id") int id) {
        return cheatsheetService.deleteCheatsheetById(id);
    }

    @GetMapping("/cheatsheets/section/{id}")
    public List<Cheatsheet> getCheatsheetBySection(@PathVariable("id") int id) {
        return cheatsheetService.getCheatsheetBySection(id);
    }
}
