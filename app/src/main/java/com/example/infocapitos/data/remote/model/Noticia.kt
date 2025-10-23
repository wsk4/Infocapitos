package com.example.infocapitos.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class Noticia (
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String
    )