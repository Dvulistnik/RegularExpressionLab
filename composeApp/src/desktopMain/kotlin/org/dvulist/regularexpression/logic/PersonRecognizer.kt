package org.dvulist.regularexpression.logic

import java.util.regex.Pattern

data class Person(val name: String)

object PersonRecognizer {
    private val PERSON_REGEX = Pattern.compile(
        "(?<person>" +
                "([А-ЯЁ][а-яё]+(?:-[А-ЯЁ][а-яё]+)?)" +
                "(?:\\s+[А-ЯЁ][а-яё]+(?:-[А-ЯЁ][а-яё]+)?){0,2}" +
                ")"
    )

    fun findPersons(text: String): List<Person> {
        val persons = mutableListOf<Person>()
        val matcher = PERSON_REGEX.matcher(text)

        while (matcher.find()) {
            val personName = matcher.group("person")
            if (!personName.isNullOrBlank() && !isLikelyNonPerson(personName)) {
                persons.add(Person(personName.trim()))
            }
        }
        return persons.distinctBy { it.name }
    }

    private fun isLikelyNonPerson(name: String): Boolean {
        val lowerCaseName = name.lowercase()
        val wordsInName = lowerCaseName.split("\\s+".toRegex())

        val commonNouns = setOf(
            "как", "это", "решение", "найденные", "только", "пример", "список", "также",
            "был", "от", "поручению", "сообщает", "освобожден", "уволил", "да", "нет", "снова", "твой", "код", "то", "есть", "выдавать"
        )

        val positionsAndRoles = setOf(
            "начальник", "министр", "президент", "глава", "генерал", "директор"
        )

        val organizations = setOf(
            "увд", "мвд", "интерфакс", "пресс-служба", "ведомства"
        )

        val geographical = setOf(
            "улица", "город", "область", "края", "район", "дом", "квартира", "рф", "томской", "москва", "санкт-петербург"
        )

        val temporalAndNumeric = setOf(
            "мая", "января", "февраля", "марта", "апреля", "июня", "июля", "августа",
            "сентября", "октября", "ноября", "декабря", "понедельник", "вторник", "среда",
            "четверг", "пятница", "суббота", "воскресенье"
        )

        for (word in wordsInName) {
            if (commonNouns.contains(word) ||
                positionsAndRoles.contains(word) ||
                organizations.contains(word) ||
                geographical.contains(word) ||
                temporalAndNumeric.contains(word)) {
                return true
            }
        }

        if (wordsInName.size == 1 && wordsInName[0].length < 2) {
            return true
        }

        if (name.matches("[А-ЯЁ]{2,}".toRegex())) {
            if (wordsInName.size == 1 && (organizations.contains(lowerCaseName) || lowerCaseName == "рф")) {
                return true
            }
        }

        if (name.trim().isEmpty()) {
            return true
        }

        return false
    }
}