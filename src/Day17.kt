fun main() {
    fun checkIfWillHit(velocityX: Int, velocityY: Int, boxX: Pair<Int, Int>, boxY: Pair<Int, Int>): Int? {
        var vX = velocityX
        var vY = velocityY

        var currentX = 0
        var currentY = 0

        var maxHeight = currentY

        while (currentX <= boxX.second) {
            if (currentX in (boxX.first..boxX.second) && currentY in (boxY.first..boxY.second)) return maxHeight

            if (vX == 0 && vY < 0 && currentY < boxY.second)
                break

            currentX += vX
            currentY += vY

            vX += if (vX > 0) -1 else if (vX < 0) 1 else 0
            vY -= 1

            maxHeight = if (currentY > maxHeight) currentY else maxHeight
        }

        return null
    }

    fun part1(input: List<String>): Int {
        val match = Regex("""x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""").find(input.first())!!
        val (xStart, xEnd, yStart, yEnd) = match.destructured.toList().map { Integer.parseInt(it) }

        var maxHeight = -Int.MAX_VALUE

        for (i in 0..xEnd) {
            for (j in 0..xEnd) {
                var height = checkIfWillHit(i, j, Pair(xStart, xEnd), Pair(yStart, yEnd))
                maxHeight = if (height != null && height > maxHeight) height else maxHeight
            }
        }

        return maxHeight
    }

    fun part2(input: List<String>): Int {
        val match = Regex("""x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""").find(input.first())!!
        val (xStart, xEnd, yStart, yEnd) = match.destructured.toList().map { Integer.parseInt(it) }

        var numberOfHits = 0

        for (i in -1000..1000) {
            for (j in -1000..1000) {
                if (checkIfWillHit(i, j, Pair(xStart, xEnd), Pair(yStart, yEnd)) != null)
                    numberOfHits++
            }
        }

        return numberOfHits
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")

    val p1Expected = 45
    val p1Got = part1(testInput)
    check(p1Expected == p1Got) { "Part 1 failed test!!!\nExpected: $p1Expected, got: $p1Got" }

    val p2Expected = 112
    val p2Got = part2(testInput)
    check(p2Expected == p2Got) { "Part 2 failed test!!!\nExpected: $p2Expected, got: $p2Got" }

    val input = readInput("Day17")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}