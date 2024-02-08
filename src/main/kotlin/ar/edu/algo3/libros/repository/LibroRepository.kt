package ar.edu.algo3.libros.repository

import ar.edu.algo3.libros.domain.Libro
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface LibroRepository : MongoRepository<Libro, String> {

    fun findByTitulo(titulo: String): Libro?

    @Query("{ estado : 'D', activo: true, titulo: {'\$regex': ?0, '\$options': 'i'} }")
    fun getLibrosPrestables(valorABuscar: String): Collection<Libro>
}