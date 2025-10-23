package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infocapitos.data.remote.dao.NoticiaDao
import kotlin.jvm.java

class NoticiaViewModelFactory (private val dao: NoticiaDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoticiaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoticiaViewModel(dao) as T
        }
        throw kotlin.IllegalArgumentException("Unknow ViewModel class")
    }
}