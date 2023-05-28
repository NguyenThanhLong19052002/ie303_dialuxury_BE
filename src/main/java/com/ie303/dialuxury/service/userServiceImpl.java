package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.user;
import com.ie303.dialuxury.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements UserDetailsService  {
    @Autowired
    private userRepository userRepository;

//    @Override
//    public user saveuser(user user) {
//        return userRepository.save(user);
//    }

//    @Override
//    public List<user> getAllusers() {
//        return userRepository.findAll();
//    }


//    Đăng nhập đăng ký
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        user user = userRepository.findByEmail(email);
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(email);
            builder.password(user.getPassword());
            builder.roles("USER");
        } else {
            throw new UsernameNotFoundException("Không tìm thấy người dùng");
        }
        return builder.build();
    }
}
