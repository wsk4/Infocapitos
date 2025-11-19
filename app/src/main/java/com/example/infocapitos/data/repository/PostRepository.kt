package com.example.infocapitos.data.repository

import com.example.infocapitos.data.remote.RetrofitInstance
import com.example.infocapitos.data.remote.dao.NoticiaDao
import com.example.infocapitos.data.remote.model.Noticia
import kotlinx.coroutines.flow.Flow

class PostRepository(private val dao: NoticiaDao) {

    // La UI siempre lee desde Room (caché)
    fun getAllNoticiasFlow(): Flow<List<Noticia>> = dao.getAllNews()

    // Carga los datos de la API y los guarda en Room
    suspend fun refreshPosts() {
        try {
            val remotePosts = RetrofitInstance.api.getPosts()

            // Mapeo e Inserción
            val noticias = remotePosts.map { postNews ->
                // Creamos una Noticia con id=0 para que Room autogenere el ID local
                Noticia(
                    id = 0,
                    title = postNews.title,
                    description = postNews.description
                )
            }
            dao.insertAll(noticias)
        } catch (e: Exception) {
            println("Error al obtener datos de la red: ${e.localizedMessage}")
        }
    }

    // Funciones CRUD para la gestión local
    suspend fun addNoticia(noticia: Noticia) = dao.addNews(noticia)
    suspend fun deleteNoticia(noticia: Noticia) = dao.deleteNews(noticia)
}