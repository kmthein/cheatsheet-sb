package com.cheatsheet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Data
public class Tag extends Base {
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Cheatsheet> cheatsheets;
}
