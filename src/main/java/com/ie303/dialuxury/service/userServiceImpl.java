package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.user;
import com.ie303.dialuxury.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userRepository userRepository;

    @Override
    public user saveuser(user user) {
        return userRepository.save(user);
    }

    @Override
    public List<user> getAllusers() {
        return userRepository.findAll();
    }
}
