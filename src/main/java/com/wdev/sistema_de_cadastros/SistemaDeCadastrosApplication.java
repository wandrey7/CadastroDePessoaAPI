package com.wdev.sistema_de_cadastros;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(info = @Info(
		title = "Sistema de cadastro",
		version = "1.0",
		description = "API de cadastro de Pessoas,Questões e Respostas"
))
@SpringBootApplication
public class SistemaDeCadastrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeCadastrosApplication.class, args);
	}

}
