typealias BingoBoard = MutableList<List<String>>

fun main() {
    fun part1(input: List<String>): Int {
        val BOARD_SIZE = 5

        val drawnNumbers = input.first().split(",").map { it.toInt() }

        val onlyBoards = input.drop(1).filter { it.isNotEmpty() }
        val numberOfBoards = onlyBoards.count() / BOARD_SIZE

        val boards = mutableListOf<Array<IntArray>>()
        (1..numberOfBoards).forEach { _ -> boards.add(Array(5) { IntArray(5) }) }

        val boardsMask = mutableListOf<Array<IntArray>>()
        (1..numberOfBoards).forEach { _ -> boardsMask.add(Array(5) { IntArray(5) }) }

        var boardsIndex = -1
        onlyBoards.forEachIndexed { lineNumber, line ->
            val rest = lineNumber % BOARD_SIZE
            if (rest == 0)
                boardsIndex += 1

            boards[boardsIndex][rest] = line.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray()
        }

        var winner = -1
        var winningNumber = -1
        for (luckyNumber in drawnNumbers) {
            if (winner != -1)
                break
            boards.forEachIndexed { boardIndex, board ->
                board.forEachIndexed { rowIndex, row ->
                    val columnIndex = row.indexOf(luckyNumber)
                    if (columnIndex != -1) {
                        boardsMask[boardIndex][rowIndex][columnIndex] = 1
                    }
                }
            }

            boardsMask.forEachIndexed { boardsIndex, board ->
                board.forEach { row ->
                    if (row.sum() == 5)
                        winner = boardsIndex
                    winningNumber = luckyNumber
                }

                (0 until BOARD_SIZE).forEach { columnIndex ->
                    var sum = 0
                    (0 until BOARD_SIZE).forEach { rowIndex -> sum += board[rowIndex][columnIndex] }
                    if (sum == 5)
                        winner = boardsIndex
                    winningNumber = luckyNumber
                }
            }
        }

        var result = 0
        boards[winner].forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, value ->
                if (boardsMask[winner][rowIndex][columnIndex] == 0)
                    result += value
            }
        }

        return result * winningNumber
    }

    fun simulateTurn(
        boards: List<List<List<Int>>>,
        boardsMask: MutableList<Array<IntArray>>, luckyNumber: Int): Unit {
            boards.forEachIndexed{boardIndex, board ->
                board.forEachIndexed{rowIndex, row ->
                    val columnIndex = row.indexOf(luckyNumber)
                    if (columnIndex != -1) {
                        boardsMask[boardIndex][rowIndex][columnIndex] = 1
                    }
                }
            }
    }

    fun checkForWin(board: Array<IntArray>): Boolean {
        board.forEach{row ->
            if (!row.contains(0))
                return true
        }

        (0 until board.first().count()).forEach{index ->
            val column = board.map { it[index] }
            if (!column.contains(0))
                return true
        }

        return false
    }

    fun part2(input: List<String>): Int {
        val luckyNumbers = input.first().split(",").map { it.toInt() }
        val boards =
            input.asSequence().drop(1).filter { it.isNotEmpty() }.map { it.split(" ") }
                .chunked(5)
                .map { board -> board.map { row -> row.filter { it.isNotEmpty() } } }
                .map { board -> board.map { row -> row.map { it.toInt() } } }
                .toList()
        val boardsMask = mutableListOf<Array<IntArray>>()
        (1..boards.count()).forEach { _ -> boardsMask.add(Array(5) { IntArray(5) }) }

        var winningNumber = luckyNumbers.first()
        var boardsLeft = boards.count()
        var lastWinningBoard = -1
        val previousWinners = mutableListOf<Int>()

        for (luckyNumber in luckyNumbers) {
            if (boardsLeft == 0)
                break

            simulateTurn(boards, boardsMask, luckyNumber)

            boards.forEachIndexed{boardIndex, _ ->
                if (checkForWin(boardsMask[boardIndex]) && boardIndex !in previousWinners) {
                    winningNumber = luckyNumber
                    boardsLeft -= 1
                    lastWinningBoard = boardIndex
                    previousWinners.add(boardIndex)
                }
            }
        }

        var boardScore = 0
        boards[lastWinningBoard].forEachIndexed { rowIndex, row ->
            row.forEachIndexed{columnIndex, column ->
                if (boardsMask[lastWinningBoard][rowIndex][columnIndex] == 0)
                    boardScore += column
            }
        }

        return boardScore*winningNumber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
