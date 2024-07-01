package com.raineru.panatilihin.tempo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.panatilihin.data.tempo.DogAndTheirOwners
import com.raineru.panatilihin.data.tempo.OwnerAndADog
import com.raineru.panatilihin.data.tempo.OwnerAndDogs
import com.raineru.panatilihin.data.tempo.OwnerAndTheirDogs
import com.raineru.panatilihin.data.tempo.OwnerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerViewModel @Inject constructor(
    private val repository: OwnerRepository
): ViewModel() {

    private val _owners = MutableStateFlow<List<OwnerAndADog>>(emptyList())
    val owners = _owners.asStateFlow()

    private val _ownerAndDogs = MutableStateFlow<List<OwnerAndDogs>>(emptyList())
    val ownerAndDogs = _ownerAndDogs.asStateFlow()

    private val _ownerAndTheirDogs = MutableStateFlow<List<OwnerAndTheirDogs>>(emptyList())
    val ownerAndTheirDogs = _ownerAndTheirDogs.asStateFlow()

    private val _dogAndTheirOwners = MutableStateFlow<List<DogAndTheirOwners>>(emptyList())
    val dogAndTheirOwners = _dogAndTheirOwners.asStateFlow()

    init {
        /*viewModelScope.launch {
            repository.getOwners().collectLatest { list ->
                _owners.value = list
            }
        }*/
        /*viewModelScope.launch {
            repository.getOwnerAndDogs().collectLatest { list ->
                _ownerAndDogs.value = list
            }
        }*/
        viewModelScope.launch {
            repository.getOwnerAndTheirDogs().collectLatest { list ->
                _ownerAndTheirDogs.value = list
            }
        }
        viewModelScope.launch {
            repository.getDogAndTheirOwners().collectLatest { list ->
                _dogAndTheirOwners.value = list
            }
        }
    }
}