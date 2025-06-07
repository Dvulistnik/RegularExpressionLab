package org.dvulist.regularexpression.logic

data class Token(val type: TokenType, val value: String)

enum class TokenType {
    VARIABLE,
    NUMBER,
    CONSTANT,
    FUNCTION,
    OPERATOR,
    PARENTHESIS,
    WHITESPACE,
    UNKNOWN
}

object ExpressionTokenizer {

    private val KEYWORDS = mapOf(
        // Функции
        "sin" to TokenType.FUNCTION, "cos" to TokenType.FUNCTION, "tg" to TokenType.FUNCTION,
        "ctg" to TokenType.FUNCTION, "ln" to TokenType.FUNCTION, "lg" to TokenType.FUNCTION,
        "log" to TokenType.FUNCTION, "exp" to TokenType.FUNCTION, "sqrt" to TokenType.FUNCTION,
        // Константы
        "pi" to TokenType.CONSTANT, "e" to TokenType.CONSTANT, "sqrt2" to TokenType.CONSTANT,
        "ln2" to TokenType.CONSTANT, "ln10" to TokenType.CONSTANT
    )

    private val OPERATORS = setOf('^', '*', '/', '+', '-')
    private val PARENTHESES = setOf('(', ')')

    fun tokenize(expression: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0

        while (i < expression.length) {
            val char = expression[i]

            if (char.isWhitespace()) {
                tokens.add(Token(TokenType.WHITESPACE, char.toString()))
                i++
                continue
            }

            if (OPERATORS.contains(char)) {
                tokens.add(Token(TokenType.OPERATOR, char.toString()))
                i++
                continue
            }

            if (PARENTHESES.contains(char)) {
                tokens.add(Token(TokenType.PARENTHESIS, char.toString()))
                i++
                continue
            }

            if (char.isDigit()) {
                var j = i
                var hasDecimal = false
                while (j < expression.length && (expression[j].isDigit() || (expression[j] == '.' && !hasDecimal))) {
                    if (expression[j] == '.') {
                        hasDecimal = true
                    }
                    j++
                }
                tokens.add(Token(TokenType.NUMBER, expression.substring(i, j)))
                i = j
                continue
            }

            if (char.isLetter() || char == '_') {
                var j = i
                while (j < expression.length && (expression[j].isLetterOrDigit() || expression[j] == '_')) {
                    j++
                }
                val value = expression.substring(i, j)
                val type = KEYWORDS[value] ?: TokenType.VARIABLE
                tokens.add(Token(type, value))
                i = j
                continue
            }

            tokens.add(Token(TokenType.UNKNOWN, char.toString()))
            i++
        }

        return tokens.filter { it.type != TokenType.WHITESPACE }
    }
}