package com.example.infocapitos.data.remote


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.infocapitos.data.remote.dao.UserImageDao
import com.example.infocapitos.data.remote.model.UserImage

// Agregamos UserImage al array de entities y subimos la versión
@Database(entities = [UserImage::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userImageDao(): UserImageDao // Agregamos el nuevo DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database" // Nombre genérico para la DB
                )
                    .fallbackToDestructiveMigration() // IMPORTANTE: Borra la DB vieja si cambias estructuras
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}