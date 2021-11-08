package ar.edu.algo3.libros.bootstrap

import ar.edu.algo3.libros.domain.Libro
import ar.edu.algo3.libros.domain.Persona
import ar.edu.algo3.libros.domain.Prestamo
import ar.edu.algo3.libros.repository.LibroRepository
import ar.edu.algo3.libros.repository.PersonaRepository
import ar.edu.algo3.libros.repository.PrestamoRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LibrosBootstrap : InitializingBean {

    @Autowired
    lateinit var personaRepository: PersonaRepository

    @Autowired
    lateinit var libroRepository: LibroRepository

    @Autowired
    lateinit var prestamoRepository: PrestamoRepository

    fun init() {
        var medina = Persona().apply {
            nombre = "Medina"
            password = "Piquito"
        }
        var santos = Persona().apply {
            nombre = "Santos"
            password = "Milazzo"
        }

        var elAleph = Libro().apply {
            titulo = "El Aleph"
            autor = "Jorge Luis Borges"
        }
        var noHabraMasPenas = Libro().apply {
            titulo = "No habrá más penas ni olvido"
            autor = "Osvaldo Soriano"
        }
        var novelaPeron = Libro().apply {
            titulo = "La novela de Perón"
            autor = "Tomás Eloy Martínez"
        }

        personaRepository.createIfNotExists(
            Persona().apply {
                nombre = "Lampone"
                password = "Betun"
            })
        medina = personaRepository.createIfNotExists(medina)
        santos = personaRepository.createIfNotExists(santos)

        elAleph = libroRepository.createIfNotExists(elAleph)
        noHabraMasPenas = libroRepository.createIfNotExists(noHabraMasPenas)
        libroRepository.createIfNotExists(
            Libro().apply {
                titulo = "100 años de soledad"
                autor = "Gabriel García Márquez"
            })
        novelaPeron = libroRepository.createIfNotExists(novelaPeron)
        libroRepository.createIfNotExists(
            Libro().apply {
                titulo = "¿Por quién doblan las campanas?"
                autor = "Ernest Hemingway"
            })

        val elAlephASantos = crearPrestamo(elAleph, santos)
        elAleph.prestar()
        libroRepository.save(elAleph)
        prestamoRepository.createWhenNew(elAlephASantos)

        val noHabraAMedina = crearPrestamo(noHabraMasPenas, medina)
        noHabraMasPenas.prestar()
        libroRepository.save(noHabraMasPenas)
        prestamoRepository.createWhenNew(noHabraAMedina)

        val novelaASantos = crearPrestamo(novelaPeron, santos)
        novelaPeron.prestar()
        libroRepository.save(novelaPeron)
        prestamoRepository.createWhenNew(novelaASantos)
    }

    fun PrestamoRepository.createWhenNew(prestamo: Prestamo) {
        if (this.findByLibro(prestamo.libro) === null) {
            this.save(prestamo)
        }
    }

    fun PersonaRepository.createIfNotExists(usuario: Persona): Persona {
        val bdUsuario = this.findByNombre(usuario.nombre)
        return if (bdUsuario === null) {
            this.save(usuario)
            usuario
        } else {
            bdUsuario
        }
    }

    fun LibroRepository.createIfNotExists(libro: Libro): Libro {
        val bdLibro = this.findByTitulo(libro.titulo)
        return if (bdLibro === null) {
            this.save(libro)
            libro
        } else {
            bdLibro
        }
    }

    fun crearPrestamo(_libro: Libro, _persona: Persona): Prestamo {
        return Prestamo().apply {
            libro = _libro
            persona = _persona
        }
    }

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running initialization")
        println("************************************************************************")
        init()
    }

}