package com.mybatis.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value="com.mybatis.shiro.dao")
public class MybatisApp {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(MybatisApp.class, args);
		
	}

//	@Autowired
//	private RestTemplateBuilder restTemplateBuilder;
//
//	@Bean
//	public RestTemplate restTemplate(){
//		return restTemplateBuilder.build();
//	}

}
