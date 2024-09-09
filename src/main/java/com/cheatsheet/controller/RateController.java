package com.cheatsheet.controller;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Rate;
import com.cheatsheet.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @PostMapping("")
    private ResponseDTO addNewRate(@RequestBody Rate rate) {
        ResponseDTO res = new ResponseDTO();
        if(rate.getCount() == null) {
            res.setStatus("404");
            res.setMessage("Count can't be empty");
        } else {
            res = rateService.addNewRate(rate);
        }
        return res;
    }
}
