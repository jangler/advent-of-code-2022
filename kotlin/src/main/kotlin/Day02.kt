import java.io.File

object Day02 {
    const val filename = "day02.txt"

    fun partOneSolution(path: String) =
        File(path).readLines().sumOf { line ->
            val (opp, you) = parseRoundPartOne(line)
            you.score + outcomeOf(you, opp).score
        }

    fun partTwoSolution(path: String) =
        File(path).readLines().sumOf { line ->
            val (opp, outcome) = parseRoundPartTwo(line)
            val you = playForOutcome(opp, outcome)
            you.score + outcome.score
        }

    enum class Play(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        infix fun beats(other: Play) = when (this to other) {
            ROCK to SCISSORS, PAPER to ROCK, SCISSORS to PAPER -> true
            else -> false
        }
    }

    enum class Outcome(val score: Int) {
        LOSE(0), DRAW(3), WIN(6)
    }

    private fun outcomeOf(you: Play, opp: Play) = when {
        you beats opp -> Outcome.WIN
        opp beats you -> Outcome.LOSE
        else -> Outcome.DRAW
    }

    private fun parseRoundPartOne(line: String) =
        Pair(parseOppPlay(line[0]), parseYourPlay(line[2]))

    private fun parseRoundPartTwo(line: String) =
        Pair(parseOppPlay(line[0]), parseOutcome(line[2]))

    private fun parseOppPlay(char: Char) = when (char) {
        'A' -> Play.ROCK
        'B' -> Play.PAPER
        'C' -> Play.SCISSORS
        else -> throw Exception("no such play: $char")
    }

    private fun parseYourPlay(char: Char) = when (char) {
        'X' -> Play.ROCK
        'Y' -> Play.PAPER
        'Z' -> Play.SCISSORS
        else -> throw Exception("no such play: $char")
    }

    private fun parseOutcome(char: Char) = when (char) {
        'X' -> Outcome.LOSE
        'Y' -> Outcome.DRAW
        'Z' -> Outcome.WIN
        else -> throw Exception("no such outcome: $char")
    }

    private fun playForOutcome(opp: Play, outcome: Outcome) = when (outcome) {
        Outcome.LOSE -> Play.values().find { opp beats it }!!
        Outcome.DRAW -> opp
        Outcome.WIN -> Play.values().find { it beats opp }!!
    }
}