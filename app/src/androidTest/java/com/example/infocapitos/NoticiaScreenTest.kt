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

        val viewModel = PostViewModel()

        composeTestRule.setContent {
            NoticiaScreen(viewModel = viewModel)
        }


        composeTestRule.onNodeWithText("Agregar noticia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Agregar noticia").performClick()
        composeTestRule.onNodeWithText("El título no puede estar vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("La descripción no puede estar vacía").assertIsDisplayed()
    }

    @Test
    fun testEscrituraDeCampos() {
        val viewModel = PostViewModel()
        composeTestRule.setContent {
            NoticiaScreen(viewModel = viewModel)
        }


        composeTestRule.onNodeWithText("Título de la noticia")
            .performTextInput("Nuevo Título")

        composeTestRule.onNodeWithText("Nuevo Título").assertIsDisplayed()
    }
}