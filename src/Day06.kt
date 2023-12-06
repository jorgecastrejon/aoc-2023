fun main() {

    fun part1(input: List<String>): Int {
        val (time, distance) = input.map { line ->
            line.split(":").last().trim().split(" ").filter { it != "" }.mapIndexed { index, s ->
                index to s.trim().toInt()
            }.toMap()
        }

        return (0 until time.size).map { index ->
            val total = time.getValue(index)

            (0 until total).count { chargeTime ->
                distance.getValue(index) < (total - chargeTime) * chargeTime
            }
        }.reduce { acc, next -> acc * next }
    }


    fun part2(input: List<String>): Int {
        val (time, distance) = input.map { line ->
            line.split(":").last().trim().replace(" ", "").toLong()
        }

        return (0 until time).count { chargeTime ->
            distance < (time - chargeTime) * chargeTime
        }
    }


    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
