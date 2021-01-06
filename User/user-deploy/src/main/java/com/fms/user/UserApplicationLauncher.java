package com.fms.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@EnableEurekaClient
@ComponentScan(basePackages = {"com.fms.user.*"})
public class UserApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(UserApplicationLauncher.class, args);

    }
}
