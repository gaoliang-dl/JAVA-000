package com.example.splitdb;

import com.example.splitdb.model.User;
import com.example.splitdb.service.UserService;
import com.example.splitdb.service.XaDemoService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@MapperScan("com.example.splitdb.mapper")
@SpringBootApplication
public class SplitDbApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SplitDbApplication.class, args);

        runXaDemo(context);
    }

    public static void runXaDemo(ConfigurableApplicationContext context) {

        XaDemoService service = context.getBean(XaDemoService.class);
        List<User> list = service.addTenUser();
        log.info("user list: {}", list);

        List<User> rollbackUserList = new ArrayList<>();;
        try {
            service.addTenUserWithError(rollbackUserList);
        } catch (Exception e) {
            log.warn("rollback", e);
        }

        UserService userService = context.getBean(UserService.class);
        User user = userService.getById(rollbackUserList.get(0).getId());
        log.info("user -- null: {}", user);
    }

}
