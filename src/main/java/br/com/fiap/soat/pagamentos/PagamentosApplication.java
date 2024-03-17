package br.com.fiap.soat.pagamentos;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamoDBRepositories(basePackages = "br.com.fiap.soat.pagamentos.dynamodb.repositories")
public class PagamentosApplication {
	public static void main(String[] args) {
		SpringApplication.run(PagamentosApplication.class, args);
	}
}