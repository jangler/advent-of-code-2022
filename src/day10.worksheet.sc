import Instruction.*
enum Instruction(val newStates: Int => List[Int]):
  case Addx(val v: Int) extends Instruction(x => List(x, x + v))
  case NoOp extends Instruction(x => List(x))

object Instruction:
  def parse(s: String): Instruction =
    s.split(" ").toList match
      case "noop" :: Nil      => NoOp
      case "addx" :: v :: Nil => Addx(v.toInt)
      case _ => throw Exception(s"unrecognized instruction: $s")

type Program = Seq[Instruction]

object Program:
  def read(lines: Seq[String]): Program =
    lines.map(Instruction.parse)

extension (p: Program)
  def states: List[Int] =
    p.foldLeft(List(1, 1))((xs, ins) => xs ++ ins.newStates(xs.last))

  def render: String =
    val rows =
      for group <- states.tail.dropRight(1).grouped(40)
      yield group.zipWithIndex
        .map((x, i) => if math.abs(x - i) <= 1 then '#' else '.')
        .mkString
    "\n" + rows.mkString("\n")

def signalProfile(xs: Seq[Int]): Seq[Int] =
  (20 to 220 by 40).map(i => i * xs(i))

def readLines(path: String) =
  io.Source.fromFile(path).getLines.toList

// example 1
val ex1Lines = readLines("examples/day10small.txt")
val ex1Prog = Program.read(ex1Lines)
ex1Prog.states

// example 2
val ex2Lines = readLines("examples/day10large.txt")
val ex2Prog = Program.read(ex2Lines)
signalProfile(ex2Prog.states)

// part one answer
val lines = readLines("input/day10.txt")
val prog = Program.read(lines)
signalProfile(prog.states).sum

// example 2
ex2Prog.render

// part two answer
prog.render
