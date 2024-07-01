package com.raineru.panatilihin.tempo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SnapshotFlowTestViewModel {

    var name by mutableStateOf("")
        private set

    val nameSnapshotFlow = snapshotFlow { name }

    private val _aFlow = MutableStateFlow(0)
    val flow = _aFlow.asStateFlow()

    fun setFlowValue(value: Int) {
        _aFlow.value = value
    }
}