package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.infocapitos.ui.screens.ProfileScreen
import com.example.infocapitos.ui.viewmodel.ProfileViewModel
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMuestraIconoPorDefecto() {
        // 1. Usamos el DAO falso
        val fakeDao = FakeUserImageDao()
        val viewModel = ProfileViewModel(fakeDao)

        composeTestRule.setContent {
            // 2. Usamos el controlador normal (ya incluido en Compose)
            val navController = rememberNavController()
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        // 3. Verificamos que se muestra el icono (que tiene contentDescription="Icono por defecto")
        composeTestRule.onNodeWithContentDescription("Icono por defecto").assertIsDisplayed()
        composeTestRule.onNodeWithText("Usuario Infocap").assertIsDisplayed()
    }

    @Test
    fun testBotonNavegacionExiste() {
        val fakeDao = FakeUserImageDao()
        val viewModel = ProfileViewModel(fakeDao)

        composeTestRule.setContent {
            val navController = rememberNavController()
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        // Verificamos que el botón se puede clicar (aunque no comprobemos adónde va)
        composeTestRule.onNodeWithText("Editar Foto / Subir Archivo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Editar Foto / Subir Archivo").performClick()
    }
}