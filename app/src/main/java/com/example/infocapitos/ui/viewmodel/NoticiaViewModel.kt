package com.example.infocapitos.ui.viewmodel

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
}