package com.example.infocapitos.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infocapitos.data.remote.model.UserImage
import kotlinx.coroutines.flow.Flow

@Dao
interface UserImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(userImage: UserImage)

    @Query("SELECT * FROM user_images ORDER BY id DESC LIMIT 1")
    fun getLastImage(): Flow<UserImage?>
}