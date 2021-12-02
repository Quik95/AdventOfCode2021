fun main() {
    fun part1(input: List<String>): Int {
        val parsed = input.map{it.split(" ")}.map{Pair(it.first(), it.last().toInt())}
        val horizontal = parsed.filter { it.first == "forward" }.sumOf { it.second }
        val up = parsed.filter { it.first == "up" }.sumOf { it.second }
        val down = parsed.filter { it.first == "down" }.sumOf { it.second }

        return horizontal * (down-up)
    }

    fun part2(input: List<String>): Int {
        val parsed = input.map{it.split(" ")}.map{Pair(it.first(), it.last().toInt())}

        var horizontal = 0
        var depth = 0
        var aim = 0
        for ((command, value) in parsed) {
            when(command) {
                "forward" -> {
                    horizontal += value
                    depth += aim * value
                }
                "up" -> aim -= value
                "down" -> aim += value
            }
        }

        return depth * horizontal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
