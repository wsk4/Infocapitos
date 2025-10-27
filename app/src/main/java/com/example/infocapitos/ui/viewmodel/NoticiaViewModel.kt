package com.example.infocapitos.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.dao.NoticiaDao
import com.example.infocapitos.data.remote.model.Noticia
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoticiaViewModel(private val dao: NoticiaDao) : ViewModel() {

    // Lista de noticias desde la base de datos
    val noticia = dao.getAllNews()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Campos del formulario
    var name = mutableStateOf("")
    var nameError = mutableStateOf<String?>(null)

    var description = mutableStateOf("")
    var descriptionError = mutableStateOf<String?>(null)

    // Funciones para actualizar los campos
    fun onNameChange(value: String) {
        name.value = value
        nameError.value = when {
            value.isBlank() -> "El título no puede estar vacío"
            value.length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
    }

    fun onDescriptionChange(value: String) {
        description.value = value
        descriptionError.value = when {
            value.isBlank() -> "La descripción no puede estar vacía"
            else -> null
        }
    }

    // Validación central del formulario
    fun validateForm(): Boolean {
        // Actualiza los errores al validar
        onNameChange(name.value)
        onDescriptionChange(description.value)
        return nameError.value == null && descriptionError.value == null
    }

    // Agregar noticia
    fun addNews(title: String, description: String) {
        viewModelScope.launch {
            dao.addNews(Noticia(title = title, description = description))
        }
    }

    // Eliminar noticia
    fun deleteNews(noticia: Noticia) {
        viewModelScope.launch {
            dao.deleteNews(noticia)
        }
    }
}
