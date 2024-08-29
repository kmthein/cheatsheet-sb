package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
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
        List<Section> sectionList = repo.getAllSections();
        return sectionList;
    }

    @Override
    public Optional<Section> findById(int id) {
        return repo.findSectionById(id);
    }

    @Override
    public ResponseDTO addNewSection(Section section) {
        ResponseDTO res = new ResponseDTO();
        Optional<Section> sectionExist = repo.findByName(section.getName());
        if(sectionExist != null) {
            res.setStatus("409");
            res.setMessage("Section name already existed");
            return res;
        }
        Section tempSection = repo.save(section);
        if(tempSection == null) {
            res.setStatus("403");
            res.setMessage("Section add failed.");
        } else {
            res.setStatus("200");
            res.setMessage("Section add done");
        }
        return res;
    }

    @Override
    public ResponseDTO updateSection(int id, Section section) {
        ResponseDTO res = new ResponseDTO();
        Optional<Section> sectionExist = repo.findById(id);
        if(sectionExist == null || sectionExist.isEmpty()) {
            res.setStatus("404");
            res.setMessage("Section was not found");
            return res;
        }
        Optional<Section> nameExist = repo.findByName(section.getName());
        if(nameExist.isPresent()) {
            res.setStatus("409");
            res.setMessage("Section name already existed");
            return res;
        }
        section.setId(id);
        Section tempSection = repo.save(section);
        if(tempSection == null) {
            res.setStatus("403");
            res.setMessage("Section update failed.");
        } else {
            res.setStatus("200");
            res.setMessage("Section update done");
        }
        return res;
    }

    @Override
    public ResponseDTO deleteSection(int id) {
        Optional<Section> tempSection = repo.findById(id);
        ResponseDTO res = new ResponseDTO();
        if(tempSection.isEmpty() || tempSection == null) {
            res.setStatus("404");
            res.setMessage("Section was not found");
            return res;
        } else {
            Section sectionExist = tempSection.get();
            sectionExist.setIsDeleted(true);
            repo.save(sectionExist);
            res.setStatus("204");
            res.setMessage("Section deleted");
        }
        return res;
    }
}
