package com.cheatsheet.service;

import com.cheatsheet.dto.ResponseDTO;
import com.cheatsheet.dto.ResponseTokenDTO;
import com.cheatsheet.dto.UserDTO;
import com.cheatsheet.entity.Role;
import com.cheatsheet.entity.User;
import com.cheatsheet.exception.ResourceNotFoundException;
import com.cheatsheet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtService jwtService;
    @Override
    public ResponseDTO register(User user) {
        ResponseDTO res = new ResponseDTO();
        if(user.getPassword().length() < 6) {
            res.setStatus("403");
            res.setMessage("Password must have between 6 and 20 characters");
            return res;
        } else if(user.getPassword().length() > 20) {
            res.setStatus("403");
            res.setMessage("Password must have between 6 and 20 characters");
            return res;
        }
        User emailExist = repo.findByEmail(user.getEmail());
        if(emailExist != null) {
            res.setStatus("409");
            res.setMessage("Email already existed");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            repo.save(user);
            res.setStatus("201");
            res.setMessage("User Registration Successful");
        }
        return res;
    }

    @Override
    public Object login(User user) {
        try {
            User existUser = repo.findByEmail(user.getEmail());
            if(existUser == null) {
                return new ResponseEntity<>("Email is incorrect, try again", HttpStatus.NOT_FOUND);
            }
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            String token = jwtService.generateToken(existUser);
            ResponseTokenDTO tokenDTO = new ResponseTokenDTO();
            tokenDTO.setToken(token);
            UserDTO userDTO = mapper.map(existUser, UserDTO.class);
            tokenDTO.setUserDetails(userDTO);
            return tokenDTO;
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>("Password is incorrect, try again", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
