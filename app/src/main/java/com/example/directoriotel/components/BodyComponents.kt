package com.example.directorio.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.directoriotel.R
import com.example.directoriotel.SystemIntegration
import com.example.directoriotel.model.Contacto

@Composable
fun MainTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold)
}


val TextOutLinedD = Color(0xFF4051B5)
val TextD = Color(0xFF4051B5)

val TextOutLinedL = Color(0xFF283990)
val TextL = Color(0xFF4051B5)

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
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val textColor: Color
    val focusedBorderColor: Color
    val unfocusedBorderColor: Color

    if(isSystemInDarkTheme()) {
        textColor = TextD
        focusedBorderColor = TextOutLinedD
        unfocusedBorderColor = TextOutLinedD
    }else{
        textColor = TextL
        focusedBorderColor = TextOutLinedL
        unfocusedBorderColor = TextOutLinedL
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            leadingIcon = leadingIcon,
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = textColor
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = focusedBorderColor,
                focusedLabelColor = focusedBorderColor,
                focusedTextColor = focusedBorderColor,

                unfocusedBorderColor = unfocusedBorderColor,
                unfocusedLabelColor = unfocusedBorderColor.copy(alpha = 0.7f),
                unfocusedTextColor = unfocusedBorderColor,

                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorCursorColor = MaterialTheme.colorScheme.error,
                errorTextColor = MaterialTheme.colorScheme.error,
            )
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun FloatButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (contacto.favorito)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.primaryContainer,
            contentColor = if (contacto.favorito)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.size(48.dp),
                    shadowElevation = 2.dp
                ) {
                    ContactAvatar(
                        imagenUri = contacto.imagenUri,
                        modifier = Modifier.size(48.dp),
                        onClick = onClick
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "${contacto.nombre} ${contacto.apellidosP} ${contacto.apellidosM}".trim(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = onToggleFavorito) {
                    Icon(
                        imageVector = if (contacto.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (contacto.favorito) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.error
                    )
                }
            }

            if (!contacto.correo.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        SystemIntegration.sendEmail(
                            context,
                            contacto.correo
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Enviar Correo",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Text(text = contacto.correo, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showPhoneOptionsDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Opciones de telefono",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = contacto.telefono, modifier = Modifier.weight(1f), fontSize = 16.sp)
            }

            if (!contacto.direccion.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { SystemIntegration.openMap(context, contacto.direccion) },
                        enabled = contacto.direccion.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Ver mapa",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Text(text = contacto.direccion, fontSize = 16.sp)
                }
            }

            Divider(
                color = MaterialTheme.colorScheme.primaryContainer,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
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
        title = { Text(text = "Acción para $phoneNumber", style = MaterialTheme.typography.headlineSmall) },
        text = { Text(text = "¿Qué te gustaría hacer?", style = MaterialTheme.typography.bodyMedium) },
        confirmButton = {
            TextButton(onClick = onCallClick) {
                Text("Llamar")
            }
        },
        dismissButton = {
            TextButton(onClick = onSmsClick) {
                Text("Enviar Mensaje")
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ContactAvatar(
    imagenUri: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        if (imagenUri != null) {
            SubcomposeAsyncImage(
                model = imagenUri,
                contentDescription = "Avatar de contacto",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person),
                        contentDescription = "Cargando avatar",
                        modifier = Modifier.size(45.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                },

                error = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person),
                        contentDescription = "Error cargando avatar",
                        modifier = Modifier.size(45.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.baseline_person),
                contentDescription = "Avatar por defecto",
                modifier = Modifier.size(45.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
            )
        }
    }
}