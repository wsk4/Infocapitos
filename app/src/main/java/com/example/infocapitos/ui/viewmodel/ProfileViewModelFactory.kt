package com.example.infocapitos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infocapitos.data.remote.dao.UserImageDao

class ProfileViewModelFactory(private val dao: UserImageDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}