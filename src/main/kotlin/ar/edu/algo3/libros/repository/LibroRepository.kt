package ar.edu.algo3.libros.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ar.edu.algo3.libros.domain.Libro

interface LibroRepository : MongoRepository<Libro, String> {

    fun findByTitulo(titulo: String): Libro?

    @Query("{ estado : 'D', activo: true, titulo: {'\$regex': ?0, '\$options': 'i'} }")
    fun getLibrosPrestables(valorABuscar: String): Collection<Libro>
}