package com.example.infocapitos.ui.screens

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.infocapitos.ui.viewmodel.ImagenViewModel // Usamos tu nombre de ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileUploadScreen(imagenViewModel: ImagenViewModel = viewModel()) {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver

    // Mantenemos el estado local para la vista previa
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }

    // 🚨 NUEVO ESTADO: URI de destino de la foto (necesaria para el launcher)
    var cameraFileUri by remember { mutableStateOf<Uri?>(null) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Función auxiliar para crear un archivo de imagen en el almacenamiento (antes de tomar la foto)
    fun createCameraFileUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "perfil_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    // 1. LANZADOR DE CÁMARA (Guarda la URI y notifica al ViewModel)
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture() // Usa TakePicture (requiere URI)
    ) { success ->
        if (success) {
            val uri = cameraFileUri

            // 🚨 PERSISTENCIA: Guardar la URI de la foto recién tomada en el DAO
            uri?.toString()?.let {
                imagenViewModel.saveProfileImageUri(it)
                Toast.makeText(context, "Foto de cámara guardada en el perfil.", Toast.LENGTH_SHORT).show()
            }

            // Mostrar la imagen
            imageUri = uri
        } else {
            Toast.makeText(context, "Captura de cámara cancelada.", Toast.LENGTH_SHORT).show()
        }
        bitmap = null
        fileUri = null
        cameraFileUri = null // Limpiar la URI temporal
    }

    // 2. LANZADOR DE PERMISOS (Prepara la URI antes de la cámara)
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            if (isGranted) {
                val uri = createCameraFileUri() // Crea el archivo de destino
                cameraFileUri = uri // Almacena la URI de destino

                if (uri != null) {
                    Toast.makeText(context, "Permiso concedido, abriendo cámara...", Toast.LENGTH_SHORT).show()
                    takePictureLauncher.launch(uri) // Lanza la cámara con la URI de destino
                } else {
                    Toast.makeText(context, "Error creando archivo.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // 3. LANZADOR DE GALERÍA (Obtiene la URI y la guarda)
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        bitmap = null
        fileUri = null

        if (uri != null) {
            // 🚨 PERSISTENCIA: Guardar la URI de la imagen seleccionada
            imagenViewModel.saveProfileImageUri(uri.toString())
            Toast.makeText(context, "Foto guardada en el perfil.", Toast.LENGTH_SHORT).show()
        }
    }

    // 4. LANZADOR DE ARCHIVOS
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

            // Botón de Cámara: Usa el launcher de permisos/preparación
            Button(onClick = {
                if (hasCameraPermission) {
                    // Si ya tiene permiso, lanza la preparación (que luego lanza la cámara)
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
                Text("📷 Tomar Foto")
            }

            // Botón de Galería
            Button(onClick = { selectImageLauncher.launch("image/*") }) {
                Text("🖼️ Seleccionar Imagen")
            }

            // Botón de Archivos
            Button(onClick = { selectFileLauncher.launch(arrayOf("*/*")) }) {
                Text("📁 Seleccionar Archivo")
            }
            Divider(Modifier.padding(vertical = 16.dp))

            // Visualización de resultados
            when {
                // Ya no usamos 'bitmap' para la foto tomada, usamos 'imageUri' (el mismo que galería)
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