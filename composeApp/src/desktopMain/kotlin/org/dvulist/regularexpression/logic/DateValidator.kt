package org.dvulist.regularexpression.logic

object DateValidator {

    private val RUSSIAN_MONTHS = mapOf(
        "января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5, "июня" to 6,
        "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12
    )

    private val ENGLISH_FULL_MONTHS = mapOf(
        "January" to 1, "February" to 2, "March" to 3, "April" to 4, "May" to 5, "June" to 6,
        "July" to 7, "August" to 8, "September" to 9, "October" to 10, "November" to 11, "December" to 12
    )

    private val ENGLISH_SHORT_MONTHS = mapOf(
        "Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4, "May" to 5, "Jun" to 6,
        "Jul" to 7, "Aug" to 8, "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12
    )

    private fun isValidDateComponents(year: Int, month: Int, day: Int): Boolean {
        if (year < 0) return false

        if (month < 1 || month > 12) return false
        if (day < 1 || day > 31) return false

        val daysInMonth = when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
        return day <= daysInMonth
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    private fun String.toIntOrNullStrict(): Int? {
        return try {
            this.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun isValidDate(dateString: String): Boolean {
        val trimmedDate = dateString.trim()

        val dmySeparators = listOf(".", "/", "-")
        for (sep in dmySeparators) {
            val parts = trimmedDate.split(sep)
            if (parts.size == 3) {
                val day = parts[0].toIntOrNullStrict()
                val month = parts[1].toIntOrNullStrict()
                val year = parts[2].toIntOrNullStrict()
                if (day != null && month != null && year != null) {
                    if (isValidDateComponents(year, month, day)) return true
                }
            }
        }

        // 2. Форматы год.месяц.день, год/месяц/день, год-месяц-день
        val ymdSeparators = listOf(".", "/", "-")
        for (sep in ymdSeparators) {
            val parts = trimmedDate.split(sep)
            if (parts.size == 3) {
                val year = parts[0].toIntOrNullStrict()
                val month = parts[1].toIntOrNullStrict()
                val day = parts[2].toIntOrNullStrict()
                if (day != null && month != null && year != null) {
                    if (isValidDateComponents(year, month, day)) return true
                }
            }
        }

        for ((monthName, monthNum) in RUSSIAN_MONTHS) {
            if (trimmedDate.contains(" $monthName ")) {
                val parts = trimmedDate.split(" $monthName ")
                if (parts.size == 2) {
                    val day = parts[0].toIntOrNullStrict()
                    val year = parts[1].toIntOrNullStrict()
                    if (day != null && year != null) {
                        if (isValidDateComponents(year, monthNum, day)) return true
                    }
                }
            }
        }

        for ((monthName, monthNum) in ENGLISH_FULL_MONTHS) {
            if (trimmedDate.startsWith("$monthName ")) {
                val remaining = trimmedDate.substring(monthName.length).trim()
                val parts = remaining.split(",").map { it.trim() }
                if (parts.size == 2) {
                    val day = parts[0].toIntOrNullStrict()
                    val year = parts[1].toIntOrNullStrict()
                    if (day != null && year != null) {
                        if (isValidDateComponents(year, monthNum, day)) return true
                    }
                }
            }
        }

        for ((monthName, monthNum) in ENGLISH_SHORT_MONTHS) {
            if (trimmedDate.startsWith("$monthName ")) {
                val remaining = trimmedDate.substring(monthName.length).trim()
                val parts = remaining.split(",").map { it.trim() }
                if (parts.size == 2) {
                    val day = parts[0].toIntOrNullStrict()
                    val year = parts[1].toIntOrNullStrict()
                    if (day != null && year != null) {
                        if (isValidDateComponents(year, monthNum, day)) return true
                    }
                }
            }
        }

        for (monthMap in listOf(ENGLISH_FULL_MONTHS, ENGLISH_SHORT_MONTHS)) {
            val parts = trimmedDate.split(",").map { it.trim() }
            if (parts.size == 2) {
                val year = parts[0].toIntOrNullStrict()
                val monthDayPart = parts[1]
                for ((monthName, monthNum) in monthMap) {
                    if (monthDayPart.startsWith("$monthName ")) {
                        val dayString = monthDayPart.substring(monthName.length).trim()
                        val day = dayString.toIntOrNullStrict()
                        if (year != null && day != null) {
                            if (isValidDateComponents(year, monthNum, day)) return true
                        }
                    }
                }
            }
        }

        return false
    }
}