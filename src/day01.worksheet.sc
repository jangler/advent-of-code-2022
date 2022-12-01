val input = io.Source.fromFile("input/day01.txt")
val lines = input.getLines.toList
val caloriesPerElf = lines
  .foldLeft(List(0))((acc, line) =>
    if line.isEmpty then 0 :: acc
    else (acc.head + line.toInt) :: acc.tail
  )
  .reverse

// check the head and tail of list against the input data
def firstElf(xs: Seq[String]): Int = xs.takeWhile(_.nonEmpty).map(_.toInt).sum
firstElf(lines) == caloriesPerElf.head
firstElf(lines.reverse) == caloriesPerElf.last

// part one answer:
caloriesPerElf.max

// part two answer:
caloriesPerElf.sorted.takeRight(3).sum