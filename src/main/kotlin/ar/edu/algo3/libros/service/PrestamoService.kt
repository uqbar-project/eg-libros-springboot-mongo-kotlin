package ar.edu.algo3.libros.service


import ar.edu.algo3.libros.domain.Prestamo
import ar.edu.algo3.libros.errorHandling.NotFoundException
import ar.edu.algo3.libros.repository.LibroRepository
import ar.edu.algo3.libros.repository.PrestamoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PrestamoService {

    @Autowired
    lateinit var prestamoRepository: PrestamoRepository

    @Autowired
    lateinit var libroRepository: LibroRepository

    @Transactional
    fun generarPrestamo(prestamo: Prestamo) {
        val libro = libroRepository.findById(prestamo.libro.id).orElseThrow { NotFoundException("El libro con id " + prestamo.libro.id + " no existe" ) }
        prestamo.libro = libro
        prestamo.validar()
        libro.prestar()
        prestamoRepository.save(prestamo)
        libroRepository.save(libro)
    }

    @Transactional(readOnly = true)
    fun getPrestamosPendientes() =
        prestamoRepository.getPrestamosPendientes()

    @Transactional
    fun devolverPrestamo(prestamoOrigen: Prestamo) {
        val prestamo = prestamoRepository.findById(prestamoOrigen.id).orElseThrow { NotFoundException("El préstamo con id " + prestamoOrigen.id + " no existe" ) }
        val libro = libroRepository.findById(prestamo.libro.id).orElseThrow { NotFoundException("El libro con id " + prestamo.libro.id + " no existe" ) }
        prestamo.libro = libro
        prestamo.devolver()
        prestamoRepository.save(prestamo)
        libroRepository.save(libro)
    }

}