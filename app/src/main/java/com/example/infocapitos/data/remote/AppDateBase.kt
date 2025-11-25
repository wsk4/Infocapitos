package com.example.infocapitos.data.remote


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.infocapitos.data.remote.dao.UserImageDao
import com.example.infocapitos.data.remote.model.UserImage


@Database(entities = [UserImage::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userImageDao(): UserImageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}