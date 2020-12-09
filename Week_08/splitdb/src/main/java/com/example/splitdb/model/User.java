package com.example.splitdb.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("user")
public class User {

    @TableId(value = "id")
    private Long id;

    private String username;

    private String mobile;
}
