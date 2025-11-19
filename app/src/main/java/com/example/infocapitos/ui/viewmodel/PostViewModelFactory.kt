package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infocapitos.data.remote.dao.NoticiaDao
import com.example.infocapitos.data.repository.PostRepository

class PostViewModelFactory(private val dao: NoticiaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            // Se inyecta el DAO al Repositorio, y luego el Repositorio al ViewModel
            val repository = PostRepository(dao)
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}