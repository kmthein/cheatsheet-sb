package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.entity.User;
import com.cheatsheet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Override
    public ResponseDTO register(User user) {
        User emailExist = repo.findByEmail(user.getEmail());
        ResponseDTO res = new ResponseDTO();
        if(emailExist != null) {
            res.setStatus("409");
            res.setMessage("Email already existed");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repo.save(user);
            res.setStatus("201");
            res.setMessage("User Registration Successful");
        }
        return res;
    }

    @Override
    public ResponseDTO login(User user) {
        return null;
    }
}
