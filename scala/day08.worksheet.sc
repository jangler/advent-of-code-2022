type TreeMap = Vector[Vector[Int]]

def parseMap(lines: Seq[String]): TreeMap =
  lines.toVector.map(_.toVector.map(_.toString.toInt))

// Returns the orthgonal lines of other trees between a given tree and the edges
// of the map. Each line is ordered from nearest to farthest from the given
// tree.
def orthogonalsFromTree(rows: TreeMap, x: Int, y: Int): Seq[Seq[Int]] =
  val columns = rows.transpose
  val left = rows(y).take(x).reverse
  val right = rows(y).drop(x + 1)
  val up = columns(x).take(y).reverse
  val down = columns(x).drop(y + 1)
  Seq(up, left, right, down)

def isTreeVisible(rows: TreeMap, x: Int, y: Int): Boolean =
  val height = rows(y)(x)
  orthogonalsFromTree(rows, x, y).exists(_.forall(_ < height))

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

def scenicScore(rows: TreeMap, x: Int, y: Int): Int =
  val height = rows(y)(x)
  def lineScore(line: Seq[Int]): Int =
    line.indexWhere(_ >= height) match
      case -1 => line.size
      case i  => i + 1
  orthogonalsFromTree(rows, x, y).map(lineScore).product

def maxScenicScore(map: TreeMap): Int =
  matrixIndices(map).map((x, y) => scenicScore(map, x, y)).max

// example
scenicScore(exMap, 2, 1)
scenicScore(exMap, 2, 3)
maxScenicScore(exMap)

// part two answer
maxScenicScore(map)
