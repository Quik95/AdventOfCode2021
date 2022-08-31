import java.math.BigInteger

const val VERSION_LENGTH = 3
const val TYPE_ID_LENGTH = 3
const val LITERAL_GROUP_LENGTH = 5

class BITSReader(bits: String) {
    var versionSum = 0

    private val binaryStream: String = bits
    private var currentPosition = 0

    fun readNextPacket(): BigInteger {
        // read header
        val typeID = readHeader()

        return if (typeID == 4)
            readLiteralPacket()
        else
            readOperatorPacket(typeID)
    }

    private fun readLiteralPacket(): BigInteger {
        val groups = mutableListOf<String>()
        var firstBit = '1'
        do {
            val g = readLiteralGroup()
            firstBit = g.first()
            groups.add(g.drop(1))
        } while (firstBit == '1')

        return BigInteger(groups.joinToString(""), 2)
    }

    private fun readOperatorPacket(typeID: Int): BigInteger {
        val lengthTypeID = readLengthTypeID()
        val operationData = readInData(lengthTypeID)

        return when (typeID) {
            0 -> operationData.sumOf{it}
            1 -> operationData.fold(BigInteger("1")){ cum, next -> cum*next }
            2 -> operationData.minOrNull()!!
            3 -> operationData.maxOrNull()!!
            5 -> if (operationData.first() > operationData.last()) BigInteger("1") else BigInteger("0")
            6 -> if (operationData.first() < operationData.last()) BigInteger("1") else BigInteger("0")
            7 -> if (operationData.first() == operationData.last()) BigInteger("1") else BigInteger("0")
            else -> throw Exception("something went very wrong")
        }
    }

    private fun readInData(lengthTypeID: String): List<BigInteger> {
        return if (lengthTypeID == "0")
            readOperatorPacketType0()
        else
            readOperatorPacketType1()
    }

    private fun readOperatorPacketType0(): List<BigInteger> {
        val totalLengthOfSubpackets = readTotalLengthInBits()
        val endPosition = currentPosition+totalLengthOfSubpackets

        val res = mutableListOf<BigInteger>()

        while (currentPosition != endPosition) {
            res.add(readNextPacket())
        }

        return res
    }

    private fun readTotalLengthInBits(): Int {
        return Integer.parseInt(readNBits(15), 2)
    }

    private fun readTotalNumberOfSubpackets(): Int {
        return Integer.parseInt(readNBits(11), 2)
    }

    private fun readOperatorPacketType1(): List<BigInteger> {
        val res = mutableListOf<BigInteger>()

        val numberOfSubpackets = readTotalNumberOfSubpackets()
        (1..numberOfSubpackets).forEach { _ -> res.add(readNextPacket()) }

        return res
    }

    private fun readLengthTypeID(): String { return readNBits(1) }

    private fun readLiteralGroup(): String {
        return readNBits(LITERAL_GROUP_LENGTH)
    }

    private fun readHeader(): Int {
        readVersion()
        return readTypeID()
    }

    private fun readNBits(n: Int): String {
        val res = binaryStream.drop(currentPosition).take(n)
        currentPosition += n
        return res
    }

    private fun readVersion() {
        val v =  Integer.parseInt(readNBits(VERSION_LENGTH), 2)
        versionSum += v
    }

    private fun readTypeID(): Int {
        return Integer.parseInt(readNBits(TYPE_ID_LENGTH), 2)
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val asBinary = input.first().chunked(1).map { val i = Integer.parseInt(it, 16); return@map Integer.toBinaryString(i).padStart(4, '0')}.joinToString("")

        val reader = BITSReader(asBinary)
        reader.readNextPacket()

        return reader.versionSum
    }

    fun part2(input: List<String>): BigInteger {
        val asBinary = input.first().chunked(1).map { val i = Integer.parseInt(it, 16); return@map Integer.toBinaryString(i).padStart(4, '0')}.joinToString("")

        val reader = BITSReader(asBinary)
        return reader.readNextPacket()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")

    val p1Expected = 40
    val p1Got = part1(testInput)
//    check(p1Expected == p1Got) { "Part 1 failed test!!!\nExpected: $p1Expected, got: $p1Got" }

    val p2Expected = 1
    val p2Got = part2(testInput)
//    check(p2Expected == p2Got) { "Part 2 failed test!!!\nExpected: $p2Expected, got: $p2Got" }

    val input = readInput("Day16")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}