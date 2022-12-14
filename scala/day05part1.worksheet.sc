val exampleLines = io.Source
  .fromString("""
|    [D]    
|[N] [C]    
|[Z] [M] [P]
| 1   2   3 
|
|move 1 from 2 to 1
|move 3 from 1 to 3
|move 2 from 2 to 1
|move 1 from 1 to 2
|""".stripMargin)
  .getLines
  .toList
  .tail

import scala.collection.mutable.Stack

type Crate = Char

def cratesFromLine(line: String): Map[Int, Crate] =
  line
    .grouped(4)
    .zipWithIndex
    .filter((s, i) => !s.isBlank)
    .map((s, i) => i + 1 -> s(1))
    .toMap

exampleLines.take(3).map(cratesFromLine)

def stacksFromInput(lines: Seq[String]): Vector[Stack[Crate]] =
  val crates = lines.takeWhile(_(1) != '1').flatMap(cratesFromLine(_).toSeq)
  val stacks = Vector.fill(crates.map((i, c) => i).max)(Stack[Crate]())
  for (index, crate) <- crates.reverse do stacks(index - 1).push(crate)
  stacks

val exampleStacks = stacksFromInput(exampleLines)

case class Move(amount: Int, src: Int, dst: Int):
  def apply(stacks: Seq[Stack[Crate]]): Unit =
    for _ <- 1 to amount do
      val crate = stacks(src - 1).pop()
      stacks(dst - 1).push(crate)

def moveFromLine(line: String): Option[Move] =
  "move (\\d+) from (\\d+) to (\\d+)".r
    .findPrefixMatchOf(line)
    .map(m => Move(m.group(1).toInt, m.group(2).toInt, m.group(3).toInt))

val exampleMoves = exampleLines.takeRight(4).flatMap(moveFromLine)
exampleMoves.foreach(_.apply(exampleStacks))

def message(stacks: Seq[Stack[Crate]]): String = stacks.map(_.top).mkString

message(exampleStacks)

val lines = io.Source.fromFile("input/day05.txt").getLines.toList
val stacks = stacksFromInput(lines)
val moves = lines.dropWhile(_.nonEmpty).tail.map(moveFromLine(_).get)
moves.foreach(_.apply(stacks))
message(stacks)
