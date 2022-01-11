package com.piconemarc.calculator.utils.interfaces

import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



class DefaultStore<S : UiState>(
    initialState: S,
    private val reducer: Reducer<S>
) : Store<S> {
    private val subscribers = mutableSetOf<StoreSubscriber<S>>()
    private var state: S = initialState
        set(value) {
            field = value
            subscribers.forEach { it(value) }
        }

    override fun dispatch(action: UiAction) {
        state = reducer(state, action)
    }

    override fun add(subscriber: StoreSubscriber<S>):
            Boolean = subscribers.add(subscriber)

    override fun remove(subscriber: StoreSubscriber<S>):
            Boolean = subscribers.remove(subscriber)
}

open class BaseUiModel(
    open val id : Long = 0,
    open val name : String = ""
)

fun CoroutineScope.launchOnIOCatchingError(
    block : suspend CoroutineScope.() -> Unit,
    doOnSuccess : ()-> Unit = {},
    doOnError : ()-> Unit = {},
) : Job {
    return this.launch(Dispatchers.IO) {
        try {
            block()
            doOnSuccess()
        }catch (e : SQLiteException){
            Log.e(this::class.java.simpleName, "dispatchEvent: ", e)
            doOnError()
        }
    }
}