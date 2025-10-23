package com.example.infocapitos.data.remote.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infocapitos.data.remote.model.Noticia
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticiaDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addNews(noticia: Noticia)

    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllNews(): Flow<List<Noticia>>

    @Delete
    suspend fun deleteNews(noticia: Noticia)
}