import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var xSize = 0
        var ySize = 0

        for (line in input) {
            val (x1, y1, x2, y2) = line.split(" -> ").map { it.split(",") }.flatten().map { it.toInt() }
            if (x1 > xSize || x2 > xSize)
                xSize = max(x1, x2)
            if (y1 > ySize || y2 > ySize)
                ySize = max(y1, y2)
        }

        val pointsMap = Array<IntArray>(ySize + 1) { IntArray(xSize + 1) }

        for (line in input) {
            val (x1, y1, x2, y2) = line.split(" -> ").map { it.split(",") }.flatten().map { it.toInt() }
            if (x1 == x2) {
                val start = min(y1, y2)
                val end = max(y1, y2)
                for (i in start..end) {
                    pointsMap[i][x1] += 1
                }
            } else if (y1 == y2) {
                val start = min(x1, x2)
                val end = max(x1, x2)
                for (i in start..end) {
                    pointsMap[y1][i] += 1
                }
            }
        }

        return pointsMap.map { it.filter { it >= 2 } }.flatten().count()
    }

    fun part2(input: List<String>): Int {
        var xSize = 0
        var ySize = 0

        for (line in input) {
            val (x1, y1, x2, y2) = line.split(" -> ").map { it.split(",") }.flatten().map { it.toInt() }
            if (x1 > xSize || x2 > xSize)
                xSize = max(x1, x2)
            if (y1 > ySize || y2 > ySize)
                ySize = max(y1, y2)
        }

        val pointsMap = Array<IntArray>(ySize + 1) { IntArray(xSize + 1) }

        for (line in input) {
            val (x1, y1, x2, y2) = line.split(" -> ").map { it.split(",") }.flatten().map { it.toInt() }
            if (x1 == x2) {
                val start = min(y1, y2)
                val end = max(y1, y2)
                for (i in start..end) {
                    pointsMap[i][x1] += 1
                }
            } else if (y1 == y2) {
                val start = min(x1, x2)
                val end = max(x1, x2)
                for (i in start..end) {
                    pointsMap[y1][i] += 1
                }
            } else {
                val iteratorX = if (x1 < x2) (x1..x2) else (x1 downTo x2)
                val iteratorY = if (y1 < y2) (y1..y2) else (y1 downTo y2)

                for ((x, y) in iteratorX zip iteratorY) {
                    pointsMap[y][x] += 1
                }
            }
        }

        val result = pointsMap.map { it.filter { it >= 2 } }.flatten().count()
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
