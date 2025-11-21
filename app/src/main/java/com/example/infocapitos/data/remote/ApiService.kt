package com.example.infocapitos.data.remote

import com.example.infocapitos.data.remote.model.PostNews
import retrofit2.http.GET

interface ApiService {
    @GET("noticias")
    suspend fun getPosts(): List<PostNews>
}