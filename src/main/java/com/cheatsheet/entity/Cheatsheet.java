package com.cheatsheet.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cheatsheet extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "style")
    private String style;

    @Column(name = "type")
    private String type;

    @Column(name = "language")
    private String language;

    @Column(name = "layout")
    private String layout;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "cheatsheet")
    private List<Block> blockList;

    @ManyToMany
    @JoinTable(
            name = "cheatsheet_has_tag",
            joinColumns = @JoinColumn(name = "cheatsheet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private List<Tag> tags;
}
