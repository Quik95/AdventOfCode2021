import java.util.PriorityQueue
import kotlin.math.absoluteValue
import kotlin.math.max

fun main() {
    data class Coordinate(val X: Int, val Y: Int) {
        fun isValid(goal: Coordinate): Boolean {
            return this.X >= 0 && this.Y >= 0 && this.X <= goal.X && this.Y <= goal.Y
        }
    }

    data class Item(val point: Coordinate, val priority: Int)

    fun getNeighbours(current: Coordinate, goal: Coordinate): List<Coordinate> {
        val res = listOf(
            Coordinate(current.X, current.Y-1),
            Coordinate(current.X, current.Y+1),
            Coordinate(current.X-1, current.Y),
            Coordinate(current.X+1, current.Y)
        )
        return res.filter { it.isValid(goal) }
    }

    fun heurystyka(a: Coordinate, b: Coordinate): Int {
        val (x1, y1) = a
        val (x2, y2) = b
        return (x1-x2).absoluteValue + (y1-y2).absoluteValue
    }

    fun part1(input: List<String>): Int {
        val tmp = input.map {it.toCharArray().map { it2 -> it2.digitToInt() } }
        val map = mutableMapOf<Coordinate, Int>()
        tmp.forEachIndexed { row, rowData ->
            rowData.forEachIndexed { column, value -> map[Coordinate(column, row)] = value }
        }

        val start = Coordinate(0, 0)
        val goal = Coordinate(input.first().count()-1, input.count()-1)
        val frontier = PriorityQueue<Item> { self, other ->
            if (self.priority > other.priority) 1
            else if (self.priority < other.priority) -1
            else 0
        }
        frontier.add(Item(Coordinate(0, 0), 0))

        val cameFrom = mutableMapOf<Coordinate, Coordinate?>(start to null)
        val costSoFar = mutableMapOf<Coordinate, Int>(start to 0)

        while (frontier.isNotEmpty()) {
            val current = frontier.poll()

             if (current.point == goal)
                 break

            for (next in getNeighbours(current.point, goal)) {
                val newCost = costSoFar[current.point]!! + map[next]!!
                if (!costSoFar.containsKey(next) || newCost < costSoFar.getOrDefault(next, Int.MAX_VALUE)) {
                    costSoFar[next] = newCost
                    frontier.add(Item(next, newCost + heurystyka(next, goal)))
                    cameFrom[next] = current.point
                }
            }
        }

        return costSoFar[goal]!!
    }

    fun part2(input: List<String>): Int {
        val tmp = input.map {it.toCharArray().map { it2 -> it2.digitToInt() } }
        val map = mutableMapOf<Coordinate, Int>()
        tmp.forEachIndexed { row, rowData ->
            rowData.forEachIndexed { column, value -> map[Coordinate(column, row)] = value }
        }

        val numberOfRows = input.first().count()
        val numberOfColumns = input.count()

        (0 until numberOfRows*5).forEach {rowNumber -> (0 until numberOfColumns*5).forEach { columnNumber ->
            if (rowNumber in (0 until numberOfRows) && columnNumber in (0 until numberOfColumns))
                return@forEach
            var riskLevel = max(map.getOrDefault(Coordinate(rowNumber-numberOfRows, columnNumber), -1), map.getOrDefault(Coordinate(rowNumber, columnNumber-numberOfColumns), -1)) + 1
            if (riskLevel>9) riskLevel = 1
            map[Coordinate(rowNumber, columnNumber)] = riskLevel
        }}

        val start = Coordinate(0, 0)
        val goal = Coordinate(numberOfRows*5-1, numberOfColumns*5-1)
        val frontier = PriorityQueue<Item> { self, other ->
            if (self.priority > other.priority) 1
            else if (self.priority < other.priority) -1
            else 0
        }
        frontier.add(Item(Coordinate(0, 0), 0))

        val cameFrom = mutableMapOf<Coordinate, Coordinate?>(start to null)
        val costSoFar = mutableMapOf<Coordinate, Int>(start to 0)

        while (frontier.isNotEmpty()) {
            val current = frontier.poll()

            if (current.point == goal)
                break

            for (next in getNeighbours(current.point, goal)) {
                val newCost = costSoFar[current.point]!! + map[next]!!
                if (!costSoFar.containsKey(next) || newCost < costSoFar.getOrDefault(next, Int.MAX_VALUE)) {
                    costSoFar[next] = newCost
                    frontier.add(Item(next, newCost + heurystyka(next, goal)))
                    cameFrom[next] = current.point
                }
            }
        }

        return costSoFar[goal]!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")

    val p1Expected = 40
    val p1Got = part1(testInput)
    check(p1Expected == p1Got) { "Part 1 failed test!!!\nExpected: $p1Expected, got: $p1Got" }

    val p2Expected = 315
    val p2Got = part2(testInput)
    check(p2Expected == p2Got) { "Part 2 failed test!!!\nExpected: $p2Expected, got: $p2Got" }

    val input = readInput("Day15")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
