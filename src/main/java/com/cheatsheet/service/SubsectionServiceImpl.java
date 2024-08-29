package com.cheatsheet.service;

import com.cheatsheet.entity.Subsection;
import com.cheatsheet.repository.SubsectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubsectionServiceImpl implements SubsectionService {
    @Autowired
    private SubsectionRepository repo;
    @Override
    public List<Subsection> getAllSubsections() {
        return repo.findAllSubsections();
    }

    @Override
    public Subsection findById(int id) {
        Optional<Subsection> temp = repo.findSubsectionById(id);
        Subsection subsection = null;
        if(temp.isPresent()) {
            subsection = temp.get();
        }
        return subsection;
    }
}
