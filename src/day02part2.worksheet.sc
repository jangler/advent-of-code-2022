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

case class Round(opposingThrow: Throw, result: Result):
  def myThrow: Throw = result match
    case Win => Throw.values.find(_ beats opposingThrow).get
    case Lose => Throw.values.find(opposingThrow beats _).get
    case Draw => opposingThrow

  def score: Int = myThrow.score + result.score

// examples
Round(Rock, Draw).score
Round(Paper, Lose).score
Round(Scissors, Win).score

val rounds = lines.map(line =>
  Round(
    line.charAt(0) match
      case 'A' => Rock
      case 'B' => Paper
      case 'C' => Scissors
    ,
    line.charAt(2) match
      case 'X' => Lose
      case 'Y' => Draw
      case 'Z' => Win
  )
)

// part two answer
rounds.map(_.score).sum