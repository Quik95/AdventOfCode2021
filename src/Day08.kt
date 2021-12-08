fun main() {
    fun part1(input: List<String>): Int {
        return input.flatMap { it.split(" | ").last().split(" ") }.count { it.length in listOf(2, 3, 4, 7) }
    }

    fun part2(input: List<String>): Int {
        val inputData = input.map { it.split(" | ").map { it.split(" ") } }

        var sum = 0

        inputData.forEach{(example, real) ->
            val digits = mutableMapOf(
                0 to setOf(),
                1 to example.first { it.length == 2 }.asIterable().toSet(),
                2 to setOf(),
                3 to setOf(),
                4 to example.first { it.length == 4 }.asIterable().toSet(),
                5 to setOf(),
                6 to setOf(),
                7 to example.first { it.length == 3 }.asIterable().toSet(),
                8 to example.first { it.length == 7 }.asIterable().toSet(),
                9 to setOf(),
            )

            example.forEach {
                val parts = it.asIterable().toSet()
                when(it.length) {
                    6 -> {
                        if (parts.containsAll(digits[7]!!)) {
                            if (parts.containsAll(digits[4]!!))
                                digits[9] = parts
                            else
                                digits[0] = parts
                        }
                        else
                            digits[6] = parts
                    }
                    5 -> {
                        if (parts.containsAll(digits[7]!!))
                            digits[3] = parts
                    }
                }
            }

            val middle = digits[8]!!.subtract(digits[0]!!)
            val topLeft = digits[4]!!.subtract(middle.plus(digits[1]!!))

            example.filter { it.length == 5 }.forEach {
                val parts = it.asIterable().toSet()
                if (parts.containsAll(topLeft))
                    digits[5] = parts
                else
                    digits[2] = parts
            }

            val reversed = digits.mapValues { it.value.sorted().joinToString("") }.entries.associateBy({it.value}) { it.key }

            val digit = real.map { it.asIterable().sorted().joinToString("") }.toList().map { (reversed[it] ?: 2) }.joinToString("").toInt()

            sum += digit
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
