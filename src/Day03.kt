fun main() {
    fun part1(input: List<String>): Int {
        val gammaRate = (0 until input[0].count()).map { index -> if (input.count{it[index] == '1'} > input.count() / 2) '1' else '0'}.joinToString ("").toInt(2)
        val epsilonRate = (0 until input[0].count()).map { index -> if (input.count{it[index] == '1'} > input.count() / 2) '0' else '1'}.joinToString ("").toInt(2)
        return gammaRate * epsilonRate
    }

    fun part2Calc(input: List<String>, index: Int, ifMostCommon: Boolean): List<String> {
        if (index == input.first().length || input.count() == 1)
            return input

        val mostCommon = if (input.count{it[index] == '1'} >= (input.count()+1)/2)  '1' else '0'
        return part2Calc(input.filter{(it[index] == mostCommon) == ifMostCommon}, index + 1, ifMostCommon)
    }


    fun part2(input: List<String>): Int {
        val oxygenRating = part2Calc(input, 0, true).first().toInt(2)
        val cO2Rating = part2Calc(input, 0, false).first().toInt(2)
        return oxygenRating * cO2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
