package ar.edu.algo3.libros.repository

import ar.edu.algo3.libros.domain.Libro
import ar.edu.algo3.libros.domain.Prestamo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface PrestamoRepository : MongoRepository<Prestamo, String> {

    fun findByLibro(libro: Libro): Libro?

    @Query("{ fechaDevolucion : { \$exists: false } }")
    fun getPrestamosPendientes(): Collection<Prestamo>

}