package com.cheatsheet.service;

import com.cheatsheet.entity.Section;
import com.cheatsheet.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionRepository repo;

    @Override
    public List<Section> findAll() {
        List<Section> sectionList = repo.findAll();
        return sectionList;
    }

    @Override
    public Optional<Section> findById(int id) {
        return repo.findById(id);
    }
}
