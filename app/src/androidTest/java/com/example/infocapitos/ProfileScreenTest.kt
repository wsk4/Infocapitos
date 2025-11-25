package com.example.infocapitos

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.infocapitos.ui.screens.ProfileScreen
import com.example.infocapitos.ui.viewmodel.ProfileViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMuestraIconoPorDefecto() {
        val fakeDao = FakeUserImageDao()
        val viewModel = ProfileViewModel(fakeDao)

        composeTestRule.setContent {
            val navController = rememberNavController()
            ProfileScreen(navController = navController, viewModel = viewModel)
        }


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

        composeTestRule.onNodeWithText("Editar Foto / Subir Archivo").assertIsDisplayed()


    }
}