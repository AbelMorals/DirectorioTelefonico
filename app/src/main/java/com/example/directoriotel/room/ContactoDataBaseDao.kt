package com.example.directorio.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.directoriotel.model.Contacto
import kotlinx.coroutines.flow.Flow

//Interface -> Repositorios -> ViewModel -> View

@Dao //Data Access Object
interface ContactoDataBaseDao {
    //Crud
    @Query("Select * From contactos")
    fun getContactos(): Flow<List<Contacto>>

    @Query("Select * From contactos Where id = :id")
    fun getContactosById(id: Long): Flow<Contacto>

    @Query("UPDATE contactos SET favorito = :favorito WHERE id = :id")
    suspend fun updateFavorito(id: Long, favorito: Boolean)

    @Query("Select * From contactos Where favorito = 1")
    fun getFavoritos(): Flow<List<Contacto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacto: Contacto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(contacto: Contacto)

    @Delete
    suspend fun delete(contacto: Contacto)

}