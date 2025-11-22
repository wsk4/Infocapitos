package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.dao.UserImageDao
import com.example.infocapitos.data.remote.model.UserImage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val dao: UserImageDao) : ViewModel() {

    // Observa la base de datos y mantiene el estado de la imagen actualizado
    val currentImage = dao.getLastImage().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Función para guardar la URI en la base de datos
    fun saveImage(uri: String) {
        viewModelScope.launch {
            dao.insertImage(UserImage(imageUri = uri))
        }
    }
}