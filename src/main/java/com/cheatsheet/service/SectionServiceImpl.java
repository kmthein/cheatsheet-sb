package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.SectionDTO;
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
    public ResponseDTO addNewSection(SectionDTO sectionDTO) {
        ResponseDTO res = new ResponseDTO();
        Optional<Section> sectionExist = repo.findByName(sectionDTO.getName());
        if(sectionExist.isPresent()) {
            res.setStatus("409");
            res.setMessage("Section name already existed");
            return res;
        }
        Section section = new Section();
        Optional<Section> parentSection = repo.findSectionById(sectionDTO.getParentId());
        if(parentSection.isPresent()) {
            section.setParent(parentSection.get());
        }
        section.setName(sectionDTO.getName());
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
    public ResponseDTO updateSection(int id, SectionDTO sectionDTO) {
        ResponseDTO res = new ResponseDTO();
        Optional<Section> sectionExist = repo.findById(id);
        if(sectionExist.isEmpty()) {
            res.setStatus("404");
            res.setMessage("Section was not found");
            return res;
        }
        Optional<Section> nameExist = repo.findByName(sectionDTO.getName());
        if(nameExist.isPresent()) {
            if(!sectionExist.get().getName().equals(nameExist.get().getName())) {
                res.setStatus("409");
                res.setMessage("Section name already existed");
                return res;
            }
        }
        Section section = new Section();
        section.setId(id);
        section.setName(sectionDTO.getName());
        section.setCreatedAt(sectionExist.get().getCreatedAt());
        Optional<Section> parentSection = repo.findSectionById(sectionDTO.getParentId());
        if(parentSection.isPresent()) {
            section.setParent(parentSection.get());
        }
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
        if(tempSection.isEmpty()) {
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
