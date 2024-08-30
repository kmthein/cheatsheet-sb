package com.cheatsheet.dto;

import lombok.Data;

@Data
public class SectionDTO {
    private int id;
    private String name;
    private int parentId;
}
