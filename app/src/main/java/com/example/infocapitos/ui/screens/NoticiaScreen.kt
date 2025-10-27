package com.example.infocapitos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infocapitos.data.remote.model.Noticia
import com.example.infocapitos.ui.viewmodel.NoticiaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaScreen(viewModel: NoticiaViewModel) {
    val news by viewModel.noticia.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nueva publicación") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Título
            OutlinedTextField(
                value = viewModel.name.value,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Título de la noticia") },
                isError = viewModel.nameError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            viewModel.nameError.value?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Descripción
            OutlinedTextField(
                value = viewModel.description.value,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descripción de la noticia") },
                isError = viewModel.descriptionError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            viewModel.descriptionError.value?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón agregar noticia
            Button(
                onClick = {
                    if (viewModel.validateForm()) {
                        viewModel.addNews(viewModel.name.value, viewModel.description.value)
                        viewModel.onNameChange("")
                        viewModel.onDescriptionChange("")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar noticia")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de noticias
            LazyColumn {
                items(news) { noticia ->
                    NoticiaItem(noticia = noticia, onDelete = { viewModel.deleteNews(noticia) })
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = noticia.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = noticia.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onDelete(noticia) }, modifier = Modifier.fillMaxWidth()) {
                Text("Eliminar")
            }
        }
    }
}
