package com.example.infocapitos.data.remote

import com.example.infocapitos.data.remote.model.PostNews
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("noticias")
    suspend fun getPosts(): List<PostNews>

    @POST("noticias")
    suspend fun addNoticias(@Body new: PostNews): PostNews
}
