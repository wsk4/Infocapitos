package com.example.infocapitos.data.repository

import com.example.infocapitos.data.remote.RetrofitInstance
import com.example.infocapitos.data.remote.model.PostNews

class PostRepository {

    suspend fun getPosts(): List<PostNews> {
        return try {
            RetrofitInstance.api.getPosts()
        } catch (e: Exception) {
            println("Error al obtener datos de la red: ${e.localizedMessage}")
            emptyList()
        }
    }
}