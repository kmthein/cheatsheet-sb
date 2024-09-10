package com.cheatsheet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockDTO {
    private int id;
    private String title;
    private String note;
    private List<List<String>> content;
}
