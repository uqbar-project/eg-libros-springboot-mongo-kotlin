package ar.edu.algo3.libros.service

import ar.edu.algo3.libros.repository.LibroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LibroService {

    @Autowired
    lateinit var libroRepository: LibroRepository

    @Transactional(readOnly = true)
    fun librosPrestables(valorABuscar: String) =
        libroRepository.getLibrosPrestables(valorABuscar)

}