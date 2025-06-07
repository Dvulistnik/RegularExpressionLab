package org.dvulist.regularexpression.logic

object WebColorValidator {
    private val webColorRegex = Regex("^((#([0-9a-fA-F]{3}){1,2})|(rgb\\(\\s*((25[0-5]|2[0-4]\\d|1?\\d{1,2})|(100|[1-9]?\\d)%)\\s*,\\s*((25[0-5]|2[0-4]\\d|1?\\d{1,2})|(100|[1-9]?\\d)%)\\s*,\\s*((25[0-5]|2[0-4]\\d|1?\\d{1,2})|(100|[1-9]?\\d)%)\\s*\\))|(hsl\\(\\s*(360|3[0-5]\\d|[12]?\\d{1,2})\\s*,\\s*(100|[1-9]?\\d)%\\s*,\\s*(100|[1-9]?\\d)%\\s*\\)))$", RegexOption.IGNORE_CASE)

    fun isValidWebColor(webColor: String): Boolean {
        return webColorRegex.matches(webColor)
    }
}