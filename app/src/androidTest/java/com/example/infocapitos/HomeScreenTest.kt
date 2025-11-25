package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.infocapitos.data.remote.model.PostNews
import com.example.infocapitos.ui.screens.HomeScreenContent
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMuestraNoticiasCargadas() {
        val noticiasDePrueba = listOf(
            PostNews(
                id = 1,
                titulo = "Renato care pato",
                descripcion = "Esta es la descripción de prueba 1"
            ),
            PostNews(
                id = 2,
                titulo = "colo colo eterno campeon",
                descripcion = "El popular ganó nuevamente"
            )
        )

        composeTestRule.setContent {
            HomeScreenContent(
                noticias = noticiasDePrueba,
                onItemClick = {}
            )
        }


        composeTestRule.onNodeWithText("Renato care pato").assertIsDisplayed()
        composeTestRule.onNodeWithText("colo colo eterno campeon").assertIsDisplayed()
    }
}