fun main() {

    fun calculateExtrapolation2(line: String, operation: (acc: Int, List<Int>) -> Int): Int {
        var iteration = line.splitAsInt()
        val iterations = mutableListOf(iteration)

        while (iteration.any { it != 0 }) {
            iteration = iteration.zipWithNext { a, b -> b - a }
            iterations.add(iteration)
        }

        return iterations.reversed().fold(initial = 0, operation = operation)
    }


    fun part1(input: List<String>): Int = input.sumOf { line ->
        calculateExtrapolation2(line = line, operation = { acc, next -> acc + next.last() })
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        calculateExtrapolation2(line = line, operation = { acc, next -> next.first() - acc })
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()

}
