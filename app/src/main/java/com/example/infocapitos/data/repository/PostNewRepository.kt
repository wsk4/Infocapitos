package com.example.infocapitos.data.repository

import com.example.infocapitos.data.remote.RetrofitInstance
import com.example.infocapitos.data.remote.model.PostNews
import com.example.infocapitos.data.remote.ApiService
class PostNewRepository(
    private val api: ApiService = RetrofitInstance.api
) {

    suspend fun getNoticias(): List<PostNews> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun addNoticia(postNews: PostNews): PostNews {
        return RetrofitInstance.api.addNoticias(postNews)
    }
}
