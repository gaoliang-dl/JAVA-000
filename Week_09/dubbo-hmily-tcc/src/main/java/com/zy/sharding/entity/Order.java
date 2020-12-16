package com.zy.sharding.entity;

import lombok.Data;

@Data
public class Order {

    private int id;

    private int status;

    private long orderSN;

    private String tradeNo;

    private int userId;

    private int goodId;

    private int payType;

    private int payFee;
}
