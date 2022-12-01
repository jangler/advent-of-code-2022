val input = io.Source.fromFile("input/day01.txt")
val lines = input.getLines.toList
val caloriesPerElf = lines.foldLeft(Seq(0))((acc, line) =>
  if line.isEmpty then 0 +: acc
  else (acc.head + line.toInt) +: acc.tail
).reverse

// check the head and tail of list against the input data
lines.take(8).map(_.toInt).sum == caloriesPerElf.head
lines.takeRight(15).map(_.toInt).sum == caloriesPerElf.last

// part one answer:
caloriesPerElf.max