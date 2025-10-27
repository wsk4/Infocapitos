package com.example.infocapitos.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.dao.NoticiaDao
import com.example.infocapitos.data.remote.model.Noticia
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoticiaViewModel (private val dao: NoticiaDao): ViewModel() {


    val noticia = dao.getAllNews()
        .stateIn(
            viewModelScope,
            SharingStarted.Companion.WhileSubscribed(5000),
            emptyList()
        )


    fun addNews(title: String, description: String){
        viewModelScope.launch {
            dao.addNews(Noticia(title = title, description = description))
        }
    }


    fun deleteNews(noticia: Noticia){
        viewModelScope.launch {
            dao.deleteNews(noticia)
        }}

    var name = mutableStateOf("")
    var nameError = mutableStateOf<String?>(null)

    var description = mutableStateOf("")
    var descriptionError = mutableStateOf<String?>(null)

    fun onNameChange(value: String) {
        name.value = value
        nameError.value = when {
            value.isBlank() -> "El nombre no puede estar vacío"
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

    fun isFormValid(): Boolean {
        return nameError.value == null &&
                descriptionError.value == null &&
                name.value.isNotBlank() &&
                description.value.isNotBlank()
    }
}