package ar.edu.algo3.libros.controller

import ar.edu.algo3.libros.service.LibroService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class LibroController {

    @Autowired
    lateinit var libroService: LibroService

    @GetMapping("/libros/{valorABuscar}")
    @Operation(summary = "Recupera información de los libros cuyo título contiene el valor a buscar (campo obligatorio). No distingue mayúsculas / minúsculas, por lo que si se busca 'prin' devolverá por ejemplo el libro que tiene como título 'El Principito'.")
    fun getLibrosPrestables(@PathVariable valorABuscar: String) =
        this.libroService.librosPrestables(valorABuscar)

}