package com.cheatsheet.service;

import com.cheatsheet.dto.ParentDTO;
import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.SectionDTO;
import com.cheatsheet.dto.UserDTO;
import com.cheatsheet.entity.Section;
import com.cheatsheet.entity.User;
import com.cheatsheet.exception.ResourceNotFoundException;
import com.cheatsheet.exception.ResourceNotFoundExceptionHandler;
import com.cheatsheet.repository.SectionRepository;
import com.cheatsheet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<SectionDTO> findAll() {
        List<Section> sectionList = sectionRepo.getAllSections();
        List<SectionDTO> sectionDTOList = new ArrayList<>();
        for(Section section: sectionList) {
            SectionDTO sectionDTO = mapper.map(section, SectionDTO.class);
            sectionDTO.setUserId(null);
            sectionDTO.setParentId(null);
            UserDTO userDTO = mapper.map(section.getUser(), UserDTO.class);
            sectionDTO.setUser(userDTO);
            if(section.getParent() != null) {
                ParentDTO parentDTO = new ParentDTO(section.getParent().getId(), section.getParent().getName());
                sectionDTO.setParent(parentDTO);
            }
            sectionDTOList.add(sectionDTO);
        }
        return sectionDTOList;
    }

    @Override
    public SectionDTO findById(int id) {
        Section section = null;
        SectionDTO sectionDTO = new SectionDTO();
        Optional<Section> tempSection = sectionRepo.findSectionById(id);
        if (tempSection.isEmpty() || tempSection == null) {
            throw new ResourceNotFoundException("Section not found");
        } else if (tempSection.isPresent()) {
            section = tempSection.get();
            sectionDTO = mapper.map(section, SectionDTO.class);
            sectionDTO.setUserId(null);
            sectionDTO.setParentId(null);
            UserDTO userDTO = mapper.map(section.getUser(), UserDTO.class);
            sectionDTO.setUser(userDTO);
            if (section.getParent() != null) {
                ParentDTO parentDTO = new ParentDTO(section.getParent().getId(), section.getParent().getName());
                sectionDTO.setParent(parentDTO);
            }
        }
        return sectionDTO;
    }

    @Override
    public ResponseDTO addNewSection(SectionDTO sectionDTO) {
        ResponseDTO res = new ResponseDTO();
        Optional<Section> sectionExist = sectionRepo.findByName(sectionDTO.getName());
        if(sectionExist.isPresent()) {
            res.setStatus("409");
            res.setMessage("Section name already existed");
            return res;
        }
        Section section = new Section();
        Optional<Section> parentSection = sectionRepo.findSectionById(sectionDTO.getParentId());
        if(parentSection.isPresent()) {
            section.setParent(parentSection.get());
        }
        Optional<User> user = userRepo.findById(sectionDTO.getUserId());
        if(user.isPresent()) {
            section.setUser(user.get());
        } else {
            res.setStatus("404");
            res.setMessage("User not found");
            return res;
        }
        section.setName(sectionDTO.getName());
        Section tempSection = sectionRepo.save(section);
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
        Optional<Section> sectionExist = sectionRepo.findById(id);
        if(sectionExist.isEmpty()) {
            res.setStatus("404");
            res.setMessage("Section was not found");
            return res;
        }
        Optional<Section> nameExist = sectionRepo.findByName(sectionDTO.getName());
        if(nameExist.isPresent()) {
            if(!sectionExist.get().getName().equals(nameExist.get().getName())) {
                res.setStatus("409");
                res.setMessage("Section name already existed");
                return res;
            }
        }
        Section section = sectionExist.get();
        Optional<Section> parentSection = sectionRepo.findSectionById(sectionDTO.getParentId());
        if(parentSection.isPresent()) {
            section.setParent(parentSection.get());
        }
        section.setName(sectionDTO.getName());
        Section tempSection = sectionRepo.save(section);
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
        Optional<Section> tempSection = sectionRepo.findById(id);
        ResponseDTO res = new ResponseDTO();
        if(tempSection.isEmpty()) {
            res.setStatus("404");
            res.setMessage("Section was not found");
            return res;
        } else {
            Section sectionExist = tempSection.get();
            sectionExist.setIsDeleted(true);
            sectionRepo.save(sectionExist);
            res.setStatus("204");
            res.setMessage("Section deleted");
        }
        return res;
    }
}
