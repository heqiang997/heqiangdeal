package com.zhimeng.caiwuweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.zhimeng.caiwuweb.dao")
@EnableSwagger2
//@EnableScheduling
public class CaiwuWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaiwuWebApplication.class, args);
	}
}
