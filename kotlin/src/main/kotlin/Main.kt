const val exampleDir = "../examples/"
const val inputDir = "../input/"

fun main() {
    val day = Day03
    val path = inputDir + day.filename
    println("part one: ${day.partOneSolution(path)}")
    println("part two: ${day.partTwoSolution(path)}")
}