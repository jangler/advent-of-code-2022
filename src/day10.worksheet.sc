import scala.collection.mutable.ArrayBuffer

class Crt():
  var x = 1
  val xStates = ArrayBuffer[Int](x)

  def addx(v: Int): Unit =
    xStates ++= Seq(x, x)
    x += v

  def noop(): Unit = xStates += x

  def exec(s: String): Unit =
    s.split(" ").toList match
      case "noop" :: Nil      => noop()
      case "addx" :: v :: Nil => addx(v.toInt)
      case ins => throw Exception(s"unrecognized instruction: $ins")

  override def toString: String =
    val stateStr = xStates.mkString(", ")
    s"Crt($stateStr, $x)"

  def signalStrength(i: Int): Int = xStates(i) * i

  def render: String =
    val rows =
      for group <- xStates.tail.grouped(40)
      yield group.zipWithIndex
        .map((x, i) => if math.abs(x - i) <= 1 then '#' else '.')
        .mkString
    "\n" + rows.mkString("\n")

def simulate(instructions: Seq[String]): Crt =
  val crt = Crt()
  instructions.foreach(crt.exec)
  crt

def signalProfile(crt: Crt): Seq[Int] =
  (20 to 220 by 40).map(crt.signalStrength)

// example 1
val ex1Lines = io.Source.fromFile("examples/day10small.txt").getLines.toList
simulate(ex1Lines)

// example 2
val ex2Lines = io.Source.fromFile("examples/day10large.txt").getLines.toList
val ex2Crt = simulate(ex2Lines)
signalProfile(ex2Crt)

// part one answer
val lines = io.Source.fromFile("input/day10.txt").getLines.toList
(simulate(lines)).sum

// example 2
ex2Crt.render

// part two answer
simulate(lines).render
