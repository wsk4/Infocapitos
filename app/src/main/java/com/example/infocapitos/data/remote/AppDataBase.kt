package com.example.infocapitos.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.infocapitos.data.remote.dao.ImagenDao
import com.example.infocapitos.data.remote.model.ImagenPerfil // Solo la entidad de imagen

// ⚠️ CAMBIO 1: La lista de entidades solo contiene ProfileImage.
// ⚠️ CAMBIO 2: La versión se establece en 1 (o la que corresponda si es un proyecto nuevo).
@Database(entities = [ImagenPerfil::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    // 🚨 CAMBIO 3: Solo se expone el DAO del perfil.
    abstract fun imagenDao(): ImagenDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "profile_db" // 🚨 Se recomienda cambiar el nombre del archivo DB
                ).build().also { INSTANCE = it }
            }
        }
    }
}