package com.piconemarc.calculator.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.piconemarc.calculator.reducer.GlobalAction
import com.piconemarc.calculator.reducer.GlobalState
import com.piconemarc.calculator.reducer.appStoreSubscriber
import com.piconemarc.calculator.utils.interfaces.DefaultStore
import com.piconemarc.calculator.utils.interfaces.StoreSubscriber
import com.piconemarc.calculator.utils.interfaces.UiAction
import com.piconemarc.calculator.utils.interfaces.UiState
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<A : UiAction, S : UiState>(
    private val store: DefaultStore<GlobalState>,
    val state: MutableStateFlow<S>
) : ViewModel() {

    private val subscriber: StoreSubscriber<GlobalState> = appStoreSubscriber

    init {
        store.add(subscriber)
    }

    fun updateState(vararg action: GlobalAction) {
        action.forEach {
            store.dispatch(it)
        }
    }

    abstract fun dispatchAction(action: A)

    val uiState : MutableState<S> = mutableStateOf(state.value)
}