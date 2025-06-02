package com.example.directorio.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.directorio.components.Directorio
import com.example.directorio.components.FloatButton
import com.example.directorio.components.MainTitle
import com.example.directorio.viewModels.ContactoViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, contactoVM: ContactoViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Directorio") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")
            }
        }
    ) {
        ContentHomeView(it, navController, contactoVM)
    }
}

@Composable
fun ContentHomeView(it: PaddingValues, navController: NavController, contactoVM: ContactoViewModel) {
    val contactoList by contactoVM.contactos.collectAsState()

    Column(modifier = Modifier.padding(it)) {
        LazyColumn {
            items(contactoList) { item ->
                val delete=SwipeAction(
                    icon=rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = {contactoVM.deleteContacto(item)}
                )
                val delete2=SwipeAction(
                    icon=rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Blue,
                    onSwipe = {contactoVM.deleteContacto(item)}
                )

                SwipeableActionsBox(
                    startActions = listOf(delete2),
                    endActions =listOf(delete) ,
                    swipeThreshold = 105.dp
                ) {
                    Directorio(item.nombre, item.apellidosP, item.apellidosM, item.correo, item.telefono) {
                        navController.navigate("EditView/${item.id}")
                    }
                }
            }
        }
    }
}