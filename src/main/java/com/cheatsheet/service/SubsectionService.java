package com.cheatsheet.service;

import com.cheatsheet.entity.Subsection;

import java.util.List;

public interface SubsectionService {
    List<Subsection> getAllSubsections();

    Subsection findById(int id);
}
