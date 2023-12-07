fun main() {

    fun part1(input: List<String>): Int =
        input.map(::getHandOfCards)
            .sorted()
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()

    fun part2(input: List<String>): Int =
        input.map {line -> getHandOfCards(line, wildcards = true) }
            .sorted()
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private fun getHandOfCards(line: String, wildcards: Boolean = false): HandOfCard {
    val (raw, bid) = line.split(" ")
    val cardMap: MutableMap<Int, Int> = mutableMapOf(5 to 0, 4 to 0, 3 to 0, 2 to 0, 1 to 0)

    val wildcardsCount = raw.count { it == 'J' }
    val score = raw.map { c -> getCardValue(char = c, wildcards = wildcards) }.joinToString("").toInt()

    val group = raw.filterNot { it == 'J' && wildcards}.groupBy { it }

    for ((_, list) in group) {
        cardMap[list.size] = cardMap.getOrDefault(list.size, 0) + 1
    }
    if (wildcards &&  wildcardsCount in 1 until 5) {
        val index = cardMap.keys.first { key -> cardMap[key] != 0 }
        cardMap[index + wildcardsCount] = cardMap.getValue(index + wildcardsCount) + 1
        cardMap[index] = cardMap.getValue(index) - 1
    } else if (wildcards && wildcardsCount == 5) {
        cardMap[5] = 1
    }

    val rank = cardMap.values.joinToString("").toInt()


    return HandOfCard(rank = rank, score = score, bid = bid.toInt(), raw = raw)
}

private fun getCardValue(char: Char, wildcards: Boolean = false): String =
    when (char) {
        'A' -> "14"
        'K' -> "13"
        'Q' -> "12"
        'J' -> if (wildcards) "01" else "11"
        'T' -> "10"
        else -> "0$char"
    }

private data class HandOfCard(val rank: Int, val score: Int, val bid: Int, val raw: String) : Comparable<HandOfCard> {
    override fun compareTo(other: HandOfCard): Int {
        val diffRank = rank - other.rank

        return if (diffRank != 0) diffRank else score - other.score
    }
}
