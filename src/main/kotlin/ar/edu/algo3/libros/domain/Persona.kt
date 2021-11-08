package ar.edu.algo3.libros.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "personas")
class Persona {
    @Id
    lateinit var id: String
    lateinit var nombre: String
    lateinit var password: String

    override fun toString() = nombre

}