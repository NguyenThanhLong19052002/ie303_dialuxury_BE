package com.ie303.dialuxury.service;
import com.ie303.dialuxury.model.user;

import java.util.List;

public interface userService {
    public user saveuser(user user);
    public List<user> getAllusers();
}
