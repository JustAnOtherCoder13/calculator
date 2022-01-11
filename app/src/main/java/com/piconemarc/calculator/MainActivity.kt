package com.piconemarc.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.piconemarc.calculator.reducer.HomeAction
import com.piconemarc.calculator.ui.screen.TableList
import com.piconemarc.calculator.ui.theme.CalculatorTheme
import com.piconemarc.calculator.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel by viewModels<HomeViewModel>()

        setContent {
            CalculatorTheme {
                TableList(
                    onTableListChange = { tableNumber, isChecked ->
                        homeViewModel.dispatchAction(
                            HomeAction.UpdateTableList(tableNumber, isChecked, homeViewModel.homeState.checkedTableList)
                        )
                    }
                )
            }
        }
    }
}