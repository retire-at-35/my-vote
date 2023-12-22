package com.gzhh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling//开启SpringTask
@EnableTransactionManagement//开启事务注解
public class MyvoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyvoteApplication.class, args);
    }

}
