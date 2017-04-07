package com.qatang.team.algorithm.tpf.spi.impl;

import com.qatang.team.algorithm.tpf.spi.HelloInterface;

/**
 * @author qatang
 */
public class HelloInterfaceImpl implements HelloInterface {
    @Override
    public void hello() {
        System.out.println("hello spi");
    }
}
