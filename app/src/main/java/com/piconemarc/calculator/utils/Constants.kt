package com.piconemarc.calculator.utils

import android.os.CountDownTimer

enum class GameLevel(val value: String, val allocatedTime: Long) {
    NOVICE("Novice", 60000 * 3),
    NOT_SO_BAD("Not so bad", 60000 * 2),
    PRO("Professional", 60000)
}

val operandList: List<String> = listOf("+", "-", "*")

fun initCountDownTimer(
    allocatedTime: Long,
    onTick_ : (millisUntilFinished: Long) -> Unit,
    onFinish_ : () -> Unit
) {
    object : CountDownTimer(allocatedTime, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            onTick_(millisUntilFinished)
        }
        override fun onFinish() { onFinish_() }
    }.start()
}