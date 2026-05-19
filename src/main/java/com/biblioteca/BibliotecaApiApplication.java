package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * Sistema de Gestión de Biblioteca — API REST
 *
 * Arquitectura por capas:
 *   Controller → DTO → Service → Repository → Model → MongoDB Atlas
 */
@SpringBootApplication
public class BibliotecaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApiApplication.class, args);
    }
}
