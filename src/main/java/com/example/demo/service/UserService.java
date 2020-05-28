package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {

    @Autowired
    public UserMapper userMapper;

    public User selectByName(String userName){
        return userMapper.getUserByName(userName);
    }

    //注册
    public int insert(User user){
        return userMapper.addUser(user);
    }

}
