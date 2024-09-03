package com.cheatsheet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cheatsheet extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "style")
    private String style;

    @Column(name = "type")
    private String type;

    @Column(name = "language")
    private String language;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "cheatsheet")
    private List<Block> blockList;
}
