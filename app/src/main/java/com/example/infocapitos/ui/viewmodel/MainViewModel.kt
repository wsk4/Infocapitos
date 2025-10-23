package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.model.Noticia
import com.example.infocapitos.data.repository.SampleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel (
    private val repo: SampleRepository= SampleRepository()
): ViewModel(){

   private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())

   val noticias: StateFlow<List<Noticia>> = _noticias.asStateFlow()

   init {
       viewModelScope.launch {
           _noticias.value = repo.getAll()
       }
   }

    fun getNoticia(id: Int): Noticia? = repo.getById(id)
}