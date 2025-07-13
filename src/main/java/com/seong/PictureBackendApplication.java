package com.seong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shengyu
 * @version V1.0.0 2024-04-12
 **/
@EnableFeignClients
@SpringBootApplication
public class PictureBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PictureBackendApplication.class, args);
    }
}