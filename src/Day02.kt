fun main() {
    fun part1(input: List<String>): Int =
        input.map { rawGame -> parseGame(rawGame) }
            .sumOf { game ->
                val isImpossible =
                    game.sets.any { set ->
                        set.green > GREEN_GOAL || set.red > RED_GOAL || set.blue > BLUE_GOAL
                    }
                if (isImpossible) 0 else game.id
            }


    fun part2(input: List<String>): Int =
        input.map { rawGame -> parseGame(rawGame) }.sumOf { game ->
            val map = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            game.sets.forEach { set ->
                if (set.red > (map["red"] ?: 0)) map["red"] = set.red
                if (set.green > (map["green"] ?: 0)) map["green"] = set.green
                if (set.blue > (map["blue"] ?: 0)) map["blue"] = set.blue
            }
            (map["red"] ?: 0) * (map["green"] ?: 0) * (map["blue"] ?: 0)
        }


    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun parseGame(rawGame: String): Game {
    val (rawId, rawSets) = rawGame.split(": ")
    val id = rawId.removePrefix("Game ").toInt()
    val sets = rawSets.split("; ").map { rawSet -> parseSet(rawSet) }

    return Game(id = id, sets = sets)
}

private fun parseSet(rawSet: String): Set {
    val map = rawSet.split(", ").associate { cubes ->
        val (number, color) = cubes.split(" ")
        color to number.toInt()
    }

    return Set(red = map["red"] ?: 0, green = map["green"] ?: 0, blue = map["blue"] ?: 0)
}

private data class Game(val id: Int, val sets: List<Set>)
private data class Set(val red: Int, val green: Int, val blue: Int)

private const val RED_GOAL = 12
private const val GREEN_GOAL = 13
private const val BLUE_GOAL = 14