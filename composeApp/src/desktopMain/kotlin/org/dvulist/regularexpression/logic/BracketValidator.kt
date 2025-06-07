package org.dvulist.regularexpression.logic

import java.util.regex.Pattern

object BracketValidator {

    private val PAIRS_REGEX: Pattern = Pattern.compile("\\(\\)|\\{\\}|\\[\\]")

    fun isValidBracketExpression(expression: String): Boolean {
        var currentExpression = expression.trim()

        if (currentExpression.isEmpty()) {
            return true
        }

        var previousExpressionLength = -1

        while (currentExpression.length != previousExpressionLength) {
            previousExpressionLength = currentExpression.length
            currentExpression = PAIRS_REGEX.matcher(currentExpression).replaceAll("")
        }

        return currentExpression.isEmpty()
    }
}