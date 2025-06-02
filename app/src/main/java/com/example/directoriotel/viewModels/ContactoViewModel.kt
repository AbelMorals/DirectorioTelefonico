package com.example.directorio.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.directorio.repository.ContactoRepository
import com.example.directoriotel.model.Contacto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class ContactoViewModel @Inject constructor(private val repository: ContactoRepository) : ViewModel() {
    private val _contactos = MutableStateFlow<List<Contacto>>(emptyList())
    val contactos = _contactos.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllContactos().collect {item->
                if(item.isNullOrEmpty()){
                    _contactos.value = emptyList()
                }else{
                    _contactos.value = item
                }

            }
        }
    }

    fun addContacto(contacto: Contacto) = viewModelScope.launch { repository.addContacto(contacto) }

    fun updateContacto(contacto: Contacto) = viewModelScope.launch { repository.updateContacto(contacto) }

    fun deleteContacto(contacto: Contacto) = viewModelScope.launch { repository.deleteContacto(contacto) }
}