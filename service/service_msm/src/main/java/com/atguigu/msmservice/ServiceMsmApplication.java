package com.atguigu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // feign服务调用
@ComponentScan({"com.atguigu"})
public class ServiceMsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMsmApplication.class, args);
    }
}