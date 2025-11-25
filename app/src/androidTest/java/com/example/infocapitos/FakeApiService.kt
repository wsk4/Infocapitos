package com.example.infocapitos

import com.example.infocapitos.data.remote.ApiService
import com.example.infocapitos.data.remote.model.PostNews

class FakeApiService : ApiService {
    // Datos de prueba fijos
    private val fakeDb = mutableListOf(
        PostNews(1, "El maty es genial", "Es el mejro amigo y compañero uqe existe "),
        PostNews(2, "Para veliz", "veliz perro te amo")
    )

    override suspend fun getPosts(): List<PostNews> {
        return fakeDb
    }

    override suspend fun addNoticias(new: PostNews): PostNews {
        fakeDb.add(new)
        return new
    }
}