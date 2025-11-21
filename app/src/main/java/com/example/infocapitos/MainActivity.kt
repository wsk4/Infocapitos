package com.example.infocapitos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.infocapitos.navigation.BottomBar
import com.example.infocapitos.navigation.BottomNavNoticia
import com.example.infocapitos.navigation.Routes
import com.example.infocapitos.ui.screens.*
import com.example.infocapitos.ui.theme.InfocapitosTheme
import com.example.infocapitos.ui.viewmodel.PostViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            InfocapitosTheme {
                val navController = rememberNavController()
                val bottomNoticias = listOf(
                    BottomNavNoticia.Home,
                    BottomNavNoticia.Add,
                    BottomNavNoticia.Profile
                )

                Scaffold(
                    bottomBar = { BottomBar(navController, bottomNoticias) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.HOME) {
                            HomeScreen(onItemClick = { id ->
                                navController.navigate(Routes.detailRoute(id))
                            })
                        }

                        composable(Routes.ADD) {
                            val postViewModel: PostViewModel = viewModel()
                            NoticiaScreen(viewModel = postViewModel)
                        }

                        composable(
                            Routes.DETAIL,
                            arguments = listOf(navArgument("noticiaId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("noticiaId") ?: -1
                            val postViewModel: PostViewModel = viewModel()
                            DetailScreen(
                                noticiaId = id,
                                viewModel = postViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(Routes.PROFILE) {
                            ProfileScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
