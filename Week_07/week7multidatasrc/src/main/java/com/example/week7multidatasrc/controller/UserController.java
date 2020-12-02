package com.example.week7multidatasrc.controller;

import com.example.week7multidatasrc.config.DataSource;
import com.example.week7multidatasrc.entity.User;
import com.example.week7multidatasrc.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService iUserService;

    @GetMapping("/master")
    @DataSource("master")
    public List<User> listByMaster() throws Exception {
        return iUserService.list();
    }

    @GetMapping("/slave")
    @DataSource("slave")
    public List<User> listBySlave() throws Exception {
        return iUserService.list();
    }
}
