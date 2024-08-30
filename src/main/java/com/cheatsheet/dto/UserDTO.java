package com.cheatsheet.dto;

import com.cheatsheet.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String description;
    private String website;
    private Role role;
    private String imgUrl;
}
