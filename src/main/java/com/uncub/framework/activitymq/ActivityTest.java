package com.uncub.framework.activitymq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:spring-application.xml"}) //加载配置文件
public class ActivityTest {
    @Autowired
    MessageProductor productor;
    @Test
    public void test(){
        productor.send("abcd<a href=\"abcd.com\"/>");
    }
}
