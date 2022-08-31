fun main() {
    data class Cave(val position: String, val pathSoFar: Set<String>)


    fun part1(input: List<String>): Int {
        val graph = mutableMapOf<String, MutableSet<String>>();
        input.map { it -> it.split('-') }.forEach{ it ->
            graph.getOrPut(it.first()) { mutableSetOf() }.add(it.last())
            graph.getOrPut(it.last()) { mutableSetOf() }.add(it.first())
        }

        var numberOfCaves = 0
        var queue = ArrayDeque<Cave>(listOf(Cave(position = "start", setOf("start"))))

        while (queue.isNotEmpty()) {
            val current = queue.first()
            queue.removeFirst()

            if (current.position == "end") {
                numberOfCaves++
                continue
            }

            graph[current.position]!!
                .filter { !current.pathSoFar.contains(it) }
                .forEach {
                    val newPath = current.pathSoFar.toMutableSet()
                    if (it.first().isLowerCase())
                        newPath.add(it)

                    queue.add(Cave(position = it, newPath))
                }
        }

        return numberOfCaves
    }

    fun part2(input: List<String>): Int {
        data class Cave(val position: String, val pathSoFar: Set<String>, val twice: Boolean)

        val graph = mutableMapOf<String, MutableSet<String>>();
        input.map { it -> it.split('-') }.forEach{ it ->
            graph.getOrPut(it.first()) { mutableSetOf() }.add(it.last())
            graph.getOrPut(it.last()) { mutableSetOf() }.add(it.first())
        }

        var numberOfCaves = 0
        val queue = ArrayDeque<Cave>(listOf(Cave(position = "start", setOf("start"), false)))

        while (queue.isNotEmpty()) {
            val current = queue.first()
            queue.removeFirst()

            if (current.position == "end") {
                numberOfCaves++
                continue
            }

            graph[current.position]!!
                .filter { it != "start" }
                .forEach {
                    val seenBefore = current.pathSoFar.contains(it)
                    if (!seenBefore) {
                        val newPath = current.pathSoFar.toMutableSet()
                        if (it.first().isLowerCase()) newPath.add(it)
                        queue.add(Cave(position = it, newPath, current.twice))
                    } else if (!current.twice && it != "end")
                        queue.add(Cave(position = it, current.pathSoFar, true))
                }
        }

        return numberOfCaves
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
