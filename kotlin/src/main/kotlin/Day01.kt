import java.io.File

object Day01 {
    const val examplePath = "../examples/day01.txt"
    const val inputPath = "../input/day01.txt"

    fun partOneSolution(path: String): Int {
        val lines = File(path).readLines()
        val elves = parseElves(lines)
        return elves.max()
    }

    private fun parseElves(lines: List<String>): List<Int> =
        groupLines(lines).map { line -> line.sumOf { it.toInt() } }

    private fun groupLines(lines: List<String>): List<List<String>> =
        if (lines.isEmpty()) listOf()
        else {
            val group = lines.takeWhile { it.isNotEmpty() }
            val rest = lines.drop(group.size + 1)
            listOf(group) + groupLines(rest)
        }
}