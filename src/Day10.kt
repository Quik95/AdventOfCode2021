import java.util.ArrayDeque

fun main() {
    fun part1(input: List<String>): Int {
        val weights = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )
        var score = 0

        fun checkIsClosing(o: Char, c: Char) = when (o) {
            '(' -> c == ')'
            '[' -> c == ']'
            '{' -> c == '}'
            '<' -> c == '>'
            else -> false
        }


        for (line in input) {
            val stack = ArrayDeque<Char>()
            for (character in line) {
                if (character in listOf('(', '[', '{', '<'))
                    stack.push(character)
                else {
                    if (checkIsClosing(stack.first, character))
                        stack.pop()
                    else {
                        score += weights.getOrDefault(character, 0)
                        break
                    }
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Long {
        fun checkIsClosing(o: Char, c: Char) = when (o) {
            '(' -> c == ')'
            '[' -> c == ']'
            '{' -> c == '}'
            '<' -> c == '>'
            else -> false
        }

        val weights = mapOf(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )

        val scores = mutableListOf<Long>()
        input.forEach { line ->
            val stack = ArrayDeque<Char>()
            for (character in line) {
                if (character in listOf('(', '[', '{', '<'))
                    stack.push(character)
                else {
                    if (checkIsClosing(stack.first, character))
                        stack.pop()
                    else
                        return@forEach
                }
            }
            var lineScore: Long = 0
            for (c in stack) {
                lineScore = lineScore * 5 + weights.getOrDefault(c, 0)
            }
            scores.add(lineScore)
        }

        return scores.sorted().getOrElse(scores.count() / 2) { 0 }
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
