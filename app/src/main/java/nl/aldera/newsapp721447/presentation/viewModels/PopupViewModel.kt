package nl.aldera.newsapp721447.presentation.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.RegisterMessage

class PopupViewModel : ViewModel() {

    private val popupMutableState = MutableStateFlow<Boolean>(false)
    val popupState: StateFlow<Boolean> = popupMutableState

    fun openPopup() {
        popupMutableState.tryEmit(true)
    }

    fun closePopup() {
        popupMutableState.tryEmit(false)
    }

}