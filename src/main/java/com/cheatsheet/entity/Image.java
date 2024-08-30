package com.cheatsheet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image extends Base {
    @Column(name = "img_url")
    private String imgUrl;

    @ManyToMany(mappedBy = "userImage")
    private List<User> users;
}
