package com.example.infocapitos.ui.screens


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun FileUploadScreen() {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bmp ->
        bitmap = bmp
        imageUri = null
        fileUri = null
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            if (isGranted) {
                Toast.makeText(context, "Permiso concedido, abriendo cámara...", Toast.LENGTH_SHORT).show()
                takePictureLauncher.launch(null)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        bitmap = null
        fileUri = null
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
            TopAppBar(title = { Text("Gestor de Archivos 📂") })
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
            Text(
                text = "Selecciona una opción:",
                style = MaterialTheme.typography.titleMedium
            )

            Button(onClick = {
                if (hasCameraPermission) {
                    takePictureLauncher.launch(null)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
                Text("📷 Tomar Foto")
            }
            Button(onClick = { selectImageLauncher.launch("image/*") }) {
                Text("🖼️ Seleccionar Imagen")
            }
            Button(onClick = { selectFileLauncher.launch(arrayOf("*/*")) }) {
                Text("📁 Seleccionar Archivo")
            }
            Divider(Modifier.padding(vertical = 16.dp))

            when {
                bitmap != null -> {
                    Text("📸 Foto tomada:")
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                imageUri != null -> {
                    Text("🖼️ Imagen seleccionada:")
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                fileUri != null -> {
                    Text("📁 Archivo seleccionado: ${fileUri?.lastPathSegment}")
                }
                else -> {
                    Text("Ningún archivo seleccionado aún")
                }
            }
        }
    }
}