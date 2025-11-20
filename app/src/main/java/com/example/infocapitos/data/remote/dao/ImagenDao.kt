package com.example.infocapitos.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infocapitos.data.remote.model.ImagenPerfil // Usamos la nueva entidad
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagenDao {

    // Inserta o reemplaza la única entrada de la foto de perfil
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profileImage: ImagenPerfil)

    // Obtiene la única foto de perfil como un Flow
    @Query("SELECT * FROM imagenPerfil WHERE id = 1")
    fun getImagenPerfil(): Flow<ImagenPerfil>
}