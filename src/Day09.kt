fun main() {
    fun part1(input: List<String>): Int {
        val asInts = input.map{ it.map(Character::getNumericValue) }
        val res = asInts.flatMapIndexed { rowIndex, row ->
            row.filterIndexed { columnIndex, value ->
                value < row.getOrElse(columnIndex + 1){9}
                && value < row.getOrElse(columnIndex - 1){9}
                && value < asInts.getOrElse(rowIndex + 1){ listOf() }.getOrElse(columnIndex){9}
                && value < asInts.getOrElse(rowIndex - 1){ listOf() }.getOrElse(columnIndex){9}
            }
        }

        return res.sumOf { it + 1 }
    }

    fun propagateSearch(depthMap: List<List<Int>>, foundPoints: MutableSet<Pair<Int, Int>>, point: Pair<Int, Int>): MutableSet<Pair<Int, Int>> {
        fun getPointValue(x: Int, y: Int) = depthMap.getOrElse(y){ listOf() }.getOrElse(x){-10}

        val (currentX, currentY) = point
        val pointValue = getPointValue(currentX, currentY)

        if (pointValue == 9)
            return foundPoints

        foundPoints.add(point)

        if (getPointValue(currentX, currentY+1) > pointValue)
            propagateSearch(depthMap, foundPoints, Pair(currentX, currentY+1))
        if (getPointValue(currentX, currentY-1) > pointValue)
            propagateSearch(depthMap, foundPoints, Pair(currentX, currentY-1))
        if (getPointValue(currentX+1, currentY) > pointValue)
            propagateSearch(depthMap, foundPoints, Pair(currentX+1, currentY))
        if (getPointValue(currentX-1, currentY) > pointValue)
            propagateSearch(depthMap, foundPoints, Pair(currentX-1, currentY))

        return foundPoints
    }

    fun part2(input: List<String>): Int {
        val asInts = input.map{ it.map(Character::getNumericValue) }
        val res = asInts.flatMapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, value ->
                if (value < row.getOrElse(columnIndex + 1){9}
                        && value < row.getOrElse(columnIndex - 1){9}
                        && value < asInts.getOrElse(rowIndex + 1){ listOf() }.getOrElse(columnIndex){9}
                        && value < asInts.getOrElse(rowIndex - 1){ listOf() }.getOrElse(columnIndex){9})
                    return@mapIndexed Pair(columnIndex, rowIndex)
                else
                    return@mapIndexed null
            }

        }.filterNotNull()

        val basins = res.map { point -> propagateSearch(asInts,  mutableSetOf(), point) }
        return basins.map{ it.count() }.sortedDescending().take(3).reduce{cum, value -> cum*value}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
