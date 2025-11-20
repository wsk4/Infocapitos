package com.example.infocapitos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape // Para la forma de la imagen
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Para inyectar el ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage // Para cargar la imagen desde la URI
import com.example.infocapitos.navigation.Routes
import com.example.infocapitos.ui.viewmodel.ProfileViewModel // ViewModel del perfil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// 🚨 CAMBIO: Se añade ProfileViewModel como argumento con valor por defecto
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {

    // 🚨 CLAVE: Observar el estado del perfil desde Room
    val profileImage by profileViewModel.profileImage.collectAsState()
    val profileImagePath = profileImage.imagen // La ruta o URI guardada

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (!profileImagePath.isNullOrEmpty()) {
                // 🚨 Mostrar la imagen guardada si existe la ruta
                AsyncImage(
                    model = profileImagePath,
                    contentDescription = "Foto de Perfil del Usuario",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape), // Corta la imagen en un círculo
                    contentScale = ContentScale.Crop
                )
            } else {
                // 🚨 Mostrar el icono por defecto si no hay imagen guardada
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icono de perfil",
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Pantalla de perfil",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.PICTURE)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Ir a Subir Archivo",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}