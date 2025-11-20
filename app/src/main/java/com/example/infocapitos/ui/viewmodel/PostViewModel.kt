package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infocapitos.data.remote.model.PostNews
import com.example.infocapitos.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _noticias = MutableStateFlow<List<PostNews>>(emptyList())
    val noticias: StateFlow<List<PostNews>> = _noticias.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _noticias.value = repository.getPosts()
        }
    }

    fun getNoticiaById(id: Int): PostNews? {
        return noticias.value.find { it.id == id }
    }
}