fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { line -> ("${line.first(Char::isDigit)}${line.last(Char::isDigit)}").toInt() }


    fun part2(input: List<String>): Int =
        input.map(::replaceLetters)
            .sumOf { line -> ("${line.first(Char::isDigit)}${line.last(Char::isDigit)}").toInt() }


    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun replaceLetters(string: String): String {
    val minPair = table.entries.minBy { (letter, _) -> string.indexOf(letter).takeIf { it != -1 } ?: Int.MAX_VALUE }
    val firstDigit = string.firstOrNull(Char::isDigit)

    var line = string

    if (firstDigit != null) {
        val digitIndex = string.indexOf(firstDigit)
        val wordIndex = string.indexOf(minPair.key)

        if (digitIndex > wordIndex) {
            line = string.replaceFirst(minPair.key, minPair.value, true)
        }
    } else {
        line = string.replaceFirst(minPair.key, minPair.value, true)
    }

    table.entries.maxBy { (letter, _) -> line.lastIndexOf(letter) }
        .let { (letters, number) ->
            line = line.replace(letters, number, true)
        }

    return line
}

val table = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)
