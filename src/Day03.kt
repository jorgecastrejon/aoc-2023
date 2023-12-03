fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0

        for (y in input.indices) {
            val line = input[y]

            for (x in line.indices) {
                val char = line[x]

                if (char.isDigit()) {
                    sum += getMatch(input, x, y)?.number ?: 0
                }
            }
        }

        return sum
    }


    fun part2(input: List<String>): Int {
        val table = mutableMapOf<String, MutableList<Int>>()

        for (y in input.indices) {
            val line = input[y]

            for (x in line.indices) {
                val char = line[x]

                if (char.isDigit()) {
                    getMatch(input, x, y)?.let { match ->
                        table.getOrPut(match.link) { mutableListOf() }.add(match.number)
                    }
                }
            }
        }


        return table.filter { (_, list) -> list.size > 1 }
            .map { (_, list) -> list.reduce { acc, i -> acc * i } }
            .sum()
    }


    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

private fun getMatch(input: List<String>, x: Int, y: Int): Match? {
    if (input.getOrNull(y)?.getOrNull(x - 1) != null && input[y][x - 1].isDigit()) return null

    var number = ""
    var index = x
    val line = input[y]

    while (line.getOrNull(index) != null && line[index].isDigit()) {
        number += line[index++]
    }

    for (i in (y - 1..y + 1)) {
        for (j in (x - 1..index)) {
            val char = input.getOrNull(i)?.getOrNull(j)

            if (char != null && char != '.' && !char.isDigit()) {
                return Match(number = number.toInt(), link = "$i-$j")
            }
        }
    }

    return null
}

private data class Match(val number: Int, val link: String)