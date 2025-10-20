package com.example.infocapitos.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {

    var name = mutableStateOf("")
    var nameError = mutableStateOf<String?>(null)

    var description = mutableStateOf("")
    var descriptionError = mutableStateOf<String?>(null)

    fun onNameChange(value: String) {
        name.value = value
        nameError.value = when {
            value.isBlank() -> "El nombre no puede estar vacío"
            value.length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
    }

    fun onDescriptionChange(value: String) {
        description.value = value
        descriptionError.value = when {
            value.isBlank() -> "La descripción no puede estar vacía"
            else -> null
        }
    }

    fun isFormValid(): Boolean {
        return nameError.value == null &&
                descriptionError.value == null &&
                name.value.isNotBlank() &&
                description.value.isNotBlank()
    }
}