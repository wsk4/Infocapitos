package com.example.infocapitos

import com.example.infocapitos.data.remote.dao.UserImageDao
import com.example.infocapitos.data.remote.model.UserImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserImageDao : UserImageDao {

    private val fakeImageFlow = MutableStateFlow<UserImage?>(null)

    override suspend fun insertImage(userImage: UserImage) {
        fakeImageFlow.value = userImage
    }

    override fun getLastImage(): Flow<UserImage?> {
        return fakeImageFlow
    }
}