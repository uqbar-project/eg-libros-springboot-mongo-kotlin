package ar.edu.algo3.libros.domain

import ar.edu.algo3.libros.errorHandling.UserException
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "prestamos")
class Prestamo {
    @Id
    lateinit var id: String
    lateinit var persona: PersonaDTO

    // @DocumentReference si queremos usar solo el ID de Libro
    // (y tener a cambio N+1 queries para traer los préstamos pendientes)
    lateinit var libro: Libro
    var fechaPrestamo = LocalDate.now()
    var fechaDevolucion: LocalDate? = null

    fun estaDisponible() = fechaDevolucion != null

    fun validar() {
        if (!libro.estaDisponible()) {
            throw UserException("El libro no está disponible")
        }
    }

    fun validarDevolucion() {
        if (estaDisponible()) {
            throw UserException("El préstamo del libro ya terminó")
        }
        if (libro.estaDisponible()) {
            throw UserException("El libro ya fue devuelto")
        }
    }

    fun devolver() {
        validarDevolucion()
        fechaDevolucion = LocalDate.now()
        libro.devolver()
    }

}

data class PersonaDTO(var id: String, var nombre: String)

fun Persona.toDTO() = PersonaDTO(id, nombre)