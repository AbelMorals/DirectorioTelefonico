package com.example.directorio.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.directoriotel.model.Contacto

@Database(entities = [Contacto::class], version = 1, exportSchema = false)
abstract class ContactoDatabase:RoomDatabase() {
    abstract fun contactoDao(): ContactoDataBaseDao
}