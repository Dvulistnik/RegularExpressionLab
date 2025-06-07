package org.dvulist.regularexpression.logic

object SentenceTokenizer {

    private val listItemRegex = Regex("""^\s*\d+[.)]\s+.*""")

    fun tokenize(text: String): List<String> {
        val result = mutableListOf<String>()
        val buffer = StringBuilder()
        var collectingList = false
        var previousWasListIntro = false

        fun flushBuffer() {
            if (buffer.isNotBlank()) {
                result.add(buffer.toString().trim())
                buffer.clear()
            }
        }

        val lines = text.lines()

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()

            if (line.isEmpty()) {
                flushBuffer()
                collectingList = false
                previousWasListIntro = false
                i++
                continue
            }

            val isListItem = listItemRegex.matches(line)

            if (isListItem) {
                if (!collectingList && !previousWasListIntro) {
                    flushBuffer()
                }
                collectingList = true
                buffer.appendLine(line)
                previousWasListIntro = false
            } else {
                val nextLine = lines.getOrNull(i + 1)?.trim()
                val nextIsListItem = nextLine != null && listItemRegex.matches(nextLine)

                if (nextIsListItem) {
                    flushBuffer()
                    buffer.appendLine(line)
                    previousWasListIntro = true
                } else {
                    if (collectingList || previousWasListIntro) {
                        flushBuffer()
                        collectingList = false
                        previousWasListIntro = false
                    }
                    buffer.appendLine(line)
                }
            }

            i++
        }

        flushBuffer()
        return result
    }
}
