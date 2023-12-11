import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Long = spaceBetweenGalaxies(input, multiplier = 2L)

    fun part2(input: List<String>): Long = spaceBetweenGalaxies(input, multiplier = 1_000_000L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}

private fun spaceBetweenGalaxies(input: List<String>, multiplier: Long): Long {
    val filledRows = mutableSetOf<Int>()
    val filledColumns = mutableSetOf<Int>()
    val galaxies = mutableListOf<Grid.Point>()

    input.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c == '#') {
                galaxies.add(Grid.Point(x, y))
                filledRows.add(y)
                filledColumns.add(x)
            }
        }
    }

    val emptyRows = input.indices.filter { it !in filledRows }
    val emptyCols = input.first().indices.filter { it !in filledColumns }

    var totalDistance = 0L

    for (i in galaxies.indices) {
        for (j in (i + 1) until galaxies.size) {
            val origin = galaxies[i]
            val destination = galaxies[j]
            val distance = abs(destination.x - origin.x) + abs(destination.y - origin.y)
            val colsRange = if (destination.x > origin.x) origin.x..destination.x else destination.x..origin.x
            val rowsRange = if (destination.y > origin.y) origin.y..destination.y else destination.y..origin.y
            val extraCols = emptyCols.count { it in colsRange }
            val extraRows = emptyRows.count { it in rowsRange }

            totalDistance += (distance + extraCols * (multiplier - 1) + extraRows * (multiplier - 1))
        }
    }

    return totalDistance
}
