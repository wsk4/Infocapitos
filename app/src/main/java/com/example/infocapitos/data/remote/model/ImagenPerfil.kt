package com.example.infocapitos.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imagenPerfil")
data class ImagenPerfil(
    @PrimaryKey val id: Int = 1,
    val imagen: String? = null
)