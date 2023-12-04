import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        val games = input.map { card ->
            val (winners, mine) = card.split(": ").last().split(" | ")
                .map { row -> row.windowed(2, 3).map { it.trim().toInt() }.toSet() }
            winners.intersect(mine)
        }.sumOf { set -> if (set.isNotEmpty()) 2.0.pow(set.size - 1) else 0.0 }

        return games.toInt()
    }


    fun part2(input: List<String>): Int {
        val map = mutableMapOf(0 to 1)
        var i = 0
        while (i < input.size) {
            val game = input[i]
            val (winners, mine) = game.split(": ").last().split(" | ")
                .map { row -> row.windowed(2, 3).map { it.trim().toInt() }.toSet() }
            val numbers = winners.intersect(mine)
            val amount = numbers.size
            val rep = map.getOrPut(i) { 1 }

            for (j in 0 until amount) {
                val current = map[i + j + 1] ?: 1
                map[i + j + 1] = current + 1 * rep
            }
            i++
        }

        return map.values.sum()
    }


    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}