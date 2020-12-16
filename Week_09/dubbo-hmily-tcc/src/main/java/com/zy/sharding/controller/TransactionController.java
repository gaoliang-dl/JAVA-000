package com.zy.sharding.controller;

import com.zy.sharding.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * 分布式事务测试
     * @return long
     */
    @RequestMapping(value = "/transactionTest/{userId}/{orderSN}", method = RequestMethod.GET)
    public long transactionTest(@PathVariable Integer userId, @PathVariable long orderSN) {
        transactionService.transactionTest(userId, orderSN);
        return 1;
    }


}
