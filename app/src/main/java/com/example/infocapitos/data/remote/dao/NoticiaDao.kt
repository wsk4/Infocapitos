package com.example.infocapitos.data.remote.dao

import androidx.room.*
import com.example.infocapitos.data.remote.model.Noticia
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticiaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(noticia: Noticia)

    // Para insertar múltiples noticias de la API en la caché
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(noticias: List<Noticia>)

    @Query("SELECT * FROM news_table ORDER BY id DESC")
    fun getAllNews(): Flow<List<Noticia>>

    @Delete
    suspend fun deleteNews(noticia: Noticia)
}