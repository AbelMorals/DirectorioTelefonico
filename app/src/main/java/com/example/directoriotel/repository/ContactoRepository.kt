package com.example.directorio.repository

import com.example.directorio.room.ContactoDataBaseDao
import com.example.directoriotel.model.Contacto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactoRepository @Inject constructor(private val contactoDatabaseDao: ContactoDataBaseDao) {
    suspend fun addContacto(contacto: Contacto) = contactoDatabaseDao.insert(contacto)
    suspend fun updateContacto(contacto: Contacto) = contactoDatabaseDao.update(contacto)
    suspend fun deleteContacto(contacto: Contacto) = contactoDatabaseDao.delete(contacto)
    suspend fun toggleFavorito(id: Long, favorito: Boolean) = contactoDatabaseDao.updateFavorito(id, favorito)

    fun getFavoritos(): Flow<List<Contacto>> = contactoDatabaseDao.getFavoritos().flowOn(Dispatchers.IO).conflate()
    fun getAllContactos(): Flow<List<Contacto>> = contactoDatabaseDao.getContactos().flowOn(Dispatchers.IO).conflate()
}
