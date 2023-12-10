class Grid(val input: List<String>) {

    val cells: Map<Point, String>

    init {
        cells = input.foldIndexed(mutableMapOf()) { y, map, line ->
            line.forEachIndexed { x, char -> map[Point(x, y)] = char.toString() }
            map
        }
    }

    data class Point(val x: Int, val y: Int) {

        fun directions(): List<Pair<Point, Direction>> = listOf(
            Point(x = x, y = y - 1) to Direction.Up,
            Point(x = x + 1, y = y) to Direction.Right,
            Point(x = x, y = y + 1) to Direction.Down,
            Point(x = x - 1, y = y) to Direction.Left,
        )
    }

    enum class Direction {
        Up, Right, Down, Left
    }
}
