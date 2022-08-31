fun main() {
    fun part1(input: List<String>): Int {
        val stepCount = 10
        val sequence = ArrayDeque(input.first().chunked(1))
        val rules = input.drop(2).map { it.split(" -> ") }.associate { Pair(Pair(it.first().first(), it.first().last()), it.last()) }

        (1 .. stepCount).forEach { _ ->
            val insertions = mutableMapOf<Int, String>()
            sequence.windowed(2).forEachIndexed{i, (first, second) ->
                insertions[i*2+1] = rules.getValue(Pair(first.single(), second.single()))
            }
            insertions.forEach{(index, value) -> sequence.add(index, value)}
        }

        val occurrenceOfEachLetter = sequence.groupingBy { it }.eachCount()
        val mostCommon = occurrenceOfEachLetter.maxByOrNull { it.value }?.key!!
        val leastCommon = occurrenceOfEachLetter.minByOrNull { it.value }?.key!!

        return occurrenceOfEachLetter[mostCommon]!! - occurrenceOfEachLetter[leastCommon]!!
    }

    fun part2(input: List<String>): Long {
        val stepCount = 40
        val rules = input.drop(2).map { it.split(" -> ") }.associate { (key, value) -> Pair(key, value.single()) }

        val elements = rules.values.associateWith { 0L }.toMutableMap()
        input.first().forEach { elements[it] = elements[it]!! + 1 }

        var pairs = rules.keys.associateWith { 0L }.toMutableMap()
        input.first().windowed(2).forEach { pairs[it] = pairs[it]!! + 1 }

        repeat(stepCount) {
            val updatedPairs = pairs.toMutableMap()

            pairs.filterValues { it > 0 }.forEach { (pair, count) ->
                val replacement = rules[pair]!!
                val newPairOne = "${pair.first()}${replacement}"
                val newPairTwo = "${replacement}${pair.last()}"

                updatedPairs[pair] = updatedPairs[pair]!! - count
                updatedPairs[newPairOne] = updatedPairs[newPairOne]!! + count
                updatedPairs[newPairTwo] = updatedPairs[newPairTwo]!! + count

                elements[replacement] = elements[replacement]!! + count
            }

            pairs = updatedPairs
        }

        val mostCommon = elements.maxByOrNull { it.value }?.key!!
        val leastCommon = elements.minByOrNull { it.value }?.key!!

        return elements[mostCommon]!! - elements[leastCommon]!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
