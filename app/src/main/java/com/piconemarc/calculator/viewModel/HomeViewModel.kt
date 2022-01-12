package com.piconemarc.calculator.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piconemarc.calculator.reducer.*
import com.piconemarc.calculator.utils.interfaces.DefaultStore
import com.piconemarc.calculator.utils.interfaces.StoreSubscriber
import com.piconemarc.calculator.utils.interfaces.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    store: DefaultStore<GlobalState>
) : BaseViewModel<HomeAction, HomeState>(
    store,
    homeState_
) {
    val homeState by uiState

    init {
        viewModelScope.launch { state.collectLatest { uiState.value = it } }
    }

    override fun dispatchAction(action: HomeAction) {
        updateState(GlobalAction.UpdateHomeState(action))
    }


}