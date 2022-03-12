package ar.edu.algo3.libros.controller

import ar.edu.algo3.libros.repository.PersonaRepository
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PersonaController {

    @Autowired
    lateinit var personaRepository: PersonaRepository

    @GetMapping("/personas")
    @Operation(summary = "Devuelve las personas que son socios de la biblioteca y pueden pedir prestado un libro.")
    fun getLibrosPrestables() = this.personaRepository.findAll()

}