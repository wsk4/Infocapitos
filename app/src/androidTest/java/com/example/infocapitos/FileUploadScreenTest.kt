package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.infocapitos.ui.screens.FileUploadScreen
import com.example.infocapitos.ui.viewmodel.ProfileViewModel
import org.junit.Rule
import org.junit.Test

class FileUploadScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testInterfazDeCarga() {
        val fakeDao = FakeUserImageDao()
        val viewModel = ProfileViewModel(fakeDao)

        composeTestRule.setContent {
            // Usamos el controlador estándar
            val navController = rememberNavController()
            FileUploadScreen(navController = navController, viewModel = viewModel)
        }

        // Verificamos que el título y las opciones están presentes
        composeTestRule.onNodeWithText("Selecciona una opción:").assertIsDisplayed()

        composeTestRule.onNodeWithText("📷 Tomar Foto Nueva").assertIsDisplayed()
        composeTestRule.onNodeWithText("🖼️ Seleccionar de Galería").assertIsDisplayed()
        composeTestRule.onNodeWithText("📁 Subir otro archivo").assertIsDisplayed()
    }
}