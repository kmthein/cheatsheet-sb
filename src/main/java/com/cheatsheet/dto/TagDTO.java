package com.cheatsheet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private int id;
    private String name;
    private List<CheatsheetDTO> cheatsheetList;
}
