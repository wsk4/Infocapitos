package com.example.infocapitos.data.repository

import com.example.infocapitos.data.remote.RetrofitInstance
import com.example.infocapitos.data.remote.model.PostNews

class PostNewRepository {
    suspend fun getNoticias(): List<PostNews> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun addNoticia(postNews: PostNews): PostNews {
        return RetrofitInstance.api.addNoticias(postNews)
    }
}
