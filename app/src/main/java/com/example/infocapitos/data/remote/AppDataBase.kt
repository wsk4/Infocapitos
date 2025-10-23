package com.example.infocapitos.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.infocapitos.data.remote.dao.NoticiaDao
import com.example.infocapitos.data.remote.model.Noticia

@Database(entities = [Noticia::class], version = 1)
abstract class AppDataBase : RoomDatabase (){

    abstract fun newsDao(): NoticiaDao

    companion object{
        @Volatile private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "news_db"
                 ).build().also { INSTANCE = it }
            }
        }
    }
}