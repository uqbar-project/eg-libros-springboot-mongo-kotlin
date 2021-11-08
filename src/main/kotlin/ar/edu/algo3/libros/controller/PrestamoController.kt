package ar.edu.algo3.libros.controller

import ar.edu.algo3.libros.domain.Prestamo
import ar.edu.algo3.libros.service.PrestamoService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PrestamoController {

    @Autowired
    lateinit var prestamoService: PrestamoService

    @GetMapping("/prestamos")
    @ApiOperation("Permite conocer los préstamos pendientes del sistema, es decir, aquellos libros que están en poder de alguna persona.")
    fun getPrestamos() =
        this.prestamoService.getPrestamosPendientes()

    @PostMapping("/prestamos")
    @ApiOperation("Permite crear un préstamo, asociando una persona con un libro. El libro deja de estar disponible.")
    fun prestar(@RequestBody prestamo: Prestamo): ResponseEntity<String> {
        prestamoService.generarPrestamo(prestamo)
        return ResponseEntity.ok().body("Se generó el préstamo correctamente")
    }

    @PatchMapping("/prestamos")
    @ApiOperation("Permite devolver el libro a la biblioteca. El libro vuelve a estar disponible.")
    fun devolver(@RequestBody prestamoOrigen: Prestamo): ResponseEntity<String> {
        prestamoService.devolverPrestamo(prestamoOrigen)
        return ResponseEntity.ok().body("Se devolvió el libro correctamente")
    }

}
