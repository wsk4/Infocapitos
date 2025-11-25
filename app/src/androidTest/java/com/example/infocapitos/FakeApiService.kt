package com.example.infocapitos

import com.example.infocapitos.data.remote.ApiService
import com.example.infocapitos.data.remote.model.PostNews

class FakeApiService : ApiService {

    private val fakeDb = mutableListOf(
        PostNews(
            id = 1,
            titulo = "La caida de mascapitos",
            descripcion = "El dream team del qeu todo el mundo habla, falla por primera vez en su historia a falta de 20 minutos, 20 minutos que los separaron de la gloria eterna."
        ),

        PostNews(2, "Renato care pato", "El maty es genial, es el mejor amigo y compañero uqe existe "),

        PostNews(3, "colo colo eterno campeon", "veliz perro te amo")
    )

    override suspend fun getPosts(): List<PostNews> {
        return fakeDb.toList()
    }

    override suspend fun addNoticias(new: PostNews): PostNews {
        val newId = (fakeDb.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        val postWithId = new.copy(id = newId)
        fakeDb.add(postWithId)
        return postWithId
    }
}