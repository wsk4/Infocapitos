package com.example.infocapitos.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infocapitos.ui.viewmodel.AddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(vm: AddViewModel = viewModel()) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Nueva Publicacion") })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = vm.name.value,
                onValueChange = { vm.onNameChange(it) },
                label = { Text("Titulo de la noticia") },
                isError = vm.nameError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (vm.nameError.value != null) {
                Text(
                    text = vm.nameError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = vm.description.value,
                onValueChange = { vm.onDescriptionChange(it) },
                label = { Text("Descripción") },
                isError = vm.descriptionError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (vm.descriptionError.value != null) {
                Text(
                    text = vm.descriptionError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Guardar en BD o Firebase */ },
                enabled = vm.isFormValid(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
