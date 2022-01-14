package com.piconemarc.calculator.utils

import android.os.CountDownTimer

enum class GameLevel(
    val value: String,
    val allocatedTime: Long,
    val questionTime : Int,
    val basePoint : Int
    ) {
    NOVICE("Novice", 120000, 12,1),
    NOT_SO_BAD("Not so bad", 90000, 9,2),
    PRO("Professional", 60000,7,3)
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