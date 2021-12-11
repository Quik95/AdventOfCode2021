typealias Grid = MutableList<MutableList<Int>>

fun main() {
    var numberOfFlashes = 0

    fun checkIfInBounds(X: Int, Y: Int): Boolean {
        return X in 0..9 && Y in 0..9
    }

    fun getAdjacent(X: Int, Y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(X, Y - 1),
            Pair(X + 1, Y - 1),
            Pair(X + 1, Y),
            Pair(X + 1, Y + 1),
            Pair(X, Y + 1),
            Pair(X - 1, Y + 1),
            Pair(X - 1, Y),
            Pair(X - 1, Y - 1),
        ).filter { (x, y) -> checkIfInBounds(x, y) }
    }

    fun ignite(grid: Grid, offsetX: Int, offsetY: Int, localFlashes: MutableList<Int>) {
        if (!checkIfInBounds(offsetX, offsetY) || grid[offsetY][offsetX] == 0)
            return

        numberOfFlashes += 1
        localFlashes[0] += 1
        grid[offsetY][offsetX] = 0
        val adjacentOctopi = getAdjacent(offsetX, offsetY)

        adjacentOctopi.forEach { (x, y) ->
            if(grid[y][x] != 0)
                grid[y][x] += 1
        }

        adjacentOctopi.forEach { (x, y) ->
            if (grid[y][x] > 9)
                ignite(grid, x, y, localFlashes)
        }
    }

    fun step(grid: Grid): Boolean {
        grid.forEachIndexed { Y, row ->
            row.forEachIndexed { X, _ -> grid[Y][X] += 1 }
        }

        val localFlashes = mutableListOf(0)

        val tinders =
            grid.flatMapIndexed { Y, row -> row.mapIndexed { X, octopi -> if (octopi > 9) Pair(X, Y) else null } }
                .filterNotNull()
        tinders.forEach { (x, y) -> ignite(grid, x, y, localFlashes) }

        return localFlashes.first() == 100
    }

    fun part1(input: List<String>): Int {
        numberOfFlashes = 0
        val STEP_COUNT = 100

        val grid: Grid = input.map { it.map(Character::getNumericValue).toMutableList() }.toMutableList()

        (1..STEP_COUNT).forEach { step(grid) }

        return numberOfFlashes
    }

    fun part2(input: List<String>): Int {
        val grid: Grid = input.map { it.map(Character::getNumericValue).toMutableList() }.toMutableList()

        var i = 1
        while (!step(grid) || i > 100000)
            i += 1

        return i
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
