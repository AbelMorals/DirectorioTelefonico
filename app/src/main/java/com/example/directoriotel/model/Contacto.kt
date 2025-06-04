package com.example.directoriotel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class Contacto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "apellidosP")
    val apellidosP: String,
    @ColumnInfo(name = "apellidosM")
    val apellidosM: String,
    @ColumnInfo(name = "correo")
    val correo: String,
    @ColumnInfo(name = "telefono")
    val telefono: String,
    @ColumnInfo(name = "direccion")
    val direccion: String,
    @ColumnInfo(name = "imagenUri")
    val imagenUri: String?,
    @ColumnInfo(name = "favorito", defaultValue = "0")
    val favorito: Boolean = false
)