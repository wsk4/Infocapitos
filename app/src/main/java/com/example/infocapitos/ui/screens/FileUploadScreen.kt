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
    // --- LÓGICA DE PERMISOS ---
    // PASO 1: Estado para saber si tenemos el permiso de cámara.
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    // --- LANZADORES DE ACTIVIDADES ---
    // 📸 Tomar foto con cámara (retorna Bitmap)
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bmp ->
        bitmap = bmp
        imageUri = null
        fileUri = null
    }
    // PASO 2: Launcher para solicitar el permiso de cámara.
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            if (isGranted) {
                // Si el usuario da el permiso, lanzamos la cámara inmediatamente.
                Toast.makeText(context, "Permiso concedido, abriendo cámara...", Toast.LENGTH_SHORT).show()
                takePictureLauncher.launch(null)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )
    // 🖼️ Seleccionar imagen desde galería
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        bitmap = null
        fileUri = null
    }
    // 📂 Seleccionar cualquier archivo (PDF, Word, etc.)
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

            // PASO 3: Modificar el onClick del botón de la cámara.
            Button(onClick = {
                if (hasCameraPermission) {
                    // Si ya tenemos el permiso, lanzamos la cámara directamente.
                    takePictureLauncher.launch(null)
                } else {
                    // Si no, pedimos el permiso. El launcher se encargará del resto.
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

            // La lógica de la vista previa se mantiene igual, ¡perfecto!
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