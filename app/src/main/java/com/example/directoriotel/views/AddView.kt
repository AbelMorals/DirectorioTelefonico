package com.example.directorio.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.directorio.components.MainIconButton
import com.example.directorio.components.MainTextField
import com.example.directorio.components.MainTitle
import com.example.directorio.viewModels.ContactoViewModel
import com.example.directoriotel.model.Contacto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController, contactoVM: ContactoViewModel){
    var nombre by remember { mutableStateOf("") }
    var apellidosP by remember { mutableStateOf("") }
    var apellidosM by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var apellidoPError by remember { mutableStateOf<String?>(null) }
    var apellidoMError by remember { mutableStateOf<String?>(null) }
    var correoError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }

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

    fun validarTelefono(telefono: String): String? {
        return if (!telefono.all { it.isDigit() } || telefono.isBlank()) {
            "El teléfono solo debe contener números"
        } else {
            null
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
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
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

            Button(
                onClick = {
                    nombreError = validarNombre(nombre)
                    apellidoPError = validarApellidoP(apellidosP)
                    apellidoMError = validarApellidoM(apellidosM)
                    correoError = validarCorreo(correo)
                    telefonoError = validarTelefono(telefono)

                    if (nombreError == null && correoError == null && telefonoError == null
                        && apellidoPError == null && apellidoMError == null) {
                        contactoVM.addContacto(
                            Contacto(
                                nombre = nombre.trim(),
                                apellidosP = apellidosP.trim(),
                                apellidosM = apellidosM.trim(),
                                correo = correo.trim(),
                                telefono = telefono.trim()
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Contacto")
            }
        }
    }
}