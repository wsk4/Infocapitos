package com.example.infocapitos.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.infocapitos.data.remote.AppDataBase
import com.example.infocapitos.ui.viewmodel.ProfileViewModel
import com.example.infocapitos.ui.viewmodel.ProfileViewModelFactory
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileUploadScreen(
    navController: NavController,
    // CAMBIO CLAVE: Inyectamos el ViewModel
    viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(AppDataBase.getDatabase(LocalContext.current).userImageDao())
    )
) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    // --- LAUNCHERS ---
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bmp ->
        if (bmp != null) {
            val savedUri = saveBitmapToCache(context, bmp)
            viewModel.saveImage(savedUri.toString())
            Toast.makeText(context, "Foto guardada", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            if (isGranted) {
                takePictureLauncher.launch(null)
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            viewModel.saveImage(uri.toString())
            Toast.makeText(context, "Imagen actualizada", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    val selectFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        fileUri = uri
        imageUri = null
        bitmap = null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subir Foto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Selecciona una opción:", style = MaterialTheme.typography.titleMedium)

            Button(onClick = {
                if (hasCameraPermission) {
                    try {
                        takePictureLauncher.launch(null)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error al abrir cámara", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("📷 Tomar Foto Nueva")
            }

            Button(onClick = { selectImageLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                Text("🖼️ Seleccionar de Galería")
            }

            OutlinedButton(onClick = { selectFileLauncher.launch(arrayOf("*/*")) }, modifier = Modifier.fillMaxWidth()) {
                Text("📁 Subir otro archivo")
            }

            Divider(Modifier.padding(vertical = 16.dp))

            if (fileUri != null) {
                Text("📁 Archivo: ${fileUri?.lastPathSegment}")
            }
        }
    }
}

private fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "profile_captured_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { stream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    }
    return Uri.fromFile(file)
}