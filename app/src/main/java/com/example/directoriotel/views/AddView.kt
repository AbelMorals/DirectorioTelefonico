package com.example.directorio.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.directorio.components.ContactAvatar
import com.example.directorio.components.MainIconButton
import com.example.directorio.components.MainTextField
import com.example.directorio.components.MainTitle
import com.example.directorio.viewModels.ContactoViewModel
import com.example.directoriotel.images.ImageUtils
import com.example.directoriotel.model.Contacto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController, contactoVM: ContactoViewModel){
    var nombre by remember { mutableStateOf("") }
    var apellidosP by remember { mutableStateOf("") }
    var apellidosM by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<String?>(null) }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var apellidoPError by remember { mutableStateOf<String?>(null) }
    var apellidoMError by remember { mutableStateOf<String?>(null) }
    var correoError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    var direccionError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val (selectedImageUri, pickImage) = ImageUtils.rememberImagePicker()

    fun validarNombre(nombre: String): String? {
        return if (nombre.isBlank()) "El nombre no puede estar vacío" else null
    }

    fun validarApellidoP(apellidoP: String): String? {
        return if (apellidoP.isBlank()) "El apellido paterno no puede estar vacío" else null
    }

    fun validarApellidoM(apellidoM: String): String? {
        return if (apellidoM.isBlank()) "El apellido materno no puede estar vacío" else null
    }

    fun validarCorreo(correo: String): String? {
        return if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            "Introduce un correo válido (ej. usuario@dominio.com)"
        } else {
            null
        }
    }

    fun validarDireccion(direccion: String): String? {
        return if (direccion.isBlank())
            "La dirección no puede estar vacía"
        else null
    }
    fun validarTelefono(telefono: String): String? {
        return if (!telefono.all { it.isDigit() } || telefono.isBlank()) {
            "El teléfono solo debe contener números"
        } else {
            null
        }
    }

    LaunchedEffect(selectedImageUri) {
        selectedImageUri?.let {
            imagenUri = it.toString()
        }
    }

    fun eliminarImagen() {
        imagenUri = null
    }

    fun guardarContacto() {
        nombreError = validarNombre(nombre)
        apellidoPError = validarApellidoP(apellidosP)
        apellidoMError = validarApellidoM(apellidosM)
        correoError = validarCorreo(correo)
        telefonoError = validarTelefono(telefono)
        direccionError = validarDireccion(direccion)

        if (nombreError == null && correoError == null && telefonoError == null &&
            apellidoPError == null && apellidoMError == null && direccionError == null
        ) {
            contactoVM.addContacto(
                Contacto(
                    nombre = nombre.trim(),
                    apellidosP = apellidosP.trim(),
                    apellidosM = apellidosM.trim(),
                    correo = correo.trim(),
                    telefono = telefono.trim(),
                    direccion = direccion.trim(),
                    imagenUri = imagenUri?.toString()
                )
            )
            navController.popBackStack()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Nuevo Contacto") },
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                },
                actions = {
                    IconButton(onClick = {
                        guardarContacto()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Guardar Contacto",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactAvatar(
                imagenUri = imagenUri?.toString(),
                nombre = nombre,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { pickImage() }
            )

            if (imagenUri != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { eliminarImagen() },
                    ) {
                        Text("Eliminar imagen")
                    }
                }
            } else {
                Text(
                    text = "Toca el círculo para seleccionar una imagen",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            MainTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = validarNombre(it)
                },
                label = "Nombre",
                isError = nombreError != null,
                errorMessage = nombreError
            )

            MainTextField(
                value = apellidosP,
                onValueChange = {
                    apellidosP = it
                    apellidoPError = validarApellidoP(it)
                },
                label = "Apellido Paterno",
                isError = apellidoPError != null,
                errorMessage = apellidoPError
            )

            MainTextField(
                value = apellidosM,
                onValueChange = {
                    apellidosM = it
                    apellidoMError = validarApellidoM(it)
                },
                label = "Apellido Materno",
                isError = apellidoMError != null,
                errorMessage = apellidoMError
            )

            MainTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    correoError = validarCorreo(it)
                },
                label = "Correo",
                isError = correoError != null,
                errorMessage = correoError,
                keyboardType = KeyboardType.Email
            )

            MainTextField(
                value = telefono,
                onValueChange = {
                    telefono = it
                    telefonoError = validarTelefono(it)
                },
                label = "Teléfono",
                isError = telefonoError != null,
                errorMessage = telefonoError,
                keyboardType = KeyboardType.Phone
            )

            MainTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    direccionError = validarDireccion(it)
                },
                label = "Dirección",
                isError = direccionError != null,
                errorMessage = direccionError,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            val addressQuery = if (direccion.isNotBlank()) Uri.encode(direccion) else ""
                            val mapIntentUri = Uri.parse("geo:0,0?q=$addressQuery")
                            val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
                            startActivity(context, mapIntent, null)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Buscar Dirección en Mapa"
                        )
                    }
                }
            )
        }
    }
}