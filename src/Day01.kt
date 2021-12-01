fun main() {
    fun part1(input: List<String>): Int {
        return input.map{ it.toInt() }.windowed(size = 2).count{ it.last() > it.first() }
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().map{ it.toInt() }.windowed(size = 3).map{ it.sum() }.windowed(size = 2).count{ it.last() > it.first() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
