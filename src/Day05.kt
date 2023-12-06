import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Long {
        val seeds = input
            .first()
            .removePrefix("seeds: ")
            .split(" ")
            .map(String::toLong)

        val mappingTables = input
            .drop(2)
            .windowedBy(String::isBlank)
            .map { group -> group.drop(1).map(::mapOperationFrom) }

        return seeds.minOf { seed ->
            mappingTables.fold(seed) { acc, lists ->
                lists.find { (_, range) -> acc in range }?.transform(acc) ?: acc
            }
        }
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input
            .first()
            .removePrefix("seeds: ")
            .split(" ")
            .map(String::toLong)
            .windowed(2, 2) {
                it.first() until (it.first() + it.last())
            }

        val mappingTables = input
            .drop(2)
            .windowedBy(String::isBlank)
            .map { group -> group.drop(1).map(::mapOperationFrom) }

        return seedRanges.minOf { seedRange ->
            mappingTables.fold(listOf(seedRange)) { ranges, mappingTable ->
                val splits: MutableList<LongRange> = mutableListOf()
                val transformed: MutableList<LongRange> = mutableListOf()
                splits.addAll(ranges)

                while (splits.isNotEmpty()) {
                    val range = splits.removeFirst()

                    val mapOperation = mappingTable.firstOrNull { (_, mapRange) ->
                        range.first < mapRange.last && range.last > mapRange.first
                    }

                    if (mapOperation != null) {
                        val lower = max(range.first, mapOperation.start)
                        val upper = min(range.last, mapOperation.end)

                        val newStart = mapOperation.transform(lower)
                        val newEnd = mapOperation.transform(upper)

                        transformed.add(newStart until newEnd)

                        if (range.first < lower) splits.add(range.first until lower)
                        if (range.last > mapOperation.end) splits.add(mapOperation.end until range.last)
                    } else {
                        transformed.add(range)
                    }
                }

                transformed
            }.minOf(LongRange::first)
        }
    }


    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private data class MappingOperation(val dest: Long, val mapRange: LongRange) {
    val start: Long get() = mapRange.first
    val end: Long get() = mapRange.last

    fun transform(number: Long): Long = number + dest - start
}

private fun mapOperationFrom(line: String): MappingOperation {
    val (dest, start, steps) = line.split(" ").map(String::toLong)

    return MappingOperation(dest = dest, mapRange = start until start + steps)
}
