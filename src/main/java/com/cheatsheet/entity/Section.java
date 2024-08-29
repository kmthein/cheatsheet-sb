package com.cheatsheet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Section extends Base {
    @Column(name = "name")
    private String name;
}
