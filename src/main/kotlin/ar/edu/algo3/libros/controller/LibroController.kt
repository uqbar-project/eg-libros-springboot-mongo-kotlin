package ar.edu.algo3.libros.controller

import ar.edu.algo3.libros.service.LibroService
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
    fun getLibrosPrestables(@PathVariable valorABuscar: String) =
        this.libroService.librosPrestables(valorABuscar)

}