package ar.edu.algo3.libros

import ar.edu.algo3.libros.domain.Libro
import ar.edu.algo3.libros.domain.Prestamo
import ar.edu.algo3.libros.repository.LibroRepository
import ar.edu.algo3.libros.repository.PersonaRepository
import ar.edu.algo3.libros.repository.PrestamoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de préstamos")
class PrestamoControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var personaRepository: PersonaRepository

    @Autowired
    lateinit var libroRepository: LibroRepository

    @Autowired
    lateinit var prestamoRepository: PrestamoRepository

    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
    }

    @Test
    fun `podemos buscar prestamos pendientes`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").isNotEmpty)
    }

    @Test
    fun `no se puede crear un prestamo sin libro`() {
        val prestamo = crearUnPrestamo()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "persona": ${objectMapper.writeValueAsString(prestamo.persona)}
                }
                """.trimIndent())
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `no se puede crear un prestamo sin persona que lo lleva`() {
        val prestamo = crearUnPrestamo()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "libro": ${objectMapper.writeValueAsString(prestamo.libro)}
                }
                """.trimIndent())
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `se puede crear un prestamo que tiene todos los datos ok`() {
        val prestamo = crearUnPrestamo()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "libro": ${objectMapper.writeValueAsString(prestamo.libro)},
                    "persona": ${objectMapper.writeValueAsString(prestamo.persona)}
                }
                """.trimIndent())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `se puede devolver un libro una vez creado el prestamo, la segunda vez tira error`() {
        val prestamo = prestamoRepository.save(crearUnPrestamo())
        libroRepository.save(prestamo.libro)
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        // la segunda vez el préstamo está finalizado
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `no se puede devolver un libro si el prestamo no existe`() {
        val prestamo = crearUnPrestamo().apply { id = "CualquierID" }

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo))
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }


    fun crearUnPrestamo() = Prestamo().apply {
        persona = personaRepository.findAll().first()
        val libroPrestable = libroRepository.getLibrosPrestables("").firstOrNull() ?: libroRepository.save(
            Libro().apply {
                autor = "Leon Tolstoi"
                titulo = "La guerra y la paz"
            }
        )
        libroPrestable.prestar()
        libro = libroPrestable
    }
}
