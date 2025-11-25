package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.infocapitos.data.repository.PostNewRepository
import com.example.infocapitos.ui.screens.DetailScreen
import com.example.infocapitos.ui.viewmodel.PostViewModel
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMuestraDetalleCorrecto() {
        // 1. Configuramos el entorno falso
        // Asegúrate de tener FakeApiService creado como te indiqué antes
        val fakeApi = FakeApiService()
        val repo = PostNewRepository(fakeApi)
        val viewModel = PostViewModel(repo)

        // 2. Cargamos la pantalla solicitando el ID 1
        // (El ID 1 existe en los datos de prueba de FakeApiService)
        composeTestRule.setContent {
            DetailScreen(
                noticiaId = 1,
                viewModel = viewModel,
                onBack = {}
            )
        }

        // Esperamos renderizado
        composeTestRule.waitForIdle()

        // 3. Validamos que se ve el título y descripción específicos
        composeTestRule.onNodeWithText("La caida de mascapitos").assertIsDisplayed()
        composeTestRule.onNodeWithText("El dream team del qeu todo el mundo habla, falla por primera vez en su historia a falta de 20 minutos, 20 minutos que los separaron de la gloria eterna.").assertIsDisplayed()
    }

    @Test
    fun testMuestraMensajeCargaSiNoExiste() {
        val fakeApi = FakeApiService()
        val repo = PostNewRepository(fakeApi)
        val viewModel = PostViewModel(repo)

        composeTestRule.setContent {
            // Pedimos un ID que NO existe (ej. 999)
            DetailScreen(
                noticiaId = 999,
                viewModel = viewModel,
                onBack = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Cargando o noticia no encontrada...")
            .assertIsDisplayed()
    }
}