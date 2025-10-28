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
import com.example.infocapitos.ui.viewmodel.NoticiaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaScreen(viewModel: NoticiaViewModel) {
    val news by viewModel.noticia.collectAsState()

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
                value = viewModel.name.value,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Título de la noticia") },
                isError = viewModel.nameError.value != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            viewModel.nameError.value?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.description.value,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descripción de la noticia") },
                isError = viewModel.descriptionError.value != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            viewModel.descriptionError.value?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (viewModel.validateForm()) {
                        viewModel.addNews(viewModel.name.value, viewModel.description.value)
                        viewModel.onNameChange("")
                        viewModel.onDescriptionChange("")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
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
                    NoticiaItem(noticia = news, onDelete = { viewModel.deleteNews(news) })
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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)) // border
    ){

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = noticia.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = noticia.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
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