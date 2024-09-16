package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.UserDTO;
import com.cheatsheet.entity.Image;
import com.cheatsheet.entity.User;
import com.cheatsheet.exception.ResourceNotFoundException;
import com.cheatsheet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    private static final String UPLOAD_DIR = "uploads";

    @Override
    public ResponseDTO updateUser(UserDTO userDTO, int id, MultipartFile image) {
        System.out.println(id);
        Optional<User> user = userRepo.findById(id);
        ResponseDTO res = new ResponseDTO();
        String fileName = "";
        if(user.get() == null) {
            throw new ResourceNotFoundException("User not found");
        } else {
            User userExist = user.get();
            userExist.setName(userDTO.getName());
            userExist.setDescription(userDTO.getDescription());
            userExist.setWebsite(userDTO.getWebsite());
            List<Image> imgList = new ArrayList<>();
            if(image != null) {
                for(Image imgExist: userExist.getUserImage()) {
                    imgExist.setIsDeleted(true);
                    imgList.add(imgExist);
                }
                if(image != null && !image.isEmpty()) {
                    // Get the original file name
                    fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

                    // Ensure the directory exists
                    File uploadDir = new File(UPLOAD_DIR);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs(); // Create the directory if it doesn't exist
                    }

                    // Construct the path to save the file
                    Path path = Paths.get(UPLOAD_DIR + "/" + fileName);
                    try {
                        Files.write(path, image.getBytes());
                        Image img = new Image();
                        img.setImgUrl("uploads\\" + fileName);
//                    img.setUser(userExist);
                        imgList.add(img);
                        userExist.setUserImage(imgList);
                        System.out.println("File saved at: " + path.toString());
                    } catch (IOException e) {
                        res.setStatus("400");
                        res.setMessage("User update failed");
                        e.printStackTrace();
                    }
            }

            }
            // Save the file locally
            userRepo.save(userExist);
            res.setStatus("200");
            res.setMessage("User updated successful");
            UserDTO userDetails = mapper.map(userExist, UserDTO.class);
            for(Image img: userExist.getUserImage()) {
                if(!img.getIsDeleted()) {
                    userDetails.setImgUrl(img.getImgUrl());
                    System.out.println(userDetails.getImgUrl());
                }
            }
            res.setUserDetails(userDetails);
        }
        return res;
    }
}
