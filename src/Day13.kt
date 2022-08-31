fun main() {
    fun foldHorizontal(grid: MutableSet<Pair<Int, Int>>, fold: String) {
        val foldLine = fold.drop(2).toInt()
        val toRemove = grid.filter { (x, y) -> y > foldLine }
        grid.removeIf{it in toRemove}
        grid.addAll(toRemove.map { (x, y) -> Pair(x, foldLine-(y-foldLine)) })
    }

    fun foldVertical(grid: MutableSet<Pair<Int, Int>>, fold: String) {
        val foldLine = fold.drop(2).toInt()
        val toRemove = grid.filter { (x, y) -> x > foldLine }
        grid.removeIf{it in toRemove}
        grid.addAll(toRemove.map { (x, y) -> Pair(foldLine-(x-foldLine), y) })
    }

    fun printGridSize(grid: MutableSet<Pair<Int, Int>>) {
        val sizeX = grid.map { it.first }.maxOf { it }
        val sizeY = grid.map { it.second }.maxOf { it }
        for (y in 0..sizeY) {
            val row = mutableListOf<Char>()
            for (x in 0..sizeX) {
                if (Pair(x, y) in  grid)
                    row.add('#')
                else
                    row.add('.')
            }
            println(row.joinToString(""))
        }
        println()
    }

    fun part1(input: List<String>): Int {
        val grid = mutableSetOf<Pair<Int, Int>>()
        val folds = mutableListOf<String>()
        for (line in input) {
            if (line.startsWith("fold along"))
                folds.add(line.split(" ")[2])
            else if (line.isNotEmpty()){
                val parts = line.split(",")
                grid.add(Pair(parts.first().toInt(), parts.last().toInt()))
            }
        }

        for (fold in folds.take(1)) {
            if (fold.startsWith("x="))
                foldVertical(grid, fold)
            else
                foldHorizontal(grid, fold)
        }

        return grid.count()
    }

    fun part2(input: List<String>) {
        val grid = mutableSetOf<Pair<Int, Int>>()
        val folds = mutableListOf<String>()
        for (line in input) {
            if (line.startsWith("fold along"))
                folds.add(line.split(" ")[2])
            else if (line.isNotEmpty()){
                val parts = line.split(",")
                grid.add(Pair(parts.first().toInt(), parts.last().toInt()))
            }
        }

        for (fold in folds) {
            if (fold.startsWith("x="))
                foldVertical(grid, fold)
            else
                foldHorizontal(grid, fold)
        }
        printGridSize(grid)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13b")
    println(part1(input))
}
