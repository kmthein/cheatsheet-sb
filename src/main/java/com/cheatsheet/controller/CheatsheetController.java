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
@RequestMapping("/api/cheatsheets")
@CrossOrigin
public class CheatsheetController {
    @Autowired
    private CheatsheetService cheatsheetService;

    @PostMapping("name")
    public ResponseDTO addNewCheatsheet(@RequestBody CheatsheetReqDTO cheatsheetReqDTO) {
        return cheatsheetService.addNewCheatsheet(cheatsheetReqDTO);
    }

    @PostMapping("")
    public ResponseDTO addCheatsheet(@RequestBody CheatsheetReqDTO cheatsheetDTO) {
        return cheatsheetService.addCheatsheet(cheatsheetDTO);
    }

    @PutMapping ("{id}")
    public ResponseDTO updateCheatsheet(@PathVariable("id") Integer id, @RequestBody CheatsheetReqDTO cheatsheetDTO) {
        return cheatsheetService.updateCheatsheet(id, cheatsheetDTO);
    }

    @GetMapping("")
    public List<CheatsheetDTO> getAllCheatsheets() {
        return cheatsheetService.findAllCheatsheets();
    }

    @GetMapping("{id}")
    public CheatsheetDTO getCheatsheetById(@PathVariable("id") int id) {
        return cheatsheetService.findCheatsheetById(id);
    }

    @DeleteMapping("{id}")
    public ResponseDTO deleteCheatsheetById(@PathVariable("id") int id) {
        return cheatsheetService.deleteCheatsheetById(id);
    }

    @GetMapping("section/{name}")
    public List<CheatsheetDTO> getCheatsheetBySection(@PathVariable("name") String name) {
        return cheatsheetService.getCheatsheetBySection(name);
    }
}
