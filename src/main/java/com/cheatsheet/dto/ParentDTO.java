package com.cheatsheet.dto;

import com.cheatsheet.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDTO {
    private int id;
    private String name;
    private SectionDTO parent;

    public ParentDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
