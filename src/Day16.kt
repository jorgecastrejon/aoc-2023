fun main() {

    fun part1(input: List<String>): Int =
        calculateEnergy(Grid(input), start = Grid.Point(0, 0), from = Grid.Direction.Left)

    fun part2(input: List<String>): Int {
        val grid = Grid(input)
        val width = input.first().length
        val height = input.size

        val points = hStartingPoints(until = width, on = height - 1) + vStartingPoints(until = height, on = width - 1)

        return points.maxOf { (point, direction) -> calculateEnergy(grid, start = point, from = direction) }
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}

private fun hStartingPoints(until: Int, on: Int): List<Pair<Grid.Point, Grid.Direction>> =
    (0 until until).map { x ->
        listOf(Grid.Point(x, 0) to Grid.Direction.Up, Grid.Point(x, on) to Grid.Direction.Down)
    }.flatten()

private fun vStartingPoints(until: Int, on: Int): List<Pair<Grid.Point, Grid.Direction>> =
    (0 until until).map { y ->
        listOf(Grid.Point(0, y) to Grid.Direction.Left, Grid.Point(on, y) to Grid.Direction.Right)
    }.flatten()

private fun calculateEnergy(grid: Grid, start: Grid.Point, from: Grid.Direction): Int {
    val pending = ArrayDeque<Pair<Grid.Point, Grid.Direction>>().apply { add(start to from) }
    val visited: MutableSet<Pair<Grid.Point, Grid.Direction>> = mutableSetOf()

    while (pending.isNotEmpty()) {
        val (point, direction) = pending.removeLast()

        val node = grid.cells[point]

        if (node != null && !visited.contains(point to direction)) {
            visited.add(point to direction)
            pending.addAll(point.next(value = node, from = direction))
        }
    }

    return visited.map { (point, _) -> point }.toSet().size
}

private fun Grid.Point.next(value: String, from: Grid.Direction): List<Pair<Grid.Point, Grid.Direction>> =
    when {
        from == Grid.Direction.Up && value in listOf(".", "|") -> {
            listOf(this.move(Grid.Direction.Down) to Grid.Direction.Up)
        }

        from == Grid.Direction.Up && value == "/" -> {
            listOf(this.move(Grid.Direction.Left) to Grid.Direction.Right)
        }

        from == Grid.Direction.Up && value == "\\" -> {
            listOf(this.move(Grid.Direction.Right) to Grid.Direction.Left)
        }

        from == Grid.Direction.Up && value == "-" -> {
            listOf(
                this.move(Grid.Direction.Left) to Grid.Direction.Right,
                this.move(Grid.Direction.Right) to Grid.Direction.Left
            )
        }

        from == Grid.Direction.Right && value in listOf(".", "-") -> {
            listOf(this.move(Grid.Direction.Left) to Grid.Direction.Right)
        }

        from == Grid.Direction.Right && value == "/" -> {
            listOf(this.move(Grid.Direction.Down) to Grid.Direction.Up)
        }

        from == Grid.Direction.Right && value == "\\" -> {
            listOf(this.move(Grid.Direction.Up) to Grid.Direction.Down)
        }

        from == Grid.Direction.Right && value == "|" -> {
            listOf(
                this.move(Grid.Direction.Up) to Grid.Direction.Down,
                this.move(Grid.Direction.Down) to Grid.Direction.Up
            )
        }

        from == Grid.Direction.Down && value in listOf(".", "|") -> {
            listOf(this.move(Grid.Direction.Up) to Grid.Direction.Down)
        }

        from == Grid.Direction.Down && value == "/" -> {
            listOf(this.move(Grid.Direction.Right) to Grid.Direction.Left)
        }

        from == Grid.Direction.Down && value == "\\" -> {
            listOf(this.move(Grid.Direction.Left) to Grid.Direction.Right)
        }

        from == Grid.Direction.Down && value == "-" -> {
            listOf(
                this.move(Grid.Direction.Left) to Grid.Direction.Right,
                this.move(Grid.Direction.Right) to Grid.Direction.Left
            )
        }

        from == Grid.Direction.Left && value in listOf(".", "-") -> {
            listOf(this.move(Grid.Direction.Right) to Grid.Direction.Left)
        }

        from == Grid.Direction.Left && value == "/" -> {
            listOf(this.move(Grid.Direction.Up) to Grid.Direction.Down)
        }

        from == Grid.Direction.Left && value == "\\" -> {
            listOf(this.move(Grid.Direction.Down) to Grid.Direction.Up)
        }

        from == Grid.Direction.Left && value == "|" -> {
            listOf(
                this.move(Grid.Direction.Up) to Grid.Direction.Down,
                this.move(Grid.Direction.Down) to Grid.Direction.Up
            )
        }

        else -> {
            emptyList()
        }
    }
