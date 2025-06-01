package com.example.directorio.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.directorio.model.Cronos

@Database(entities = [Cronos::class], version = 1, exportSchema = false)
abstract class CronosDatabase:RoomDatabase() {
    abstract fun cronosDao(): ContactoDataBaseDao

}