package com.example.directorio.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.directoriotel.SystemIntegration
import com.example.directoriotel.model.Contacto
import com.example.directoriotel.R

@Composable
fun MainTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = trailingIcon
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 30.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun MainIconButton(icon: ImageVector, onClick:() -> Unit){
    IconButton(onClick = onClick) {
        Icon(imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun FloatButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar"
        )
    }
}

@Composable
fun Directorio(
    contacto: Contacto,
    onClick: () -> Unit,
    onToggleFavorito: () -> Unit,
    context : Context = LocalContext.current
) {
    var showPhoneOptionsDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (contacto.favorito)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar del contacto
                ContactAvatar(
                    imagenUri = contacto.imagenUri,
                    nombre = contacto.nombre,
                    modifier = Modifier.size(64.dp),
                    onClick = onClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "${contacto.nombre} ${contacto.apellidosP} ${contacto.apellidosM}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onToggleFavorito) {
                        Icon(
                            imageVector = if (contacto.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (contacto.favorito) Color.Red else Color.Gray
                        )
                    }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { SystemIntegration.sendEmail(context, contacto.correo) }) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Enviar Correo",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Text(text = contacto.correo, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showPhoneOptionsDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Llamar",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = contacto.telefono, modifier = Modifier.weight(1f), fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { SystemIntegration.openMap(context, contacto.direccion) },
                    enabled = contacto.direccion.isNotBlank()
                ){
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Ver mapa",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Text(text = contacto.direccion, fontSize = 16.sp)
            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            // Diálogo para seleccionar acción de teléfono
            if (showPhoneOptionsDialog) {
                PhoneActionDialog(
                    onDismissRequest = { showPhoneOptionsDialog = false },
                    onCallClick = {
                        SystemIntegration.makeCall(context, contacto.telefono)
                        showPhoneOptionsDialog = false
                    },
                    onSmsClick = {
                        SystemIntegration.sendSms(context, contacto.telefono)
                        showPhoneOptionsDialog = false
                    },
                    phoneNumber = contacto.telefono
                )
            }
        }
    }
}

@Composable
fun PhoneActionDialog(
    onDismissRequest: () -> Unit,
    onCallClick: () -> Unit,
    onSmsClick: () -> Unit,
    phoneNumber: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Acción para $phoneNumber") },
        text = { Text(text = "¿Qué te gustaría hacer?") },
        confirmButton = {
            TextButton(onClick = onCallClick) {
                Text("Llamar")
            }
        },
        dismissButton = {
            TextButton(onClick = onSmsClick) {
                Text("Enviar Mensaje")
            }
        }
    )
}

@Composable
fun ContactAvatar(
    imagenUri: String?,
    nombre: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        if (imagenUri != null) {
            AsyncImage(
                model = imagenUri,
                contentDescription = "Avatar de contacto",
                modifier = Modifier.matchParentSize(), // Para que la imagen llene el Box
                contentScale = ContentScale.Crop
            )
        } else {
            val iniciales = if (nombre.isNotBlank()) {
                nombre.trim().split(' ').take(2).joinToString("") { it.firstOrNull()?.uppercase() ?: "" }
            } else {
                "?"
            }
            Text(
                text = iniciales,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}