fun main() {

    fun minPath(dir: CharArray, paths: Map<String, NetworkNode>, start: String, endCondition: (String) -> Boolean): Int {
        val dirSize = dir.size
        var count = 0
        var next = start

        while (!endCondition(next)) {
            val node = paths.getValue(next)
            val inst = dir[count % dirSize]
            next = node.moveTo(inst)
            count++
        }

        return count
    }

    fun part1(input: List<String>): Int {
        val dir = input.first().toCharArray()
        val paths = input.drop(2).map(::asNetworkNode).associateBy(NetworkNode::current)

        return minPath(dir = dir, paths = paths, start = "AAA", endCondition = { it == "ZZZ" })
    }


    fun part2(input: List<String>): Long {
        val dir = input.first().toCharArray()
        val paths = input.drop(2).map(::asNetworkNode).associateBy(NetworkNode::current)

        return paths.values.filter { it.current.last() == 'A' }.map { node ->
            minPath(dir = dir, paths = paths, start = node.current, endCondition = { it.last() == 'Z' }).toLong()
        }.let(::lcm)
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()

}

private fun asNetworkNode(line: String): NetworkNode {
    val (current, paths) = line.split(" = ")
    val (left, right) = paths.substring(1, paths.lastIndex).split(", ")

    return NetworkNode(current, left, right)
}

private data class NetworkNode(val current: String, val left: String, val right: String) {
    fun moveTo(lr: Char): String = if (lr == 'L') left else right
}
