package com.cheatsheet.service;

import com.cheatsheet.entity.User;
import com.cheatsheet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repo;
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email);
    }
}
