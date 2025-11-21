package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infocapitos.data.remote.dao.ImagenDao

class ImageViewModelFactory(private val dao: ImagenDao): ViewModelProvider.Factory {

    // Este método es llamado por el sistema Android para crear el ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 1. Verificamos que la clase solicitada sea ProfileViewModel
        if (modelClass.isAssignableFrom(ImagenViewModel::class.java)){

            // 2. Creamos e inyectamos la dependencia (ProfileDao)
            @Suppress("UNCHECKED_CAST")
            return ImagenViewModel(dao) as T
        }

        // Si se pide una clase que no conocemos, lanzamos una excepción
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}