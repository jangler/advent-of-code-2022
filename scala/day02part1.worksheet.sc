val src = io.Source.fromFile("input/day02.txt")
val lines = src.getLines.toList

enum Throw:
  case Rock, Paper, Scissors

  def score: Int = this.ordinal + 1

  def beats(other: Throw): Boolean = (this, other) match
    case (Rock, Scissors) | (Paper, Rock) | (Scissors, Paper) => true
    case _                                                    => false

import Throw.*

enum Result:
  case Win, Lose, Draw

  def score: Int = this match
    case Win  => 6
    case Lose => 0
    case Draw => 3

import Result.*

case class Round(a: Throw, b: Throw):
  def score: Int =
    val result =
      if a beats b then Lose
      else if b beats a then Win
      else Draw
    b.score + result.score

// examples
Round(Rock, Paper).score
Round(Paper, Rock).score
Round(Scissors, Scissors).score

val rounds = lines.map(line =>
  Round(
    line.charAt(0) match
      case 'A' => Rock
      case 'B' => Paper
      case 'C' => Scissors
    ,
    line.charAt(2) match
      case 'X' => Rock
      case 'Y' => Paper
      case 'Z' => Scissors
  )
)

// part one answer
rounds.map(_.score).sum