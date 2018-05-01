package com.uncub.blog.user.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserTest {

    @Cacheable
    public String sayHello(String str){
        System.out.println(str);
        return str;
    }
}
