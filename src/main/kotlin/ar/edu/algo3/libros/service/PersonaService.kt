package ar.edu.algo3.libros.service

import ar.edu.algo3.libros.repository.PersonaRepository
import ar.edu.algo3.libros.repository.PrestamoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonaService {

    @Autowired
    lateinit var personaRepository: PersonaRepository

    @Transactional(readOnly = true)
    fun findAll() = personaRepository.findAll()

}