package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.infocapitos.data.repository.PostNewRepository
import com.example.infocapitos.ui.screens.HomeScreen
import com.example.infocapitos.ui.viewmodel.PostViewModel
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMuestraNoticiasCargadas() {
        // 1. Preparamos el ViewModel con la API Falsa
        val fakeApi = FakeApiService()
        val fakeRepo = PostNewRepository(fakeApi)
        val fakeViewModel = PostViewModel(fakeRepo)

        // 2. Cargamos la pantalla pasando el ViewModel falso
        composeTestRule.setContent {
            HomeScreen(
                viewModel = fakeViewModel,
                onItemClick = {}
            )
        }

        // Esperamos a que Compose procese (útil por el polling)
        composeTestRule.waitForIdle()

        // 3. Verificamos que los datos del FakeApiService aparecen en pantalla
        composeTestRule.onNodeWithText("Renato care pato").assertIsDisplayed()
        composeTestRule.onNodeWithText("colo colo eterno campeon").assertIsDisplayed()
    }
}