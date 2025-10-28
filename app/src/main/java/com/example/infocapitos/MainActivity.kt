package com.example.infocapitos

import android.app.Application // Importación necesaria
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.infocapitos.data.remote.AppDataBase
import com.example.infocapitos.navigation.BottomBar
import com.example.infocapitos.navigation.BottomNavNoticia
import com.example.infocapitos.navigation.Routes
import com.example.infocapitos.ui.screens.NoticiaScreen
import com.example.infocapitos.ui.screens.DetailScreen
import com.example.infocapitos.ui.screens.FileUploadScreen
import com.example.infocapitos.ui.screens.HomeScreen
import com.example.infocapitos.ui.screens.ProfileScreen
import com.example.infocapitos.ui.theme.InfocapitosTheme
import com.example.infocapitos.ui.viewmodel.MainViewModel
import com.example.infocapitos.ui.viewmodel.NoticiaViewModel
import com.example.infocapitos.ui.viewmodel.NoticiaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dao = AppDataBase.getDatabase(application).newsDao()
        val factory = NoticiaViewModelFactory(dao)


        setContent {
            InfocapitosTheme {
                val navController = rememberNavController()
                val bottomNoticias = listOf(BottomNavNoticia.Home,  BottomNavNoticia.Add, BottomNavNoticia.Profile,
                    BottomNavNoticia.Picture)



                Scaffold(
                    bottomBar = { BottomBar(navController, bottomNoticias) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME,
                        modifier = androidx.compose.ui.Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.HOME) {
                            val vm: MainViewModel = viewModel()
                            HomeScreen(viewModel = vm, onItemClick = { id ->
                                navController.navigate(Routes.detailRoute(id))
                            })
                        }

                        composable(Routes.ADD) {
                            val viewModel: NoticiaViewModel = viewModel(factory = factory)
                            NoticiaScreen(viewModel)            }

                        composable(Routes.PICTURE) {FileUploadScreen()}

                        composable(Routes.PROFILE) { ProfileScreen(navController = navController) }
                        composable(
                            route = Routes.DETAIL,
                            arguments = listOf(navArgument("noticiaId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val vm: MainViewModel = viewModel()
                            val id = backStackEntry.arguments?.getInt("noticiaId") ?: -1
                            DetailScreen(noticiaId = id, viewModel = vm, onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}