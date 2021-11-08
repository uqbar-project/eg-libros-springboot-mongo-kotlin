package ar.edu.algo3.libros.repository

import ar.edu.algo3.libros.domain.Persona
import org.springframework.data.mongodb.repository.MongoRepository

interface PersonaRepository : MongoRepository<Persona, String> {
    fun findByNombre(nombre: String): Persona?
}