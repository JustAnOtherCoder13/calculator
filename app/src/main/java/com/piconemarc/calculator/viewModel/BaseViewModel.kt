package com.piconemarc.calculator.viewModel

import androidx.lifecycle.ViewModel
import com.piconemarc.calculator.reducer.GlobalState
import com.piconemarc.calculator.utils.interfaces.DefaultStore
import com.piconemarc.calculator.utils.interfaces.UiAction
import com.piconemarc.calculator.utils.interfaces.UiState
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<A : UiAction, S : UiState>(
    private val store: DefaultStore<GlobalState>,
    val state: MutableStateFlow<S>
)  : ViewModel() {

    //todo abstract dispatch action, implement update state subscriber and exposed state


}