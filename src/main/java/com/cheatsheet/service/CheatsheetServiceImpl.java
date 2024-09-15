package com.cheatsheet.service;

import com.cheatsheet.dto.*;
import com.cheatsheet.entity.*;
import com.cheatsheet.exception.ResourceNotFoundException;
import com.cheatsheet.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheatsheetServiceImpl implements CheatsheetService {
    @Autowired
    private CheatsheetRepository cheatsheetRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private BlockRepository blockRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseDTO addNewCheatsheet(CheatsheetReqDTO cheatsheetDTO) {
        Cheatsheet cheatsheet = new Cheatsheet();
        ResponseDTO res = new ResponseDTO();
        Cheatsheet nameExist = cheatsheetRepo.findByName(cheatsheetDTO.getName());
        if(nameExist != null) {
            throw new ResourceNotFoundException("Name was already existed, choose another");
        } else {
            cheatsheet.setName(cheatsheetDTO.getName());
            Optional<User> tempUser = userRepo.findById(cheatsheetDTO.getUserId());
            if(tempUser.isEmpty()) {
                throw new ResourceNotFoundException("User not found");
            } else {
                cheatsheet.setUser(tempUser.get());
            }
            Cheatsheet tempCs = cheatsheetRepo.save(cheatsheet);
            if(tempCs != null) {
                res.setStatus("201");
                res.setMessage("Cheatsheet created successfully");
                res.setId(tempCs.getId());
            } else {
                res.setStatus("500");
                res.setMessage("An error occurred");
            }
        }
        return res;
    }

    @Override
    public ResponseDTO addCheatsheet(CheatsheetReqDTO cheatsheetDTO) {
        ResponseDTO res = new ResponseDTO();
        try {
            Cheatsheet cheatsheet = mapper.map(cheatsheetDTO, Cheatsheet.class);
            Optional<User> tempUser = userRepo.findById(cheatsheetDTO.getUserId());
            if (tempUser.isPresent()) {
                cheatsheet.setUser(tempUser.get());
            } else {
                res.setStatus("401");
                res.setMessage("User not found");
                return res;
            }
            Optional<Section> tempSection = sectionRepo.findSectionById(cheatsheetDTO.getSectionId());
            if (tempSection.isPresent()) {
                cheatsheet.setSection(tempSection.get());
            } else {
                res.setStatus("401");
                res.setMessage("Section not found");
                return res;
            }
            if(cheatsheetDTO.getTagList().size() > 0) {
                List<Tag> tagList = new ArrayList<>();
                for (String tempTag: cheatsheetDTO.getTagList()) {
                    Tag tagExist = tagRepo.findByName(tempTag);
                    Tag tag = new Tag();
                    if(tagExist == null) {
                        tag.setName(tempTag);
                        tagRepo.save(tag);
                        tagList.add(tag);
                    } else {
                        tag.setName(tagExist.getName());
                        tag.setId(tagExist.getId());
                        tagRepo.save(tag);
                        tagList.add(tag);
                    }
                }
                cheatsheet.setTags(tagList);
            }
            cheatsheetRepo.save(cheatsheet);
            for (BlockDTO blockDTO: cheatsheetDTO.getBlocks()) {
                Block block = new Block();
                block.setTitle(blockDTO.getTitle());
                try {
                    block.setContent(new ObjectMapper().writeValueAsString(blockDTO.getContent()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to process JSON content", e);
                }
                block.setCheatsheet(cheatsheet);
                blockRepo.save(block);
            }
            res.setStatus("201");
            res.setMessage("Cheatsheet created successfully");
        } catch (Exception e) {
            res.setStatus("500");
            res.setMessage("An error occurred: " + e.getMessage());
        }
        return res;
    }


    @Override
    public ResponseDTO updateCheatsheet(int id, CheatsheetReqDTO cheatsheetDTO) {
        Cheatsheet tempCheatsheet = cheatsheetRepo.findCheatsheetById(id);
        ResponseDTO res = new ResponseDTO();
        if(tempCheatsheet == null) {
            res.setStatus("404");
            res.setMessage("Cheatsheet is not found");
            return res;
        } else {
            tempCheatsheet.setName(cheatsheetDTO.getName());
            tempCheatsheet.setColor(cheatsheetDTO.getColor());
            tempCheatsheet.setDescription(cheatsheetDTO.getDescription());
            tempCheatsheet.setLanguage(cheatsheetDTO.getLanguage());
            tempCheatsheet.setStyle(cheatsheetDTO.getStyle());
            tempCheatsheet.setType(cheatsheetDTO.getType());
            tempCheatsheet.setLayout(cheatsheetDTO.getLayout());
            tempCheatsheet.setSection(sectionRepo.findSectionById(cheatsheetDTO.getSectionId()).get());
            if(cheatsheetDTO.getBlocks() != null) {
                for (BlockDTO blockDTO : cheatsheetDTO.getBlocks()) {
                    Optional<Block> tempBlock = blockRepo.findById(blockDTO.getId());
                    Block block = null;
                    if (tempBlock.isPresent()) {
                        block = blockRepo.findById(blockDTO.getId()).get();
                    } else {
                        block = new Block();
                    }
                    block.setTitle(blockDTO.getTitle());
                    try {
                        block.setContent(new ObjectMapper().writeValueAsString(blockDTO.getContent()));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    block.setCheatsheet(tempCheatsheet);
                    blockRepo.save(block);
                }
            }
            res.setStatus("200");
            res.setMessage("Cheatsheet update successful");
            System.out.println(cheatsheetDTO.getTagList().size());
            if(cheatsheetDTO.getTagList() != null) {
                if(cheatsheetDTO.getTagList().size() > 0) {
                    List<Tag> tagList = new ArrayList<>();
                    for (String tempTag: cheatsheetDTO.getTagList()) {
                        Tag tagExist = tagRepo.findByName(tempTag);
                        Tag tag = new Tag();
                        if(tagExist == null) {
                            tag.setName(tempTag);
                            tagRepo.save(tag);
                            tagList.add(tag);
                        } else {
                            tag.setName(tagExist.getName());
                            tag.setId(tagExist.getId());
                            tagRepo.save(tag);
                            tagList.add(tag);
                        }
                    }
                    tempCheatsheet.setTags(tagList);
                } else {
                    List<Tag> tagList = tagRepo.findByCheatsheetId(id);
                    for (Tag tag: tagList) {
                        System.out.println(tag.getName());
                        Tag tempTag = tagRepo.findTagById(tag.getId());
                        if(tempTag != null) {
                            tempCheatsheet.setTags(new ArrayList<>());
                        }
                    }
                }
            } else {
                List<Tag> tagList = tagRepo.findByCheatsheetId(cheatsheetDTO.getId());
                for (Tag tag: tagList) {
                    System.out.println(tag.getName());
                    Tag tempTag = tagRepo.findTagById(tag.getId());
                    if(tempTag != null) {
                        tagRepo.delete(tempTag);
                    }
                }
            }
        }
        cheatsheetRepo.save(tempCheatsheet);
        return res;
    }

    public static List<List<String>> convertJsonToList(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<List<List<String>>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CheatsheetDTO> findAllCheatsheets() {
        List<Cheatsheet> cheatsheets = cheatsheetRepo.findAllCheatsheets();
        List<CheatsheetDTO> cheatsheetDTOList = new ArrayList<>();

        for (Cheatsheet cheatsheet : cheatsheets) {
            CheatsheetDTO cheatsheetDTO = mapCheatsheetToDTO(cheatsheet);
            cheatsheetDTOList.add(cheatsheetDTO);
        }

        return cheatsheetDTOList;
    }

    @Override
    public CheatsheetDTO findCheatsheetById(int id) {
        Cheatsheet cheatsheet = cheatsheetRepo.findCheatsheetById(id);
        if(cheatsheet == null) {
            throw new ResourceNotFoundException("Cheatsheet not found");
        }
        return mapCheatsheetToDTO(cheatsheet);
    }

    @Override
    public CheatsheetDTO mapCheatsheetToDTO(Cheatsheet cheatsheet) {
        CheatsheetDTO cheatsheetDTO = mapper.map(cheatsheet, CheatsheetDTO.class);

        // Map Blocks to BlockDTOs
        if(cheatsheet.getBlockList().size() > 0) {
            List<BlockDTO> blockDTOList = new ArrayList<>();
        for (Block block : cheatsheet.getBlockList()) {
            BlockDTO blockDTO = mapBlockToDTO(block);
            blockDTO.setNote(block.getNote());
            blockDTOList.add(blockDTO);
        }
        cheatsheetDTO.setBlocks(blockDTOList);
        }

        // Map Section and its hierarchy
        if(cheatsheet.getSection() != null) {
            Section section = cheatsheet.getSection();
            SectionDTO sectionDTO = mapSectionToDTO(section);
            cheatsheetDTO.setSection(sectionDTO);
        }

        if(cheatsheet.getTags() != null) {
            if(cheatsheet.getTags().size() > 0) {
                List<TagDTO> tagDTOList = new ArrayList<>();
                for(Tag tag: cheatsheet.getTags()) {
                    TagDTO tagDTO = new TagDTO();
                    tagDTO.setId(tag.getId());
                    tagDTO.setName(tag.getName());
                    tagDTOList.add(tagDTO);
                }
                cheatsheetDTO.setTagList(tagDTOList);
            }
        }

        // Map User
        UserDTO userDTO = mapper.map(cheatsheet.getUser(), UserDTO.class);
        userDTO.setRole(cheatsheet.getUser().getRole());
        cheatsheetDTO.setUser(userDTO);

        return cheatsheetDTO;
    }

    private BlockDTO mapBlockToDTO(Block block) {
        BlockDTO blockDTO = new BlockDTO();
        blockDTO.setId(block.getId());
        blockDTO.setTitle(block.getTitle());
        blockDTO.setContent(convertJsonToList(block.getContent()));
        blockDTO.setImgUrl(block.getImgUrl());
        return blockDTO;
    }

    private SectionDTO mapSectionToDTO(Section section) {
        SectionDTO sectionDTO = mapper.map(section, SectionDTO.class);
        sectionDTO.setUserId(null);
        sectionDTO.setParentId(null);

        if (section.getParent() != null) {
            ParentDTO parentDTO = mapParentToDTO(section.getParent());
            sectionDTO.setParent(parentDTO);
        }

        sectionDTO.setUser(null); // Clear user details for section level
        return sectionDTO;
    }

    private ParentDTO mapParentToDTO(Section parentSection) {
        ParentDTO parentDTO = new ParentDTO(parentSection.getId(), parentSection.getName());

        if (parentSection.getParent() != null) {
            SectionDTO grandParentSectionDTO = mapper.map(parentSection.getParent(), SectionDTO.class);
            grandParentSectionDTO.setUserId(null);
            grandParentSectionDTO.setParentId(null);
            grandParentSectionDTO.setUser(null);

            parentDTO.setParent(new ParentDTO(parentSection.getParent().getId(), parentSection.getParent().getName(), grandParentSectionDTO).getParent());
        }
        return parentDTO;
    }

    @Transactional
    @Override
    public List<CheatsheetDTO> getCheatsheetBySection(String name) {
        List<Cheatsheet> cheatsheets = cheatsheetRepo.findCheatsheetBySection(name);
        List<CheatsheetDTO> cheatsheetDTOList = new ArrayList<>();

        for (Cheatsheet cheatsheet : cheatsheets) {
            CheatsheetDTO cheatsheetDTO = mapCheatsheetToDTO(cheatsheet);
            cheatsheetDTOList.add(cheatsheetDTO);
        }

        return cheatsheetDTOList;
    }

    @Override
    public ResponseDTO deleteCheatsheetById(int id) {
        int result = cheatsheetRepo.deleteCheatsheetById(id);
        ResponseDTO res = new ResponseDTO();
        if(result > 0) {
            res.setStatus("204");
            res.setMessage("Delete successful");
        } else {
            res.setStatus("404");
            res.setMessage("Delete failed");
        }
        return res;
    }

    @Override
    public List<CheatsheetDTO> findCheatsheetsByUserId(int id) {
        List<Cheatsheet> cheatsheets = cheatsheetRepo.findAllByUserId(id);
        List<CheatsheetDTO> cheatsheetDTOList = new ArrayList<>();

        for (Cheatsheet cheatsheet : cheatsheets) {
            CheatsheetDTO cheatsheetDTO = mapCheatsheetToDTO(cheatsheet);
            cheatsheetDTOList.add(cheatsheetDTO);
        }

        return cheatsheetDTOList;
    }
}
