package com.cheatsheet.dto;

import lombok.Data;

@Data
public class RateReqDTO {
    private Double count;
    private String description;
    private int cheatsheetId;
    private int userId;
}
