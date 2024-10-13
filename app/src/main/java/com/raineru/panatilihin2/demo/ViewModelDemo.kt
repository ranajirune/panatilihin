package com.raineru.panatilihin2.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

// ViewModel Cheat sheet
// https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-cheatsheet

data class DiceUiState(
    val firstDieValue: Int? = null,
    val secondDieValue: Int? = null,
    val numberOfRolls: Int = 0,
)

/*@Module
@InstallIn(SingletonComponent::class)
object DiceRollModule {

    @Provides
    @Singleton
    fun provideSingletonDiceUiState(): DiceUiState = DiceUiState()
}*/

@HiltViewModel
class DiceRollViewModel @Inject constructor() : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    val firstDieRolls: SnapshotStateList<Int> = mutableStateListOf()
    val secondDieRolls: SnapshotStateList<Int> = mutableStateListOf()

    // Handle business logic
    fun rollDice() {
        _uiState.update { currentState ->
            currentState.copy(
                firstDieValue = Random.nextInt(from = 1, until = 7),
                secondDieValue = Random.nextInt(from = 1, until = 7),
                numberOfRolls = currentState.numberOfRolls + 1,
            )
        }
        firstDieRolls.add(_uiState.value.firstDieValue!!)
        secondDieRolls.add(_uiState.value.secondDieValue!!)
    }
}

// Use the 'viewModel()' function from the lifecycle-viewmodel-compose artifact
@Composable
fun DiceRollScreen(
    viewModel: DiceRollViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // Update UI elements

    Column {
        Text(text = "First die: ${uiState.firstDieValue}")
        Text(text = "Second die: ${uiState.secondDieValue}")
        Text(text = "Number of rolls: ${uiState.numberOfRolls}")
        Button(onClick = { viewModel.rollDice() }) {
            Text("ROLL DICE")
        }
        Text(text = "First die rolls: ${viewModel.firstDieRolls.toList()}")
        Text(text = "Second die rolls: ${viewModel.secondDieRolls.toList()}")
    }
}