package com.binglian.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



/**
 * 创建作者:冰怜
 * 创建时间:2018/10/24 16:09
 */
@SpringBootApplication
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"com.binglian","org.n3r.idworker"})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
    
    
}
