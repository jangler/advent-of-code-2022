import java.io.File

object Day01 {
    const val examplePath = "../examples/day01.txt"
    const val inputPath = "../input/day01.txt"

    fun partOneSolution(path: String): Int = loadElves(path).max()

    fun partTwoSolution(path: String): Int = loadElves(path).top(3).sum()

    private fun loadElves(path: String): List<Int> =
        parseElves(File(path).readLines())

    private fun parseElves(lines: List<String>): List<Int> =
        groupLines(lines).map { line -> line.sumOf { it.toInt() } }

    private fun groupLines(lines: List<String>): List<List<String>> =
        if (lines.isEmpty()) listOf()
        else {
            val group = lines.takeWhile { it.isNotEmpty() }
            val rest = lines.drop(group.size + 1)
            listOf(group) + groupLines(rest)
        }

    private fun List<Int>.top(n: Int): List<Int> = this.sortedDescending().take(n)
}