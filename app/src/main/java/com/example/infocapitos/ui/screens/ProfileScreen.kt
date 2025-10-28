package com.example.infocapitos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape // Para el border-radius
import androidx.compose.material.icons.Icons // Para los iconos
import androidx.compose.material.icons.filled.AccountCircle // Icono de perfil
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight // Para font-weight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infocapitos.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
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

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Icono de perfil",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )

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