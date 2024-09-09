package com.cheatsheet.controller;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.UserDTO;
import com.cheatsheet.entity.Cheatsheet;
import com.cheatsheet.entity.User;
import com.cheatsheet.service.CheatsheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private CheatsheetService cheatsheetService;

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping("{id}/cheatsheets")
    public List<CheatsheetDTO> getCheatsheetsByUserId(@PathVariable("id") int id) {
        return cheatsheetService.findCheatsheetsByUserId(id);
    }

}
