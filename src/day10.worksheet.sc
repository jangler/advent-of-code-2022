import scala.collection.mutable.ArrayBuffer

class Cpu():
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

  override def toString(): String =
    val stateStr = xStates.mkString(", ")
    s"Cpu($stateStr, $x)"

  def signalStrength(i: Int): Int = xStates(i) * i

def simulate(instructions: Seq[String]): Cpu =
  val cpu = Cpu()
  instructions.foreach(cpu.exec)
  cpu

def signalProfile(cpu: Cpu): Seq[Int] =
  (20 to 220 by 40).map(cpu.signalStrength)

// example 1
val ex1Lines = io.Source.fromFile("examples/day10small.txt").getLines.toList
simulate(ex1Lines)

// example 2
val ex2Lines = io.Source.fromFile("examples/day10large.txt").getLines.toList
val ex2Cpu = simulate(ex2Lines)
signalProfile(ex2Cpu)

// part one answer
val lines = io.Source.fromFile("input/day10.txt").getLines.toList
signalProfile(simulate(lines)).sum
