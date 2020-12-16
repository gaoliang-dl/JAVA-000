package org.dromara.hmily.demo.dubbo.order.service;

import org.dromara.hmily.annotation.Hmily;

public interface OrderService {

    @Hmily
    long setUserAccountA();

    @Hmily
    long setUserAccountB();
}
