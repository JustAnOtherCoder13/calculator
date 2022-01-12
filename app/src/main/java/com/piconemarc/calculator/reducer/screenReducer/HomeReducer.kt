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
                        if (!action.tableCheckedList.contains(action.tableNumber)) {
                            action.tableCheckedList.add(action.tableNumber)
                            action.tableCheckedList
                        } else action.tableCheckedList
                    }
                    false -> {
                        if (action.tableCheckedList.contains(action.tableNumber)) {
                            action.tableCheckedList.remove(action.tableNumber)
                            action.tableCheckedList
                        } else action.tableCheckedList
                    }
                }
            )
        }
        is HomeAction.UpdateOperandList -> {
            old.copy(
                operandList = when (action.isChecked) {
                    true -> {
                        if (!action.selectedOperandList.contains(action.operand)) {
                            action.selectedOperandList.add(action.operand)
                            action.selectedOperandList
                        } else action.selectedOperandList
                    }
                    false -> {
                        if (action.selectedOperandList.contains(action.operand)) {
                            action.selectedOperandList.remove(action.operand)
                            action.selectedOperandList
                        } else action.selectedOperandList
                    }
                }
            )
        }
        is HomeAction.UpdateGameLevel -> {
            old.copy(
                gameLevel = action.gameLevel
            )
        }
        is HomeAction.StartNewGame -> old.copy(gameParams = action.gameParameters)
    }
}