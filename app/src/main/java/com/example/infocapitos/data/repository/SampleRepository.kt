package com.example.infocapitos.data.repository

import com.example.infocapitos.data.remote.model.Noticia

class SampleRepository {

    private val noticias = List(20) { index ->
        Noticia(
            id= index,
            title = "noticia #$index",
            description = "Detalle de la noticia $index."
        )
    }

    fun getAll(): List<Noticia> = noticias

    fun getById(id: Int): Noticia? = noticias.find { it.id ==id}
}