package com.piconemarc.calculator.reducer.screenReducer

import android.util.Log
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
        else -> old
    }
}