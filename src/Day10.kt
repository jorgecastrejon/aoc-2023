import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        val stack = ArrayDeque<Grid.Point>()
        val visited: MutableSet<Grid.Point> = mutableSetOf()
        val distance: MutableMap<Grid.Point, Int> = mutableMapOf()

        val grid = Grid(input)
        val start = grid.cells.toList().first { (_, string) -> string == "S" }.first
        stack.add(start)
        distance[start] = 0

        while (stack.isNotEmpty()) {
            val point = stack.removeFirst()

            if (!visited.contains(point)) {
                visited.add(point)
                point.directions()
                    .filter { (neighbor, direction) ->
                        grid.cells[point].couldMoveTo(direction) && direction.wouldReach(grid.cells[neighbor])
                    }
                    .forEach { (neighbor, _) ->
                        stack.add(neighbor)
                        distance[neighbor] =
                            min(distance.getOrDefault(neighbor, Int.MAX_VALUE), distance.getValue(point) + 1)
                    }
            }
        }

        return distance.maxOf { (_, value) -> value }
    }

    fun part2(input: List<String>): Int {
        val stack = ArrayDeque<Grid.Point>()
        val loop = ArrayDeque<Grid.Point>()
        val visited: MutableSet<Grid.Point> = mutableSetOf()

        val grid = Grid(input)
        val start = grid.cells.toList().first { (_, string) -> string == "S" }.first
        stack.add(start)

        while (stack.isNotEmpty()) {
            val point = stack.removeFirst()

            if (!visited.contains(point)) {
                loop.add(point)
                visited.add(point)

                val next = point.directions().filter { (neighbor, direction) ->
                    grid.cells[point].couldMoveTo(direction) && direction.wouldReach(grid.cells[neighbor])
                }.firstOrNull { (neighbor, _) -> !visited.contains(neighbor) }

                if (next != null) {
                    stack.add(next.first)
                } else {
                    if (point.directions().any { (neighbor, _) -> neighbor == start }) {
                        break
                    }
                    loop.remove(point)
                    val latest = loop.removeLast()
                    visited.remove(latest)
                    stack.add(latest)
                }
            }
        }
        var inside: Boolean
        val innerPoints = mutableSetOf<Grid.Point>()

        for (y in input.indices) {
            inside = false

            for (x in input[y].indices) {
                val point = Grid.Point(x, y)

                if (point in loop) {
                    if (grid.cells[point] in listOf("|", "J", "L", "S")) {
                        inside = !inside
                    }
                } else if (inside) {
                    innerPoints.add(point)
                }
            }
        }

        return innerPoints.size
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

private fun String?.couldMoveTo(direction: Grid.Direction): Boolean =
    when (direction) {
        Grid.Direction.Up -> this in listOf("|", "L", "J", "S")
        Grid.Direction.Right -> this in listOf("-", "L", "F", "S")
        Grid.Direction.Down -> this in listOf("|", "F", "7", "S")
        Grid.Direction.Left -> this in listOf("-", "7", "J", "S")
    }

private fun Grid.Direction.wouldReach(destination: String?): Boolean =
    when (this) {
        Grid.Direction.Up -> destination in listOf("|", "F", "7")
        Grid.Direction.Right -> destination in listOf("-", "J", "7")
        Grid.Direction.Down -> destination in listOf("|", "J", "L")
        Grid.Direction.Left -> destination in listOf("-", "F", "L")
    }
