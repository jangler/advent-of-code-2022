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

final case class Rope(head: Pos2D, tail: Pos2D):
  def nudge(d: Direction): Rope =
    val newHead = head.nudge(d)
    Rope(newHead, tail.follow(newHead))

object Rope:
  def start: Rope = Rope(Pos2D(0, 0), Pos2D(0, 0))

final case class Move(dir: Direction, size: Int):
  def steps: Vector[Direction] = Vector.fill(size)(dir)

object Move:
  def fromString(str: String): Option[Move] =
    "(U|D|L|R) (\\d+)".r
      .findPrefixMatchOf(str)
      .map(m => Move(Direction.valueOf(m.group(1)), m.group(2).toInt))

def ropePath(ms: Seq[Move]): Seq[Rope] =
  ms.flatMap(_.steps).scanLeft(Rope.start)((r, d) => r.nudge(d))

// example
val exLines = io.Source.fromFile("examples/day09.txt").getLines.toList
val exMoves = exLines.flatMap(Move.fromString)
assert(exLines.size == exMoves.size)
val exPath = ropePath(exMoves)
exPath.map(_.tail).toSet.size

// part one answer
val lines = io.Source.fromFile("input/day09.txt").getLines.toList
val moves = lines.flatMap(Move.fromString)
assert(lines.size == moves.size)
val path = ropePath(moves)
path.map(_.tail).toSet.size
