enum Direction:
  case U, D, L, R

import Direction.*

final case class Pos2D(x: Int, y: Int):
  def nudge(d: Direction): Pos2D = d match
    case U => this.copy(y = y + 1)
    case D => this.copy(y = y - 1)
    case L => this.copy(x = x - 1)
    case R => this.copy(x = x + 1)

  def touching(other: Pos2D): Boolean =
    math.abs(x - other.x) <= 1 && math.abs(y - other.y) <= 1

  def follow(other: Pos2D): Pos2D =
    if touching(other) then this
    else if other == Pos2D(x, y + 2) then nudge(U)
    else if other == Pos2D(x, y - 2) then nudge(D)
    else if other == Pos2D(x - 2, y) then nudge(L)
    else if other == Pos2D(x + 2, y) then nudge(R)
    else nudge(if other.x > x then R else L).nudge(if other.y > y then U else D)

type Rope = List[Pos2D]

extension (r: Rope)
  def nudge(d: Direction): Rope =
    r.tail.scanLeft(r.head.nudge(d))((a, b) => b.follow(a))

  def draw: String =
    val xs = r.map(_.x).distinct
    val ys = r.map(_.y).distinct
    val grid =
      for y <- (ys.min to ys.max).reverse
      yield for x <- xs.min to xs.max
      yield r.indexOf(Pos2D(x, y)) match
        case -1 => '.'
        case 0  => 'H'
        case i  => i.toString.head
    "\n" + grid.map(_.mkString).mkString("\n")

object Rope:
  def apply(size: Int): Rope = List.fill(size)(Pos2D(0, 0))

final case class Move(dir: Direction, size: Int):
  def steps: Vector[Direction] = Vector.fill(size)(dir)

object Move:
  def fromString(str: String): Option[Move] =
    "(U|D|L|R) (\\d+)".r
      .findPrefixMatchOf(str)
      .map(m => Move(Direction.valueOf(m.group(1)), m.group(2).toInt))

def ropePath(ms: Seq[Move], size: Int): Seq[Rope] =
  ms.flatMap(_.steps).scanLeft(Rope(size))((r, d) => r.nudge(d))

// example
val exLines = io.Source.fromFile("examples/day09small.txt").getLines.toList
val exMoves = exLines.flatMap(Move.fromString)
assert(exLines.size == exMoves.size)
val exPath = ropePath(exMoves, 2)
exPath.map(_.last).toSet.size

// part one answer
val lines = io.Source.fromFile("input/day09.txt").getLines.toList
val moves = lines.flatMap(Move.fromString)
assert(lines.size == moves.size)
val path = ropePath(moves, 2)
path.map(_.last).toSet.size

// example
val exPath2 = ropePath(exMoves, 10)
exPath2.last.draw
exPath.map(_.last).toSet.size

// larger example
val ex2Lines = io.Source.fromFile("examples/day09large.txt").getLines.toList
val ex2Moves = ex2Lines.flatMap(Move.fromString)
val ex2Path = ropePath(ex2Moves, 10)
ex2Path.last.draw
ex2Path.map(_.last).toSet.size

// part two answer
ropePath(moves, 10).map(_.last).toSet.size
