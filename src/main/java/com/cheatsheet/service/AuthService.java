package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.User;

public interface AuthService {
    ResponseDTO register(User user);

    ResponseDTO login(User user);
}
