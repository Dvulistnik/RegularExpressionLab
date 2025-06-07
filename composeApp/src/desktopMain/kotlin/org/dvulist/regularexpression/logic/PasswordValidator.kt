package org.dvulist.regularexpression.logic

object PasswordValidator {
    private val passwordRegex = Regex("^(?!.*(.)\\1)(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d^$%@#&*!?]{8,}$")


    fun isValidPassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }
}