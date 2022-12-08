type TreeMap = Vector[Vector[Int]]

def parseMap(lines: Seq[String]): TreeMap =
  lines.toVector.map(_.toVector.map(_.toInt))

def isTreeVisible(rows: TreeMap, x: Int, y: Int): Boolean =
  val columns = rows.transpose
  val left = rows(y).take(x)
  val right = rows(y).drop(x + 1)
  val top = columns(x).take(y)
  val bottom = columns(x).drop(y + 1)
  val linesToEdges = Seq(left, right, top, bottom)
  val height = rows(y)(x)
  linesToEdges.exists(_.forall(_ < height))

def matrixIndices[A](mat: Seq[Seq[A]]): Seq[(Int, Int)] =
  for
    y <- mat.indices
    x <- mat(y).indices
  yield (x, y)

// example
val exLines = io.Source.fromFile("examples/day08.txt").getLines.toList
val exMap = parseMap(exLines)
val exIndices = matrixIndices(exMap)
exIndices.count((x, y) => isTreeVisible(exMap, x, y))

// part one answer
var lines = io.Source.fromFile("input/day08.txt").getLines.toList
val map = parseMap(lines)
matrixIndices(map).count((x, y) => isTreeVisible(map, x, y))
