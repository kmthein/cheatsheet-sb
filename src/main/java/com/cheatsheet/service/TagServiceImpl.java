package com.cheatsheet.service;

import com.cheatsheet.dto.CheatsheetDTO;
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
    private ModelMapper mapper;

    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepo.findAllTags();
        List<TagDTO> tagDTOS = new ArrayList<>();
        if(tags.size() > 0) {
            for(Tag tag: tags) {
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
                tagDTOS.add(tagDTO);
            }
        }
        return tagDTOS;
    }
}
