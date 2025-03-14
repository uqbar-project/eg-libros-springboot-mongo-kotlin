package ar.edu.algo3.libros.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "libros")
class Libro {
    companion object {
        const val PRESTADO = "P"
        const val DISPONIBLE = "D"
    }

    @Id
    lateinit var id: String

    lateinit var titulo: String
    lateinit var autor: String
    var activo: Boolean = true
    var estado: String // "P" prestado / "D" disponible

    @Transient // El transient de JPA (jakarta) no funciona. Hay que usar el de springframework.
    var atributoNoPersistible = "Este atributo no se persiste"

    init {
        activo = true
        estado = DISPONIBLE
    }

    fun prestar() {
        estado = PRESTADO
    }

    fun devolver() {
        estado = DISPONIBLE
    }

    fun estaDisponible() = activo && estado == DISPONIBLE

    override fun toString(): String = titulo
}