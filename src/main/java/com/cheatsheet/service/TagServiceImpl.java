package com.cheatsheet.service;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.dto.TagCountDTO;
import com.cheatsheet.dto.TagDTO;
import com.cheatsheet.entity.Cheatsheet;
import com.cheatsheet.entity.Tag;
import com.cheatsheet.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private CheatsheetService cheatsheetService;

    @Autowired
    private ModelMapper mapper;

    public TagDTO mapTagToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        List<CheatsheetDTO> cheatsheetList = new ArrayList<>();
        for (Cheatsheet cheatsheet: tag.getCheatsheets()) {
            CheatsheetDTO cheatsheetDTO = new CheatsheetDTO();
            cheatsheetDTO.setId(cheatsheet.getId());
            cheatsheetDTO.setName(cheatsheet.getName());
            cheatsheetList.add(cheatsheetDTO);
        }
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        tagDTO.setCheatsheetList(cheatsheetList);
        return tagDTO;
    }

    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepo.findAllTags();
        return getTagDTOS(tags);
    }

    @Override
    public List<TagCountDTO> getTopTagsBySection(String sectionName) {
        List<TagCountDTO> tags = tagRepo.getTopTenTags(sectionName);
        return tags;
    }

    @Override
    public List<CheatsheetDTO> getCheatsheetsByTag(String tagName) {
        List<Cheatsheet> cheatsheets = tagRepo.getCheatsheetsByTag(tagName);
        List<CheatsheetDTO> cheatsheetDTOS = new ArrayList<>();
        for(Cheatsheet cheatsheet: cheatsheets) {
            CheatsheetDTO cheatsheetDTO = cheatsheetService.mapCheatsheetToDTO(cheatsheet);
            cheatsheetDTOS.add(cheatsheetDTO);
        }
        return cheatsheetDTOS;
    }

    private List<TagDTO> getTagDTOS(List<Tag> tags) {
        List<TagDTO> tagDTOS = new ArrayList<>();
        if(tags.size() > 0) {
            for(Tag tag: tags) {
                TagDTO tagDTO = mapTagToDTO(tag);
                tagDTOS.add(tagDTO);
            }
        }
        return tagDTOS;
    }
}
