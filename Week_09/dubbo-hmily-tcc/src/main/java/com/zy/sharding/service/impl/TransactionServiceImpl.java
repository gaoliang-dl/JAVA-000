package com.zy.sharding.service.impl;

import org.dromara.hmily.demo.dubbo.order.service.OrderService;
import com.zy.sharding.service.TransactionService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final OrderService orderService;

    @Autowired(required = false)
    public TransactionServiceImpl(
            OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public void transactionTest(Integer userId, long orderSN){
        LOGGER.info("=========tcc事务开始================");
        orderService.setUserAccountA();
        orderService.setUserAccountB();
    }

    public void confirm(Integer userId, long orderSN) {
        LOGGER.info("=========tcc事务confirm操作完成================");
    }

    public void cancel(Integer userId, long orderSN) {
        LOGGER.info("=========tcc事务cancel操作完成================");
    }

}
