package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.infocapitos.ui.screens.NoticiaScreen
import com.example.infocapitos.ui.viewmodel.PostViewModel
import org.junit.Rule
import org.junit.Test

class NoticiaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testValidacionCamposVacios() {
        // Usamos el ViewModel por defecto (o uno fake, aquí da igual porque probamos validación visual)
        val viewModel = PostViewModel()

        composeTestRule.setContent {
            NoticiaScreen(viewModel = viewModel)
        }

        // 1. Verificamos que el botón existe
        composeTestRule.onNodeWithText("Agregar noticia").assertIsDisplayed()

        // 2. Hacemos clic sin escribir nada
        composeTestRule.onNodeWithText("Agregar noticia").performClick()

        // 3. Verificamos que aparezcan los mensajes de error
        composeTestRule.onNodeWithText("El título no puede estar vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("La descripción no puede estar vacía").assertIsDisplayed()
    }

    @Test
    fun testEscrituraDeCampos() {
        val viewModel = PostViewModel()
        composeTestRule.setContent {
            NoticiaScreen(viewModel = viewModel)
        }

        // Escribir en título
        composeTestRule.onNodeWithText("Título de la noticia")
            .performTextInput("Nuevo Título")

        // Verificar que se escribió
        composeTestRule.onNodeWithText("Nuevo Título").assertIsDisplayed()
    }
}