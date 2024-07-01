package com.raineru.panatilihin.tempo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestoViewModel @Inject constructor() : ViewModel() {

    private val _sharedValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val sharedValue: StateFlow<Int> = _sharedValue.asStateFlow()

    init {
        viewModelScope.launch {
            sharedValue
                .drop(1)
                .collectLatest {
                Log.d("SharedViewModel", "sharedValue: $it")
            }
        }
    }

    fun incrementSharedValue() {
        _sharedValue.value++
    }
}