package com.example.itembank;

import com.example.itembank.base.annotation.common.BrowserInfo;
import com.example.itembank.base.resolver.BrowserInfoArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;


@RequiredArgsConstructor
@ServletComponentScan
@SpringBootApplication
public class ItemBankApplication extends WebMvcConfigurationSupport {

    private final BrowserInfoArgumentResolver browserInfoArgumentResolver;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(browserInfoArgumentResolver);
    }

    public static void main(String[] args) {
        SpringApplication.run(ItemBankApplication.class, args);
    }
}
