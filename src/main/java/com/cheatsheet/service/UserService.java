package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseDTO updateUser(UserDTO userDTO, int id, MultipartFile image);
}

