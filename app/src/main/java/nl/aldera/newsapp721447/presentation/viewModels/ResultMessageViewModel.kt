package nl.aldera.newsapp721447.presentation.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultMessageViewModel : ViewModel() {
    private val resultMessageMutableState = MutableStateFlow<Int>(-1)
    var resultMessageState: StateFlow<Int> = resultMessageMutableState

    fun updateState(newValue: Int) {
        resultMessageMutableState.value = newValue
    }
}