package com.example.infocapitos.ui.screens

import androidx.compose.foundation.layout.*
// Imports añadidos para estilos y funcionalidad de scroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight // Para negrita (font-weight)
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Para el alto de línea (line-height)
import com.example.infocapitos.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(noticiaId: Int, viewModel: MainViewModel, onBack: () -> Unit) {
    val noticia = viewModel.getNoticia(noticiaId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            if (noticia != null) {
                Text(
                    text = noticia.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))
                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(Modifier.height(16.dp))

                Text(
                    text = noticia.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            } else {
                Text("Noticia no encontrada")
            }
        }
    }
}