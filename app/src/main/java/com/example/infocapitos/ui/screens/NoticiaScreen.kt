package com.example.infocapitos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.infocapitos.data.remote.model.Noticia
import com.example.infocapitos.ui.viewmodel.PostViewModel // Actualizado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaScreen(viewModel: PostViewModel) {
    // Usa el StateFlow de noticias del PostViewModel
    val news by viewModel.noticias.collectAsState()

    // Estados locales para el formulario y validación
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }

    fun validateForm(): Boolean {
        nameError = when {
            name.isBlank() -> "El título no puede estar vacío"
            name.length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
        descriptionError = if (description.isBlank()) "La descripción no puede estar vacía" else null
        return nameError == null && descriptionError == null
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nueva publicación") },
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
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; nameError = null },
                label = { Text("Título de la noticia") },
                isError = nameError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            nameError?.let { error ->
                Text(text = error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it; descriptionError = null },
                label = { Text("Descripción de la noticia") },
                isError = descriptionError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            descriptionError?.let { error ->
                Text(text = error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (validateForm()) {
                        viewModel.addNoticia(name, description)
                        name = "" // Limpia
                        description = "" // Limpia
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Agregar noticia", modifier = Modifier.padding(vertical = 4.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Noticias Publicadas",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(news){ news ->
                    NoticiaItem(noticia = news, onDelete = { viewModel.deleteNoticia(news) })
                }
            }
        }
    }
}

@Composable
fun NoticiaItem(noticia: Noticia, onDelete: (Noticia) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    ){

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = noticia.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = noticia.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f))
            }

            IconButton(onClick = { onDelete(noticia) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar noticia",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}