package com.example.infocapitos.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_images")
data class UserImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String // Guardaremos la ruta de la imagen como texto
)