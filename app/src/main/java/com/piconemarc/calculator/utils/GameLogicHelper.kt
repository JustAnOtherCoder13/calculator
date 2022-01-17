package com.piconemarc.calculator.utils

import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import kotlin.random.Random

fun getRandomResult(goodAnswer: String): Int {
    val randomModifierBound = if (goodAnswer.toInt() > 1) goodAnswer.toInt()
    else Random.nextInt(3, 10)

    val randomValueModifier = Random.nextInt(1, randomModifierBound)

    return if (goodAnswer.toInt() - randomValueModifier < 2) {
        goodAnswer.toInt() + randomValueModifier
    } else {
        goodAnswer.toInt() - randomValueModifier
    }
}

fun getUpdatedGoodAnswerCount(
    action: GameAction.UpdateScore,
    old: GameState
) = if (action.gameState.firstNumber.isOperationTrue(
        action.gameState.operand,
        action.gameState.secondNumber,
        action.result
    )
) action.gameState.goodAnswerCount + 1 else old.goodAnswerCount

fun getNewScore(
    action: GameAction.UpdateScore,
    old: GameState
) = if (action.gameState.firstNumber.isOperationTrue(
        action.gameState.operand,
        action.gameState.secondNumber,
        action.result
    )
) {
    action.gameState.score + (calculateBasePoint(action.gameState) * action.gameState.bonus)
} else old.score

fun rememberBestGoodAnswerChain(
    goodAnswerChainCount: Int,
    old: GameState
) = if (goodAnswerChainCount > old.bestGoodAnswerChain) goodAnswerChainCount
else old.bestGoodAnswerChain

fun getCalculatedBonus(goodAnswerChainCount: Int) = when (goodAnswerChainCount) {
    3, 4, 5, 6 -> GoodAnswerBonus.BASIC.value
    7, 8, 9, 10, 11, 12 -> GoodAnswerBonus.NOT_SO_BAD.value
    13, 14, 15, 16, 17, 18, 19, 20 -> GoodAnswerBonus.GOOD.value
    else -> {
        if (goodAnswerChainCount >= 21) GoodAnswerBonus.VERY_GOOD.value
        else GoodAnswerBonus.NONE.value
    }
}

fun getUpdatedGoodAnswerChain(action: GameAction.UpdateGoodAnswerChainCount) =
    if (action.gameState.firstNumber.isOperationTrue(
            action.gameState.operand,
            action.gameState.secondNumber,
            action.result
        )
    ) action.gameState.goodAnswerChain + 1 else 0

fun getGoodAnswerToString(
    operand: String,
    firstNumber: Int,
    secondNumber: Int
) = when (operand) {
    "+" -> (firstNumber + secondNumber).toString()
    "-" -> (firstNumber - secondNumber).toString()
    "*" -> (firstNumber * secondNumber).toString()
    else -> ""
}

fun getNoviceAnswerValueList(goodAnswer: String): List<Int> {
    val list = mutableListOf(
        goodAnswer.toInt(),
        getRandomResult(goodAnswer),
        getRandomResult(goodAnswer),
        getRandomResult(goodAnswer)
    )
    list.shuffle()
    return list
}


fun calculateBasePoint(gameState: GameState): Int = when (gameState.gameParameters.gameLevel) {
    GameLevel.NOVICE -> {
        GameLevel.NOVICE.basePoint * (if (gameState.answerTime > 0) (gameState.answerTime) + GameLevel.NOVICE.basePoint else GameLevel.NOVICE.basePoint)
    }
    GameLevel.NOT_SO_BAD -> {
        GameLevel.NOT_SO_BAD.basePoint * (if ( gameState.answerTime > 0) (gameState.answerTime) + GameLevel.NOT_SO_BAD.basePoint else GameLevel.NOT_SO_BAD.basePoint)
    }
    GameLevel.PRO -> {
        GameLevel.PRO.basePoint * (if (gameState.answerTime > 0) (gameState.answerTime) + GameLevel.PRO.basePoint else GameLevel.PRO.basePoint)
    }
}

fun Int.isOperationTrue(operand: String, y: Int, submittedResult: String): Boolean {
    val operationResult =
        if (submittedResult.trim().isNotEmpty()) submittedResult.trim().toInt() else 0

    return when (operand) {
        "+" -> this + y == operationResult
        "-" -> this - y == operationResult
        "*" -> this * y == operationResult
        else -> {
            false
        }
    }
}