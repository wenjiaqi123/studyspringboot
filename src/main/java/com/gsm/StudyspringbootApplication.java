package com.gsm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.gsm.dao")
@SpringBootApplication
public class StudyspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyspringbootApplication.class, args);
    }
}
