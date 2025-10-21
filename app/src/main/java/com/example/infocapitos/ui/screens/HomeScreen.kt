package com.example.infocapitos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infocapitos.data.model.Noticia
import com.example.infocapitos.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel, onItemClick: (Int) -> Unit) {
    val noticias = viewModel.noticias.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Lista de Noticias 3") })
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(noticias.value) { item ->
                ItemRow(noticia = item, onClick = { onItemClick(item.id) })
                Divider()
            }
        }
    }
}

@Composable
fun ItemRow(noticia: Noticia, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(noticia.title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(noticia.description, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
    }
}
