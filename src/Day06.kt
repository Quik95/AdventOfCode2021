fun main() {
    fun part1(input: List<String>): Int {
        val DAY_COUNT = 80

        val fishes = input.first().split(",")
        val state = mutableMapOf<String, Int>()
        (0..8).forEach { day -> state[day.toString()] = fishes.count{it == day.toString()} }

        (0 until DAY_COUNT).forEach { _ ->
            val fishesBorn = state["0"]!!
            state["0"] = state["1"]!!
            state["1"] = state["2"]!!
            state["2"] = state["3"]!!
            state["3"] = state["4"]!!
            state["4"] = state["5"]!!
            state["5"] = state["6"]!!
            state["6"] = state["7"]!! + fishesBorn
            state["7"] = state["8"]!!
            state["8"] = fishesBorn
        }

        val fishTotal = state.values.sum()
        return fishTotal
    }

    fun part2(input: List<String>): Long {
        val DAY_COUNT = 256

        val fishes = input.first().split(",")
        val state = mutableMapOf<String, Long>()
        (0..8).forEach { day -> state[day.toString()] = fishes.count{it == day.toString()}.toLong() }

        (0 until DAY_COUNT).forEach { _ ->
            val fishesBorn = state["0"]!!
            state["0"] = state["1"]!!
            state["1"] = state["2"]!!
            state["2"] = state["3"]!!
            state["3"] = state["4"]!!
            state["4"] = state["5"]!!
            state["5"] = state["6"]!!
            state["6"] = state["7"]!! + fishesBorn
            state["7"] = state["8"]!!
            state["8"] = fishesBorn
        }

        val fishTotal = state.values.sum()
        return fishTotal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
