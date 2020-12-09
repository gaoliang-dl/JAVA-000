package com.example.splitdb.service;

import com.example.splitdb.model.User;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class XaDemoService {

    @Resource
    private UserService userService;

    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public List<User> addTenUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder().username("a"+i).mobile("1861234123"+i).build();
            userService.save(user);
            list.add(user);
        }
        return list;
    }

    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public void addTenUserWithError(List<User> list) {
        for (int i = 0; i < 10; i++) {
            User user = User.builder().username("a"+i).mobile("1861234123"+i).build();
            userService.save(user);
            if (i == 9) {
                throw new RuntimeException("test xa transaction.");
            }
            list.add(user);
        }
    }
}
