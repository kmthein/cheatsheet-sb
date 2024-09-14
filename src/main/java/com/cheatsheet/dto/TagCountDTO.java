package com.cheatsheet.dto;

import lombok.Data;

@Data
public class TagCountDTO {
    private Integer id;
    private String name;
    private Long totalCount;

    public TagCountDTO(Integer id, String name, Long totalCount) {
        this.id = id;
        this.name = name;
        this.totalCount = totalCount;
    }
}
