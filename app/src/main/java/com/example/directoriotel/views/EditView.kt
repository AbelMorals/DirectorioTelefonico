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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
fun EditView(navController: NavController, contactoVM: ContactoViewModel, id:Long){
    var nombre by remember { mutableStateOf("") }
    var apellidosP by remember { mutableStateOf("") }
    var apellidosM by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<String?>(null) }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var apellidoPError by remember { mutableStateOf<String?>(null) }
    var correoError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val (selectedImageUri, pickImage) = ImageUtils.rememberImagePicker()

    fun validarNombre(nombre: String): String? {
        return if (nombre.isBlank()) "El nombre no puede estar vacío" else null
    }

    fun validarApellidoP(apellidoP: String): String? {
        return if (apellidoP.isBlank()) "El apellido paterno no puede estar vacío" else null
    }

    fun validarCorreo(correo: String): String? {
        return if (correo.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            "Introduce un correo válido (ej. usuario@dominio.com)"
        } else {
            null
        }
    }

    fun validarTelefono(telefono: String): String? {
        return if (!telefono.all { it.isDigit() } || telefono.isBlank()) {
            "El teléfono solo debe contener números"
        } else {
            null
        }
    }

    LaunchedEffect(selectedImageUri) {
        selectedImageUri?.let { uri ->
            imagenUri = uri.toString()
        }
    }

    LaunchedEffect(id) {
        contactoVM.contactos.collect { contactos ->
            contactos.find { it.id == id }?.let { contacto ->
                nombre = contacto.nombre
                apellidosP = contacto.apellidosP
                apellidosM = contacto.apellidosM
                correo = contacto.correo
                telefono = contacto.telefono
                direccion = contacto.direccion
                imagenUri = contacto.imagenUri

                nombreError = null
                apellidoPError = null
                telefonoError = null
                correoError = null
            }
        }
    }

    fun eliminarImagen() {
        imagenUri = null
    }

    fun agregarContacto() {
        nombreError = validarNombre(nombre)
        apellidoPError = validarApellidoP(apellidosP)
        telefonoError = validarTelefono(telefono)
        correoError = validarCorreo(correo)

        if (nombreError == null && telefonoError == null && apellidoPError == null && correoError == null) {
            contactoVM.updateContacto(
                Contacto(
                    id = id,
                    nombre = nombre.trim(),
                    apellidosP = apellidosP.trim(),
                    apellidosM = apellidosM.trim(),
                    correo = correo.trim(),
                    telefono = telefono.trim(),
                    direccion = direccion.trim(),
                    imagenUri = imagenUri
                )
            )
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Editar Contacto") },
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                },
                actions = {
                    IconButton(onClick = {
                        agregarContacto()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Actualizar Contacto"
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
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                } ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactAvatar(
                imagenUri = imagenUri?.toString(),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { pickImage() }
            )

            Spacer(modifier = Modifier.size(16.dp))

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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Nombre"
                    )
                },
                isError = nombreError != null,
                errorMessage = nombreError)

            MainTextField(
                value = apellidosP,
                onValueChange = {
                    apellidosP = it
                    apellidoPError = validarApellidoP(it)
                },
                label = "Apellido Paterno",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = "ApellidoP"
                    )
                },
                isError = apellidoPError != null,
                errorMessage = apellidoPError
            )

            MainTextField(
                value = apellidosM,
                onValueChange = {
                    apellidosM = it
                },
                label = "Apellido Materno",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "ApellidoM"
                    )
                }
            )

            MainTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    correoError = validarCorreo(it)
                },
                label = "Correo",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Correo"
                    )
                },
                keyboardType = KeyboardType.Email,
                isError = correoError != null,
                errorMessage = correoError
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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Telefono"
                    )
                },
                keyboardType = KeyboardType.Phone
            )

            MainTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                },
                label = "Dirección",
                leadingIcon = {
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
