import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val crabsPositions = input.first().split(",").map{it.toInt()}
        val sorted = crabsPositions.sorted()
        val numberOfCrabs = sorted.count()
        val median = if (numberOfCrabs % 2 == 0) (sorted[numberOfCrabs / 2 - 1] + sorted[numberOfCrabs / 2]) / 2 else sorted[numberOfCrabs / 2]

        val fuelExpended = crabsPositions.fold(0){acc, value -> acc + abs(median - value)}
        return fuelExpended
    }

    fun part2(input: List<String>): Int {
        val crabsPositions = input.first().split(",").map{it.toInt()}
        val numberOfCrabsAtEachPosition = crabsPositions.toSet().associateWith { position -> crabsPositions.count{it == position}}

        fun calculateFuelUsed(distance: Int) = (0..distance).sum()

        val simulation = (0..(numberOfCrabsAtEachPosition.keys.maxOrNull() ?: 0)).associateWith { currentPosition ->
            numberOfCrabsAtEachPosition
                .filterKeys { it != currentPosition }
                .map { (position, numberOfCrabs) -> numberOfCrabs * calculateFuelUsed(abs(currentPosition - position)) }
                .sum()
        }

        val (_, fuelExpaneded) = simulation.minByOrNull { it.value }!!

        return fuelExpaneded
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
