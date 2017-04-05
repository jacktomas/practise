package com.test.reposity;

/**
 * Created by Administrator on 2017/4/5.
 */
@RPCService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello : " + name;
    }
}
