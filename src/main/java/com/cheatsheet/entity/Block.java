package com.cheatsheet.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Block extends Base {
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "cheatsheet_id")
    private Cheatsheet cheatsheet;

    @ManyToMany
    @JoinTable(
            name = "block_has_image",
            joinColumns = @JoinColumn(name = "block_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> blockImage;
}
