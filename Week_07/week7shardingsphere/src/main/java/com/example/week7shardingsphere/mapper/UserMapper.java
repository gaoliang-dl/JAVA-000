package com.example.week7shardingsphere.mapper;

import com.example.week7shardingsphere.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> list();
}
