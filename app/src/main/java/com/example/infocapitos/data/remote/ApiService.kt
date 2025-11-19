package com.example.infocapitos.data.remote

import com.example.infocapitos.data.remote.model.PostNews
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostNews>
}