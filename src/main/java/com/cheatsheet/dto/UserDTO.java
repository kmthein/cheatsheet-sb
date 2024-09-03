package com.cheatsheet.dto;

import com.cheatsheet.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String description;
    private String website;
    private Role role;
    private String imgUrl;
}
