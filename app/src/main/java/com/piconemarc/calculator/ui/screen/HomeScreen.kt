package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piconemarc.calculator.R
import com.piconemarc.calculator.reducer.HomeAction
import com.piconemarc.calculator.ui.common.BaseTableToggleButton
import com.piconemarc.calculator.ui.theme.LittleMarge
import com.piconemarc.calculator.ui.theme.RegularMarge
import com.piconemarc.calculator.ui.theme.ThinBorder
import com.piconemarc.calculator.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
){
    TableList(
        onTableListChange = { tableNumber, isChecked ->
            homeViewModel.dispatchAction(
                HomeAction.UpdateTableList(
                    tableNumber,
                    isChecked,
                    homeViewModel.homeState.checkedTableList
                )
            )
        }
    )
}


@Composable
fun TableList(
    onTableListChange: (tableNumber: Int, isChecked: Boolean) -> Unit,
) {
    Column(modifier = Modifier
        .padding(vertical = RegularMarge, horizontal = LittleMarge)
        .fillMaxWidth()
        .border(ThinBorder,MaterialTheme.colors.secondaryVariant, RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.tableSelectionTitle),
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier = Modifier
                .padding(vertical = RegularMarge)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            for (i in 1..10) {
                BaseTableToggleButton(
                    buttonText = i,
                    onChecked = onTableListChange
                )
            }
        }
    }
}
