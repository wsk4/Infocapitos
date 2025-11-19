package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.model.Noticia
import com.example.infocapitos.data.repository.PostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    // 1. Estado Reactivo observado por HomeScreen y NoticiaScreen
    val noticias: StateFlow<List<Noticia>> = repository.getAllNoticiasFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        // Al iniciar, intentamos cargar los datos más recientes de la API
        viewModelScope.launch {
            repository.refreshPosts()
        }
    }

    // Usado por DetailScreen para obtener un post específico (busca en el StateFlow actual)
    fun getNoticiaById(id: Int): Noticia? {
        return noticias.value.find { it.id == id }
    }

    // Funciones de Gestión (Add/Delete)
    fun addNoticia(title: String, description: String) {
        viewModelScope.launch {
            repository.addNoticia(Noticia(title = title, description = description))
        }
    }

    fun deleteNoticia(noticia: Noticia) {
        viewModelScope.launch {
            repository.deleteNoticia(noticia)
        }
    }
}