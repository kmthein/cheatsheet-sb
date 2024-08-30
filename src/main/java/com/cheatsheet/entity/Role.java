package com.cheatsheet.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Role {
    @Enumerated(EnumType.STRING)
    NONE,
    @Enumerated(EnumType.STRING)
    USER,
    @Enumerated(EnumType.STRING)
    ADMIN
}
