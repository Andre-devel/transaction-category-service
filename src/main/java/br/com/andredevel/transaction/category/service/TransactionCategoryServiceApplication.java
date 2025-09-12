package br.com.andredevel.transaction.category.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TransactionCategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionCategoryServiceApplication.class, args);
	}

}
