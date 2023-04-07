package com.backend.uujob;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableMPP
public class UUJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(UUJobApplication.class, args);
    }

}
