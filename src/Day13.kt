fun main() {

    fun part1(input: List<String>): Int =
        input.windowedBy(String::isEmpty).sumOf { group ->
            val vertical = 100 * group.verticalRefection(diff = 0)
            val horizontal = group.map(String::toList).transposed { it.joinToString("") }.verticalRefection(diff = 0)

            vertical + horizontal
        }

    fun part2(input: List<String>): Int =
        input.windowedBy(String::isEmpty).sumOf { group ->
            val vertical = 100 * group.verticalRefection(diff = 1)
            val horizontal = group.map(String::toList).transposed { it.joinToString("") }.verticalRefection(diff = 1)

            vertical + horizontal
        }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}

private fun List<String>.verticalRefection(diff: Int): Int =
    (1 until size).firstOrNull { numberOfLines ->
        val up = take(numberOfLines).reversed()
        val down = drop(numberOfLines)

        up.zip(down).sumOf { (string1, string2) ->
            string1.zip(string2).count { (char1, char2) -> char1 != char2 }
        } == diff
    } ?: 0
