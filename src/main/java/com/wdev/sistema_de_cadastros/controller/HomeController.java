package com.wdev.sistema_de_cadastros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity helloRoute(){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Visite a documentação: https://sistema-de-cadastros.fly.dev/doc",
                "github", "https://github.com/wandrey7/CadastroDePessoaAPI"));
    }
}
