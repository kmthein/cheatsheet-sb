package com.cheatsheet.service;

import com.cheatsheet.dto.*;
import com.cheatsheet.entity.Block;
import com.cheatsheet.entity.Cheatsheet;
import com.cheatsheet.entity.Section;
import com.cheatsheet.entity.User;
import com.cheatsheet.repository.BlockRepository;
import com.cheatsheet.repository.CheatsheetRepository;
import com.cheatsheet.repository.SectionRepository;
import com.cheatsheet.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ModelMapper mapper;

    @Override
    public ResponseDTO addCheatsheet(CheatsheetReqDTO cheatsheetDTO) {
        ResponseDTO res = new ResponseDTO();

        try {
            // Map the basic fields from DTO to entity
            Cheatsheet cheatsheet = mapper.map(cheatsheetDTO, Cheatsheet.class);

            // Manually map and set the User
            Optional<User> tempUser = userRepo.findById(cheatsheetDTO.getUserId());
            if (tempUser.isPresent()) {
                cheatsheet.setUser(tempUser.get());
            } else {
                res.setStatus("401");
                res.setMessage("User not found");
                return res;
            }

            // Manually map and set the Section
            Optional<Section> tempSection = sectionRepo.findSectionById(cheatsheetDTO.getSectionId());
            if (tempSection.isPresent()) {
                cheatsheet.setSection(tempSection.get());
            } else {
                res.setStatus("401");
                res.setMessage("Section not found");
                return res;
            }

            // Save the Cheatsheet entity
            cheatsheetRepo.save(cheatsheet);

            // Save associated Blocks
            for (BlockDTO blockDTO : cheatsheetDTO.getBlocks()) {
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
            tempCheatsheet.setSection(sectionRepo.findSectionById(cheatsheetDTO.getSectionId()).get());
            cheatsheetRepo.save(tempCheatsheet);
            for(BlockDTO blockDTO: cheatsheetDTO.getBlocks()) {
                Optional<Block> tempBlock = blockRepo.findById(blockDTO.getId());
                Block block = null;
                if(tempBlock.isPresent()) {
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
            res.setStatus("200");
            res.setMessage("Cheatsheet update successful");
        }
        return res;
    }

    private List<List<String>> convertJsonToList(String json) {
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
        return mapCheatsheetToDTO(cheatsheet);
    }

    private CheatsheetDTO mapCheatsheetToDTO(Cheatsheet cheatsheet) {
        CheatsheetDTO cheatsheetDTO = mapper.map(cheatsheet, CheatsheetDTO.class);

        // Map Blocks to BlockDTOs
        List<BlockDTO> blockDTOList = new ArrayList<>();
        for (Block block : cheatsheet.getBlockList()) {
            BlockDTO blockDTO = mapBlockToDTO(block);
            blockDTOList.add(blockDTO);
        }
        cheatsheetDTO.setBlocks(blockDTOList);

        // Map Section and its hierarchy
        Section section = cheatsheet.getSection();
        SectionDTO sectionDTO = mapSectionToDTO(section);
        cheatsheetDTO.setSection(sectionDTO);

        // Map User
        UserDTO userDTO = mapper.map(section.getUser(), UserDTO.class);
        userDTO.setRole(section.getUser().getRole());
        cheatsheetDTO.setUser(userDTO);

        return cheatsheetDTO;
    }

    private BlockDTO mapBlockToDTO(Block block) {
        BlockDTO blockDTO = new BlockDTO();
        blockDTO.setId(block.getId());
        blockDTO.setTitle(block.getTitle());
        blockDTO.setContent(convertJsonToList(block.getContent()));
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
    public List<Cheatsheet> getCheatsheetBySection(int sectionId) {
        return cheatsheetRepo.findCheatsheetBySection(sectionId);
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
}
