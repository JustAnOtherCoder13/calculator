package com.piconemarc.calculator.reducer.screenReducer

import com.piconemarc.calculator.reducer.HomeAction
import com.piconemarc.calculator.reducer.HomeState
import com.piconemarc.calculator.utils.interfaces.Reducer

val homeReducer: Reducer<HomeState> = { old, action ->
    action as HomeAction
    when (action) {
        is HomeAction.UpdateTableList -> {
            old.copy(
                checkedTableList = when (action.isChecked) {
                    true -> {
                        if (!action.homeState.checkedTableList.contains(action.tableNumber)) {
                            val newList = action.homeState.checkedTableList.toMutableList()
                            newList.add(action.tableNumber)
                            newList
                        } else action.homeState.checkedTableList
                    }
                    false -> {
                        if (action.homeState.checkedTableList.contains(action.tableNumber)) {
                            val newList = action.homeState.checkedTableList.toMutableList()
                            newList.remove(action.tableNumber)
                            newList
                        } else action.homeState.checkedTableList
                    }
                }
            )
        }
        is HomeAction.UpdateOperandList -> {
            old.copy(
                operandList = when (action.isChecked) {
                    true -> {
                        if (!action.homeState.operandList.contains(action.operand)) {
                            val newList = action.homeState.operandList.toMutableList()
                            newList.add(action.operand)
                            newList
                        } else action.homeState.operandList
                    }
                    false -> {
                        if (action.homeState.operandList.contains(action.operand)) {
                            val newList = action.homeState.operandList.toMutableList()
                            newList.remove(action.operand)
                            newList
                        } else action.homeState.operandList
                    }
                }
            )
        }
        is HomeAction.UpdateGameLevel -> {
            old.copy(
                gameLevel = action.gameLevel
            )
        }
    }
}