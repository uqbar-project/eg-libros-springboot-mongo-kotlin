package ar.edu.algo3.libros

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

// es importante la configuración exclude para no sufrir el no suitable driver class
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class LibrosApplication

fun main(args: Array<String>) {
    runApplication<LibrosApplication>(*args)
}
