package com.cheatsheet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheatsheetReqDTO {
    private Integer id;
    private String name;
    private String description;
    private String color;
    private String style;
    private String type;
    private String language;
    private String layout;
    private List<BlockDTO> blocks;
    private Integer sectionId;
    private Integer userId;
    private List<String> tagList;
}
