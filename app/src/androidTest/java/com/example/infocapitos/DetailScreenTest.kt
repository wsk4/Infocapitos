package com.example.infocapitos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.infocapitos.ui.screens.DetailScreenContent
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMuestraDetalleCorrecto() {
        val tituloPrueba = "Nueva Ley Aprobada"
        val descPrueba = "El congreso aprobó la ley de presupuestos para el 2025."
        composeTestRule.setContent {
            DetailScreenContent(
                titulo = tituloPrueba,
                descripcion = descPrueba,
                onBack = {}
            )
        }

        composeTestRule.onNodeWithText(tituloPrueba).assertIsDisplayed()
        composeTestRule.onNodeWithText(descPrueba).assertIsDisplayed()
    }
}