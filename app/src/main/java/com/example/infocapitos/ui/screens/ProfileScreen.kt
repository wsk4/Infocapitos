package com.example.infocapitos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController // 1. Asegúrate de importar NavController
import com.example.infocapitos.navigation.Routes // 2. Asegúrate de importar Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) { // 3. Acepta el NavController
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Pantalla de perfil")

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Tu botón ahora tiene el navController y funcionará
            Button(onClick = {
                navController.navigate(Routes.PICTURE)
            }) {
                Text("Ir a Subir Archivo")
            }
        }
    }
}