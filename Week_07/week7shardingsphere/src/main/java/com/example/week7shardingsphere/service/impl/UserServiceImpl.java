package com.example.week7shardingsphere.service.impl;

import com.example.week7shardingsphere.entity.User;
import com.example.week7shardingsphere.mapper.UserMapper;
import com.example.week7shardingsphere.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }
}
