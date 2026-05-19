package com.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Prueba de carga del contexto de Spring Boot.
 * Verifica que todos los beans se inicialicen correctamente.
 *
 * NOTA: Para ejecutar esta prueba necesitas tener configurada
 * la conexión a MongoDB Atlas en application.properties.
 */
@SpringBootTest
class BibliotecaApiApplicationTests {

    @Test
    void contextLoads() {
        // Si el contexto de Spring Boot carga sin errores, la prueba pasa.
        // Esto verifica que todas las anotaciones, inyecciones de dependencias
        // y configuraciones estén correctas.
    }
}
