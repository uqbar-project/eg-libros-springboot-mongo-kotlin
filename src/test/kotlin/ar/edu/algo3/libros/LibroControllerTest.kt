package ar.edu.algo3.libros

import ar.edu.algo3.libros.domain.Libro
import ar.edu.algo3.libros.domain.Libro.Companion.PRESTADO
import ar.edu.algo3.libros.repository.LibroRepository
import org.junit.jupiter.api.AfterEach
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
@DisplayName("Dado un controller de libros")
class LibroControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var libroRepository: LibroRepository

    @BeforeEach
    fun setup() {
        libroRepository.save(Libro().apply {
            titulo = "La guerra y la paz"
            autor = "León Tolstoi"
        })
        libroRepository.save(Libro().apply {
            titulo = "Esperando a Tito"
            autor = "Eduardo Sacheri"
        })
        libroRepository.save(Libro().apply {
            titulo = "Esperando a Godot"
            autor = "Samuel Beckett"
        })
        libroRepository.save(Libro().apply {
            titulo = "Esperando Al Hermanito"
            autor = "Maritchu Seitun"
            activo = false
        })
        libroRepository.save(Libro().apply {
            titulo = "Esperando la carroza"
            autor = "Jacobo Langsner"
            prestar()
        })
    }

    @AfterEach
    fun deleteAll() {
        libroRepository.deleteAll()
    }

    @Test
    fun `podemos buscar libros activos y disponibles por titulo`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/libros/esperando")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Esperando a Tito"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo").value("Esperando a Godot"))
    }

}
