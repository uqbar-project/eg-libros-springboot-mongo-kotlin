package ar.edu.algo3.libros.repository

import ar.edu.algo3.libros.domain.Libro
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param

interface LibroRepository : MongoRepository<Libro, String> {

    fun findByTitulo(titulo: String): Libro?

    @Query("{ estado : 'D', activo: true, titulo: {'\$regex': :#{#valorABuscar}, '\$options': 'i'} }")
    fun getLibrosPrestables(@Param("valorABuscar") valorABuscar: String): Collection<Libro>
}