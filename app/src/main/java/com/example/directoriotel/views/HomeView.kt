package com.example.directorio.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.directorio.components.Directorio
import com.example.directorio.components.FloatButton
import com.example.directorio.components.MainTitle
import com.example.directorio.components.MainTitle2
import com.example.directorio.viewModels.ContactoViewModel
import com.example.directoriotel.R
import com.example.directoriotel.model.Contacto
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, contactoVM: ContactoViewModel, isDarkTheme: MutableState<Boolean>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var searchText by remember { mutableStateOf("") }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var contactToDelete by remember { mutableStateOf<Contacto?>(null) }

    LaunchedEffect(searchText) {
        contactoVM.setBusqueda(searchText)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle2(title = "Directorio") },
                navigationIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_filter),
                            contentDescription = "Filtro",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("Todos", "Favoritos").forEach { filtro ->
                                DropdownMenuItem(
                                    text = { Text(filtro) },
                                    onClick = {
                                        selectedFilter = filtro
                                        contactoVM.setFiltro(filtro)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { showThemeDialog = true }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_settings_24),
                            contentDescription = "Configuración",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
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
        if (showThemeDialog) {
            AlertDialog(
                onDismissRequest = { showThemeDialog = false },
                title = { Text("Seleccionar tema") },
                text = {
                    Column {
                        ListItem(
                            headlineContent = { Text("Tema claro") },
                            leadingContent = {
                                RadioButton(
                                    selected = !isDarkTheme.value,
                                    onClick = { isDarkTheme.value = false }
                                )
                            }
                        )
                        ListItem(
                            headlineContent = { Text("Tema oscuro") },
                            leadingContent = {
                                RadioButton(
                                    selected = isDarkTheme.value,
                                    onClick = { isDarkTheme.value = true }
                                )
                            }
                        )
                    }
                },
                confirmButton = {

                    TextButton(onClick = { showThemeDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        if (showDeleteConfirmationDialog && contactToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteConfirmationDialog = false
                    contactToDelete = null
                },
                title = { Text("Confirmar Eliminación") },
                text = { Text("¿Estás seguro de que quieres eliminar a ${contactToDelete?.nombre ?: "este contacto"}?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            contactToDelete?.let { contactoVM.deleteContacto(it) }
                            showDeleteConfirmationDialog = false
                            contactToDelete = null
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteConfirmationDialog = false
                            contactToDelete = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        ContentHomeView(
            it,
            navController = navController,
            contactoVM = contactoVM,
            onInitialDelete = { contacto ->
                contactToDelete = contacto
                showDeleteConfirmationDialog = true
            }
        )
    }
}

@Composable
fun ContentHomeView(
    paddingValues: PaddingValues,
    navController: NavController,
    contactoVM: ContactoViewModel,
    onInitialDelete: (Contacto) -> Unit
) {
    val contactoList by contactoVM.contactos.collectAsState()
    val lazyListState = rememberLazyListState()
    var searchText by remember { mutableStateOf("") }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.contact))

    if (contactoList.isEmpty() && searchText.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡No tienes contactos!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Presiona el botón + para agregar tu primer contacto",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    Column(modifier = Modifier.padding(paddingValues)) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                contactoVM.setBusqueda(it)
                            },
            placeholder = { Text("Buscar por nombre o teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            }
        )
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(contactoList) { item ->
                val delete = SwipeAction(
                    icon = rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = { onInitialDelete(item) }
                )
                val delete2 = SwipeAction(
                    icon = rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Blue,
                    onSwipe = { onInitialDelete(item) }
                )

                SwipeableActionsBox(
                    startActions = listOf(delete2),
                    endActions = listOf(delete),
                    swipeThreshold = 105.dp
                ) {
                    Directorio(
                        contacto = item,
                        onClick = { navController.navigate("EditView/${item.id}") },
                        onToggleFavorito = {
                            contactoVM.toggleFavorito(item)
                        }
                    )
                }
            }
        }
    }
}
