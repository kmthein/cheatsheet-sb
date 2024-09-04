package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Rate;

public interface RateService {
    ResponseDTO addNewRate(Rate rate);
}
