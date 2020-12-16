package com.zy.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;


@ImportResource({"classpath:spring-dubbo.xml"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JdbcShardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcShardingApplication.class, args);
	}
	
}
