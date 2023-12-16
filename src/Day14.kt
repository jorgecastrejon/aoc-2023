fun main() {

    fun part1(input: List<String>): Int =
        input.map(String::toCharArray).toTypedArray().tiltNorth().weight()

    fun part2(input: List<String>): Int {
        val grid = input.map(String::toCharArray).toTypedArray()
        val list = mutableListOf<String>()
        val loops = 1_000_000_000

        repeat(loops) { lap ->
            val key = grid.tiltNorth().tiltWest().tiltDown().tiltEast().encode()

            val startAt = list.indexOf(key)

            if (startAt != -1) {
                val remain = (loops - lap - 1) % (lap - startAt)

                return list[startAt + remain].decode().weight()
            }

            list.add(key)
        }

        return -1
    }


    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}

private fun Array<CharArray>.encode(): String =
    joinToString { charArray -> buildString { append(charArray) } }

private fun String.decode(): Array<CharArray> =
    split(", ").map(String::toCharArray).toTypedArray()

private fun Array<CharArray>.tiltNorth(): Array<CharArray> {
    var wall: Int

    for (x in first().indices) {
        wall = 0

        for (y in indices) {
            if (this[y][x] == 'O') {
                if (wall != y) {
                    this[wall][x] = 'O'
                    this[y][x] = '.'
                }
                wall++
            }

            if (this[y][x] == '#') {
                wall = y + 1
            }
        }
    }

    return this
}

private fun Array<CharArray>.tiltWest(): Array<CharArray> {
    var wall: Int

    for (y in indices) {
        wall = 0

        for (x in first().indices) {
            if (this[y][x] == 'O') {
                if (wall != x) {
                    this[y][wall] = 'O'
                    this[y][x] = '.'
                }
                wall++
            }

            if (this[y][x] == '#') {
                wall = x + 1
            }
        }
    }

    return this
}

private fun Array<CharArray>.tiltDown(): Array<CharArray> {
    var wall: Int

    for (x in first().lastIndex downTo 0) {
        wall = first().lastIndex

        for (y in lastIndex downTo 0) {
            if (this[y][x] == 'O') {
                if (wall != y) {
                    this[wall][x] = 'O'
                    this[y][x] = '.'
                }
                wall--
            }

            if (this[y][x] == '#') {
                wall = y - 1
            }
        }
    }

    return this
}

private fun Array<CharArray>.tiltEast(): Array<CharArray> {
    var wall: Int

    for (y in lastIndex downTo 0) {
        wall = lastIndex

        for (x in first().lastIndex downTo 0) {
            if (this[y][x] == 'O') {
                if (wall != x) {
                    this[y][wall] = 'O'
                    this[y][x] = '.'
                }
                wall--
            }

            if (this[y][x] == '#') {
                wall = x - 1
            }
        }
    }

    return this
}

private fun Array<CharArray>.weight(): Int =
    (0 until first().size).sumOf { x ->
        indices.sumOf { y -> if (this[y][x] == 'O') size - y else 0 }
    }