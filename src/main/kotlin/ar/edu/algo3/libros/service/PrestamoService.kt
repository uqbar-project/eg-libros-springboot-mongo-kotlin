package ar.edu.algo3.libros.service


import ar.edu.algo3.libros.domain.Prestamo
import ar.edu.algo3.libros.errorHandling.NotFoundException
import ar.edu.algo3.libros.repository.LibroRepository
import ar.edu.algo3.libros.repository.PrestamoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PrestamoService {

    @Autowired
    lateinit var prestamoRepository: PrestamoRepository

    @Autowired
    lateinit var libroRepository: LibroRepository

    fun generarPrestamo(prestamo: Prestamo) {
        val libro = libroRepository.findById(prestamo.libro.id).orElseThrow { NotFoundException("El libro con id " + prestamo.libro.id + " no existe" ) }
        prestamo.libro = libro
        prestamo.validar()
        prestamoRepository.save(prestamo)
        libro.prestar()
        libroRepository.save(libro)
    }

    fun getPrestamosPendientes() =
        prestamoRepository.getPrestamosPendientes()

    fun devolverPrestamo(prestamoOrigen: Prestamo) {
        val prestamo = prestamoRepository.findById(prestamoOrigen.id).orElseThrow { NotFoundException("El préstamo con id " + prestamoOrigen.id + " no existe" ) }
        val libro = libroRepository.findById(prestamo.libro.id).orElseThrow { NotFoundException("El libro con id " + prestamo.libro.id + " no existe" ) }
        prestamo.libro = libro
        prestamo.validarDevolucion()
        prestamo.devolver()
        prestamoRepository.save(prestamo)
        libroRepository.save(libro)
    }

}