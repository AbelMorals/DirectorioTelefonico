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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ContactoViewModel @Inject constructor(private val repository: ContactoRepository) : ViewModel() {
    private val _contactos = MutableStateFlow<List<Contacto>>(emptyList())
    private val _filtro = MutableStateFlow("Todos")
    private val _busqueda = MutableStateFlow("")

    val contactos = combine(_filtro, _busqueda, _contactos) { filtro, query, lista ->
        val filtrados = when (filtro) {
            "Favoritos" -> lista.filter { it.favorito }
            else -> lista
        }

        filtrados
            .filter {
                val texto = query.lowercase()
                it.nombre.lowercase().contains(texto) ||
                        it.apellidosP.lowercase().contains(texto) ||
                        it.apellidosM.lowercase().contains(texto) ||
                        it.telefono.contains(texto)
            }
            .sortedWith(
                compareByDescending<Contacto> { it.favorito }
                    .thenBy { it.nombre }
                    .thenBy { it.apellidosP }
            )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllContactos().collect { items ->
                _contactos.value = items.sortedWith(
                    compareByDescending<Contacto> { it.favorito }
                        .thenBy { it.nombre }
                        .thenBy { it.apellidosP }
                )
            }
        }
    }

    fun setFiltro(filtro: String) {
        _filtro.value = filtro
    }

    fun setBusqueda(query: String) {
        _busqueda.value = query
    }

    fun addContacto(contacto: Contacto) = viewModelScope.launch { repository.addContacto(contacto) }

    fun updateContacto(contacto: Contacto) = viewModelScope.launch { repository.updateContacto(contacto) }

    fun deleteContacto(contacto: Contacto) = viewModelScope.launch { repository.deleteContacto(contacto) }

    fun toggleFavorito(contacto: Contacto) = viewModelScope.launch { repository.toggleFavorito(contacto.id, !contacto.favorito) }
}