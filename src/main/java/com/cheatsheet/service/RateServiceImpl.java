package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.Rate;
import com.cheatsheet.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateRepository rateRepo;

    @Override
    public ResponseDTO addNewRate(Rate rate) {
        ResponseDTO res = new ResponseDTO();
        Rate rateExist = rateRepo.findByCount(rate.getCount());
        if(rateExist != null) {
            res.setStatus("409");
            res.setMessage("Rate count can't be duplicate");
            return res;
        }
        Rate tempRate = rateRepo.save(rate);
        if(res == null) {
            res.setStatus("401");
            res.setMessage("Rate adding failed");
        } else {
            res.setStatus("201");
            res.setMessage("Rate adding successful");
        }
        return res;
    }
}
