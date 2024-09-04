package com.cheatsheet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Rate extends Base {
    @NotNull(message = "Count needed to choose")
    @Column(name = "count")
    private Integer count;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
