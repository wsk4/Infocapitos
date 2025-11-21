package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.model.PostNews
import com.example.infocapitos.data.repository.PostNewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PostViewModel : ViewModel() {

    private val repository = PostNewRepository()

    private val _noticiasList = MutableStateFlow<List<PostNews>>(emptyList())
    val noticiasList: StateFlow<List<PostNews>> = _noticiasList

    init {
        fetchNoticias()  // Carga inicial
        startPolling()   // Inicia refresco periódico
    }

    private fun fetchNoticias() {
        viewModelScope.launch {
            try {
                _noticiasList.value = repository.getNoticias()
            } catch (e: Exception) {
                println("Error al obtener noticias: ${e.localizedMessage}")
            }
        }
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                try {
                    val latest = repository.getNoticias()
                    _noticiasList.value = latest
                } catch (e: Exception) {
                    println("Error en polling noticias: ${e.localizedMessage}")
                }
                delay(3000) // Espera 30 segundos antes de la próxima consulta
            }
        }
    }

    fun getNoticiaById(id: Int): PostNews? {
        return _noticiasList.value.find { it.id == id }
    }

    fun addNoticia(titulo: String, descripcion: String) {
        viewModelScope.launch {
            try {
                val newPost = PostNews(id = 0, titulo = titulo, descripcion = descripcion)
                repository.addNoticia(newPost)
                // Opcional: actualizar inmediatamente tras insertar
                fetchNoticias()
            } catch (e: Exception) {
                println("Error al agregar noticia: ${e.localizedMessage}")
            }
        }
    }
}
