package com.example.infocapitos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infocapitos.data.remote.model.Noticia
import com.example.infocapitos.ui.viewmodel.NoticiaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiaScreen(viewModel: NoticiaViewModel){
    val news by viewModel.noticia.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nueva publicación") })
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding( 16.dp)
                .fillMaxSize()
        ){
            OutlinedTextField(
                value = viewModel.name.value,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Titulo de la noticia") },
                isError = viewModel.nameError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (viewModel.nameError.value != null) {
                Text(
                    text = viewModel.nameError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.description.value,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Descripción de la noticia") },
                isError = viewModel.descriptionError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (viewModel.descriptionError.value != null) {
                Text(
                    text = viewModel.descriptionError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if(description.isNotEmpty() && title.isNotEmpty()){
                        viewModel.addNews(title, description)
                        title = ""
                        description = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar noticia")
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(news){ news ->
                    NoticiaItem(noticia = news, onDelete = { viewModel.deleteNews(news) })
                }
            }
        }
    }
}

@Composable
fun NoticiaItem(noticia: Noticia, onDelete: (Noticia) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onDelete(noticia) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column (modifier = Modifier.padding(12.dp)){
            Text(text = noticia.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "${noticia.description} descripción ", style = MaterialTheme.typography.bodyMedium)
        }
    }
}