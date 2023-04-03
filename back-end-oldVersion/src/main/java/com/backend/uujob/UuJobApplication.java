package com.backend.uujob;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

//代表返回的是json格式的数据
@RestController
@SpringBootApplication
public class UuJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(UuJobApplication.class, args);
    }
}
