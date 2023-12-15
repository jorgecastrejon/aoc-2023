fun main() {

    fun part1(input: List<String>): Int =
        input.first().split(",").sumOf { string -> string.hashAlgorithm() }

    fun part2(input: List<String>): Int {
        val map: MutableMap<Int, MutableMap<String, Int>> = mutableMapOf()

        input.first().split(",").forEach { string ->
            if (string.contains("=")) {
                val (label, distance) = string.split("=")
                val box = label.hashAlgorithm()

                map.getOrPut(box) { mutableMapOf() }[label] = distance.toInt()
            } else {
                val label = string.removeSuffix("-")
                val box = label.hashAlgorithm()

                if (map.contains(box)) {
                    map.getValue(box).remove(label)
                }
            }
        }

        return map.filter { (_, list) ->
            list.isNotEmpty()
        }.map { (box, list) ->
            list.asIterable().mapIndexed { index, (_, distance) -> (box + 1) * (index + 1) * distance }.sum()
        }.sum()
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}

private fun String.hashAlgorithm(): Int =
    fold(0) { acc: Int, c: Char -> ((acc + c.code) * 17) % 256 }