package com.example.splitdb.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.splitdb.mapper.UserMapper;
import com.example.splitdb.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserService extends ServiceImpl<UserMapper, User> {

}
