package com.cheatsheet.dto;

import com.cheatsheet.entity.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionDTO {
    private int id;
    private String name;
    private Integer parentId;
    private Integer userId;
    private UserDTO user;
    private ParentDTO parent;
}
