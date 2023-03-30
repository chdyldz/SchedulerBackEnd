package com.scheduler.n11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.scheduler.n11.demo.*")
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.scheduler.n11.demo.repository")
@EntityScan("com.scheduler.n11.demo.entity")
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}

}
