package com.cheatsheet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Section extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @ManyToMany
    @JoinTable(
            name = "section_has_image",
            joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> sectionImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Section parent;

    @JsonBackReference
    @OneToMany(mappedBy = "parent")
    private List<Section> children;
}
