package com.example.directorio.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.directorio.model.Cronos
import com.example.directorio.repository.CronosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class CronosViewModel @Inject constructor(private val repository: CronosRepository) : ViewModel() {

    private val _cronosList = MutableStateFlow<List<Cronos>>(emptyList())
    val cronosList = _cronosList.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCronos().collect {item->
                if(item.isNullOrEmpty()){
                    _cronosList.value = emptyList()
                }else{
                    _cronosList.value = item
                }

            }
        }
    }

    fun addCrono(crono: Cronos) = viewModelScope.launch { repository.addCrono(crono) }
    fun updateCrono(crono: Cronos) = viewModelScope.launch { repository.updateCrono(crono) }
    fun deleteCrono(crono: Cronos) = viewModelScope.launch { repository.deleteCrono(crono) }
}