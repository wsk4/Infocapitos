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
import com.example.infocapitos.data.remote.AppDataBase // Importar AppDataBase
import com.example.infocapitos.navigation.BottomBar
import com.example.infocapitos.navigation.BottomNavNoticia
import com.example.infocapitos.navigation.Routes
import com.example.infocapitos.ui.screens.* import com.example.infocapitos.ui.theme.InfocapitosTheme
import com.example.infocapitos.ui.viewmodel.PostViewModel
import com.example.infocapitos.ui.viewmodel.ImagenViewModel // Importar ProfileViewModel
import com.example.infocapitos.ui.viewmodel.ImageViewModelFactory // Importar Factory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. 💾 INICIALIZACIÓN DE LA PERSISTENCIA (DAO y Factory)

        // Obtener el DAO del perfil desde la base de datos Singleton
        val profileDao = AppDataBase.getDatabase(application).imagenDao()

        // Crear la Factory para inyectar el DAO al ProfileViewModel
        val profileFactory = ImageViewModelFactory(profileDao)

        setContent {
            InfocapitosTheme {
                val navController = rememberNavController()
                val bottomNoticias = listOf(
                    BottomNavNoticia.Home,
                    BottomNavNoticia.Add,
                    BottomNavNoticia.Profile,
                    BottomNavNoticia.Picture
                )

                // 2. 🧠 INSTANCIACIÓN DE VIEWMDELS

                // PostViewModel (sin argumentos, usa constructor por defecto)
                val postViewModel: PostViewModel = viewModel()

                // ProfileViewModel (usa la Factory para inyectar el DAO)
                val imagenViewModel: ImagenViewModel = viewModel(factory = profileFactory)

                Scaffold(
                    bottomBar = { BottomBar(navController, bottomNoticias) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Noticias (API Live)
                        composable(Routes.HOME) {
                            HomeScreen(viewModel = postViewModel, onItemClick = { id ->
                                navController.navigate(Routes.detailRoute(id))
                            })
                        }

                        composable(Routes.ADD) {
                            NoticiaScreen(viewModel = postViewModel)
                        }

                        composable(Routes.DETAIL,
                            arguments = listOf(navArgument("noticiaId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("noticiaId") ?: -1
                            DetailScreen(
                                noticiaId = id,
                                viewModel = postViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        // Perfil (Persistencia de Foto)
                        composable(Routes.PROFILE) {
                            // 🚨 INYECCIÓN: Pasamos el ViewModel del perfil
                            ProfileScreen(navController = navController, imagenViewModel = imagenViewModel)
                        }

                        composable(Routes.PICTURE) {
                            // 🚨 INYECCIÓN: Pasamos el ViewModel para guardar la URI
                            FileUploadScreen(imagenViewModel = imagenViewModel)
                        }
                    }
                }
            }
        }
    }
}