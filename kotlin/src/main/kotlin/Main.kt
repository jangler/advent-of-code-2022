const val exampleDir = "../examples/"
const val inputDir = "../input/"

fun main() {
    val day = Day04
    val path = inputDir + day.filename
    println("part one: ${day.partOneSolution(path)}")
    println("part two: ${day.partTwoSolution(path)}")
}