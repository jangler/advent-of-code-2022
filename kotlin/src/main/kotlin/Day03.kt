import java.io.File

object Day03 {
    const val filename = "day03.txt"

    fun partOneSolution(path: String) =
        File(path)
            .readLines()
            .mapNotNull { Rucksack(it).itemInBothCompartments?.priority }
            .sum()

    fun partTwoSolution(path: String) =
        File(path)
            .readLines()
            .chunked(3)
            .mapNotNull { commonChar(it)?.priority }
            .sum()

    @JvmInline
    private value class Rucksack(val s: String) {
        val itemInBothCompartments
            get(): Char? {
                val second = secondCompartment()
                return firstCompartment().find { it in second }
            }

        private fun firstCompartment() = s.slice(0 until s.length / 2)
        private fun secondCompartment() = s.slice(s.length / 2 until s.length)
    }

    private fun commonChar(xs: List<String>) =
        xs.first().find() { char ->
            xs.drop(1).all { char in it }
        }

    private val Char.priority
        get() = when (this) {
            in 'a'..'z' -> 1 + (this - 'a')
            in 'A'..'Z' -> 27 + (this - 'A')
            else -> throw Exception("char $this is not letter")
        }
}