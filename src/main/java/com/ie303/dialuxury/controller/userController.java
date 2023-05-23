package com.ie303.dialuxury.controller;
import com.ie303.dialuxury.model.user;
import com.ie303.dialuxury.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class userController {
    @Autowired
    private userService userService;
    @PostMapping("")
    public String add(@RequestBody user user){
        userService.saveuser(user);
        return "New user is added";
    }

    @GetMapping("")
    public List<user> list(){
        return userService.getAllusers();
    }
}
