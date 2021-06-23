package com.example.itembank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ItemBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemBankApplication.class, args);
    }
}
