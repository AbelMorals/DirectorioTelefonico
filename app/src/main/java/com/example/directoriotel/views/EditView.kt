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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.directorio.components.MainIconButton
import com.example.directorio.components.MainTextField
import com.example.directorio.components.MainTitle
import com.example.directorio.viewModels.ContactoViewModel
import com.example.directoriotel.model.Contacto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(navController: NavController, cronosVM: ContactoViewModel, id:Long){
    var nombre by remember { mutableStateOf("") }
    var apellidosP by remember { mutableStateOf("") }
    var apellidosM by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        cronosVM.contactos.collect { contactos ->
            contactos.find { it.id == id }?.let { contacto ->
                nombre = contacto.nombre
                apellidosP = contacto.apellidosP
                apellidosM = contacto.apellidosM
                correo = contacto.correo
                telefono = contacto.telefono
            }
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
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            MainTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
            MainTextField(value = apellidosP, onValueChange = { apellidosP = it }, label = "Apellidos Paterno")
            MainTextField(value = apellidosM, onValueChange = { apellidosM = it }, label = "Apellidos Materno")
            MainTextField(value = correo, onValueChange = { correo = it }, label = "Correo")
            MainTextField(value = telefono, onValueChange = { telefono = it }, label = "Teléfono")

            Button(
                onClick = {
                    cronosVM.updateContacto(
                        Contacto(
                            id = id,
                            nombre = nombre,
                            apellidosP = apellidosP,
                            apellidosM = apellidosM,
                            correo = correo,
                            telefono = telefono
                        )
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Contacto")
            }
        }
    }
}
