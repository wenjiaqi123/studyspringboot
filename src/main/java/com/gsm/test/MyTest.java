package com.gsm.test;

import com.gsm.StudyspringbootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 创建 SpringBoot 容器环境
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyspringbootApplication.class)
public class MyTest {
    @Test
    public void test() {

    }
}
