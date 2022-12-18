import java.io.File
import java.lang.IllegalArgumentException

typealias Stack = MutableList<Char>

object Day05 {
    const val filename = "day05.txt"

    fun partOneSolution(path: String) = solveWithCrane(path, Move::apply9000)

    fun partTwoSolution(path: String) = solveWithCrane(path, Move::apply9001)

    private fun solveWithCrane(path: String, crane: (Move, Array<Stack>) -> Unit): String {
        val (stacks, moves) = parseInputFrom(path)
        for (move in moves) crane(move, stacks)
        return stacks.topCrates().joinToString("")
    }

    private data class Move(val count: Int, val src: Int, val dst: Int) {
        fun apply9000(stacks: Array<Stack>) = repeat(count) {
                stacks[dst - 1].add(0, stacks[src - 1].removeFirst())
            }

        fun apply9001(stacks: Array<Stack>) {
            val movedCrates: MutableList<Char> = mutableListOf()
            repeat(count) { movedCrates += stacks[src - 1].removeFirst() }
            stacks[dst - 1].addAll(0, movedCrates)
        }
    }

    private fun parseInputFrom(path: String): Pair<Array<Stack>, List<Move>> {
        val lines = File(path).readLines()
        val stackLines = lines.takeWhile { it.isNotEmpty() }
        val moveLines = lines.drop(stackLines.size + 1)
        return Pair(parseStacks(stackLines), moveLines.map(::parseMove))
    }

    private fun parseStacks(lines: List<String>): Array<MutableList<Char>> {
        val stacks: MutableList<MutableList<Char>> = mutableListOf()
        for (line in lines.dropLast(1)) {
            for ((i, substring) in line.chunked(4).withIndex()) {
                if (substring[1] != ' ') {
                    while (stacks.size <= i) stacks.add(mutableListOf())
                    stacks[i].add(substring[1])
                }
            }
        }
        return stacks.toTypedArray()
    }

    private fun parseMove(line: String) =
        "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(line)?.let { match ->
            val ints = match.groupValues.drop(1).map { it.toInt() }
            Move(ints[0], ints[1], ints[2])
        } ?: throw IllegalArgumentException("could not parse move: $line")

    private fun Array<Stack>.topCrates() = this.map { it.first() }
}