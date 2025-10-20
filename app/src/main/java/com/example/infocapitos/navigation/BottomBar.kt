package com.example.infocapitos.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

sealed class BottomNavNoticia(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {

    object Home : BottomNavNoticia(Routes.HOME, "Home", Icons.Default.Home)

    object Add : BottomNavNoticia(Routes.ADD, "Agregar", Icons.Default.Add)

    object Profile : BottomNavNoticia(Routes.PROFILE, "Perfil", Icons.Default.Person)
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomBar(navController: NavHostController, noticias: List<BottomNavNoticia>) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBar {
            noticias.forEach { noticia ->
                NavigationBarItem(
                    icon = { Icon(noticia.icon, contentDescription = noticia.label) },
                    label = { Text(noticia.label) },
                    selected = currentRoute == noticia.route,
                    onClick = {
                        if (currentRoute != noticia.route) {
                            navController.navigate(noticia.route) {
                                popUpTo(navController.graph.startDestinationRoute ?: Routes.HOME) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }