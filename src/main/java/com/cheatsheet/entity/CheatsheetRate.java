package com.cheatsheet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CheatsheetRate extends Base {
    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
