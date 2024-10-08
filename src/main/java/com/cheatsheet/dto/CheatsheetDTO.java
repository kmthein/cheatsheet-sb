package com.cheatsheet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheatsheetDTO {
    private Integer id;
    private String name;
    private String description;
    private String color;
    private String style;
    private String type;
    private String language;
    private String layout;
    private List<BlockDTO> blocks;
    private List<TagDTO> tagList;
    private SectionDTO section;
    private UserDTO user;
}
