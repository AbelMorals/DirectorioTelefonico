package com.example.directorio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainTitle(title: String) {
    Text(text = title, color = Color.Black, fontWeight = FontWeight.Bold)
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
    keyboardType: KeyboardType = KeyboardType.Text
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
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
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
        Icon(imageVector = icon, contentDescription = null,
            tint = Color.White)
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
    nombre: String,
    apellidoP: String,
    apellidoM: String,
    email: String,
    telefono: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        )
        {
            Text(
                text = "$nombre $apellidoP $apellidoM", // Concatena nombre y apellidos
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Correo Electrónico",
                    tint = MaterialTheme.colorScheme.primary, // O el color que prefieras
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = email, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Teléfono",
                    tint = MaterialTheme.colorScheme.primary, // O el color que prefieras
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = telefono, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}