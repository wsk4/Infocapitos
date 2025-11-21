package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.dao.ImagenDao
import com.example.infocapitos.data.remote.model.ImagenPerfil // Usamos la nueva entidad
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ImagenViewModel(private val dao: ImagenDao) : ViewModel() {

    // Observa la foto desde la base de datos
    val imagenPerfil = dao.getImagenPerfil()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ImagenPerfil() // Valor inicial: ProfileImage sin ruta
        )

    /**
     * Guarda la ruta URI de la imagen en la base de datos.
     * @param uriString La URI o ruta de la imagen en el almacenamiento local.
     */
    fun saveProfileImageUri(uriString: String) {
        viewModelScope.launch {
            // Se crea un nuevo objeto ProfileImage con la URI (ID=1 por defecto)
            val newImage = ImagenPerfil(imagen = uriString)
            dao.insert(newImage)
        }
    }
}