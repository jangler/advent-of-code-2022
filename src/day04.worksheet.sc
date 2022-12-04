def rangeFromString(s: String): Option[Range] =
  "(\\d+)-(\\d+)".r
    .findPrefixMatchOf(s)
    .map(m => m.group(1).toInt to m.group(2).toInt)

type Pair = Tuple2[Range, Range]

def pairFromLine(s: String): Option[Pair] =
  val ranges = s.split(",").map(rangeFromString)
  if ranges.exists(_.isEmpty) then None
  else Some((ranges(0).get, ranges(1).get))

extension (p: Pair)
  def hasFullOverlap: Boolean =
    p(0).containsSlice(p(1)) || p(1).containsSlice(p(0))

  def hasPartialOverlap: Boolean = p(0).intersect(p(1)).nonEmpty

// example
val exampleLines = Seq(
  "2-4,6-8",
  "2-3,4-5",
  "5-7,7-9",
  "2-8,3-7",
  "6-6,4-6",
  "2-6,4-8"
)
val examplePairs = exampleLines.flatMap(pairFromLine)
examplePairs.count(_.hasFullOverlap)

val inputLines = io.Source.fromFile("input/day04.txt").getLines.toList
val pairs = inputLines.flatMap(pairFromLine)
assert(pairs.size == inputLines.size)

// part one answer
pairs.count(_.hasFullOverlap)

// example
examplePairs.count(_.hasPartialOverlap)

// part two answer
pairs.count(_.hasPartialOverlap)
