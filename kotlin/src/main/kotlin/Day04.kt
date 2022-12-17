import java.io.File
import java.lang.IllegalArgumentException

object Day04 {
    const val filename = "day04.txt"

    fun partOneSolution(path: String) =
        File(path).readLines().map(::parsePair).count {
            it.first.containsAll(it.second) || it.second.containsAll(it.first)
        }

    fun partTwoSolution(path: String) =
        File(path).readLines().map(::parsePair).count {
            it.first overlapsWith it.second
        }

    private fun parsePair(line: String): Pair<IntRange, IntRange> {
        val match = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex().matchEntire(line)
            ?: throw IllegalArgumentException("could not parse line: $line")
        val ints = match.groupValues.drop(1).map { it.toInt() }
        return Pair(ints[0]..ints[1], ints[2]..ints[3])
    }

    private fun IntRange.containsAll(other: IntRange) =
        this.first <= other.first && this.last >= other.last

    private infix fun IntRange.overlapsWith(other: IntRange) =
        this.intersect(other).isNotEmpty()
}