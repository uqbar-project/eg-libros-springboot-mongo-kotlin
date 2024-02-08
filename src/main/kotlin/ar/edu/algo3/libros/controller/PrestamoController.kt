package ar.edu.algo3.libros.controller

import ar.edu.algo3.libros.domain.Prestamo
import ar.edu.algo3.libros.errorHandling.UserException
import ar.edu.algo3.libros.service.PrestamoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class PrestamoController {

    @Autowired
    lateinit var prestamoService: PrestamoService

    @GetMapping("/prestamos")
    fun getPrestamos() =
        this.prestamoService.getPrestamosPendientes()

    @PostMapping("/prestamos")
    fun prestar(@RequestBody prestamo: Prestamo): ResponseEntity<String> {
        try {
            prestamoService.generarPrestamo(prestamo)
            return ResponseEntity.ok().body("Se generó el préstamo correctamente")
        } catch (e: UninitializedPropertyAccessException) {
            throw UserException("El préstamo no está correctamente inicializado: " + e.message)
        }
    }

    @PatchMapping("/prestamos")
    fun devolver(@RequestBody prestamoOrigen: Prestamo): ResponseEntity<String> {
        try {
            prestamoService.devolverPrestamo(prestamoOrigen)
            return ResponseEntity.ok().body("Se devolvió el libro correctamente")
        } catch (e: UninitializedPropertyAccessException) {
            throw UserException("El préstamo no está correctamente inicializado: " + e.message)
        }
    }

}
