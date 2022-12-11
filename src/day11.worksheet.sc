final case class MonkeyAttributes(
    index: Int,
    startingItems: Seq[Int],
    operation: Operation,
    divisibleByTest: Int,
    trueDst: Int,
    falseDst: Int
)

object MonkeyAttributes:
  def parse(lines: Seq[String]): MonkeyAttributes =
    val regexes = Seq(
      "Monkey (\\d+):".r,
      "  Starting items: ((?:\\d+(?:, )?)+)".r,
      "  Operation: (new = old . (?:\\d+|old))".r,
      "  Test: divisible by (\\d+)".r,
      "    If true: throw to monkey (\\d+)".r,
      "    If false: throw to monkey (\\d+)".r
    )
    val matches = regexes
      .zip(lines)
      .map((r, s) =>
        r.findPrefixMatchOf(s)
          .getOrElse(throw Exception(s"no match for: $s"))
          .group(1)
      )
    MonkeyAttributes(
      index = matches(0).toInt,
      startingItems = matches(1).split(", ").map(_.toInt).toSeq,
      operation = Operation.parse(matches(2)),
      divisibleByTest = matches(3).toInt,
      trueDst = matches(4).toInt,
      falseDst = matches(5).toInt
    )

enum Operation(val f: Int => Int):
  case MultiplyBy(n: Int) extends Operation(_ * n)
  case Add(n: Int) extends Operation(_ + n)
  case Square extends Operation(x => x * x)

object Operation:
  def parse(s: String): Operation =
    s.split(" ").toList.drop(2) match
      case "old" :: "*" :: "old" :: Nil => Square
      case "old" :: "*" :: n :: Nil     => MultiplyBy(n.toInt)
      case "old" :: "+" :: n :: Nil     => Add(n.toInt)
      case _ => throw Exception(s"unknown operation: $s")

final class Monkey(val attributes: MonkeyAttributes):
  var items = attributes.startingItems.toList
  var inspectedItems = 0

  override def toString: String =
    s"Monkey(${attributes.index}, $items, $inspectedItems)"

  def doRound(ms: IndexedSeq[Monkey]): Unit =
    for item <- items do
      val newLevel = attributes.operation.f(item) / 3
      val dst =
        if newLevel % attributes.divisibleByTest == 0 then attributes.trueDst
        else attributes.falseDst
      ms(dst).items = ms(dst).items :+ newLevel
    inspectedItems += items.size
    items = Nil

def doRound(ms: IndexedSeq[Monkey]): Unit =
  ms.foreach(_.doRound(ms))

def monkeyBusinessLevel(ms: Seq[Monkey]): Int =
  ms.map(_.inspectedItems).sorted.takeRight(2).product

def readLines(path: String): Seq[String] =
  io.Source.fromFile(path).getLines.toSeq

// example
val exLines = readLines("examples/day11.txt")
val exAttrs = exLines.grouped(7).map(MonkeyAttributes.parse).toVector
val exMonkeys = exAttrs.map(Monkey(_))
for i <- 1 to 20 do doRound(exMonkeys)
monkeyBusinessLevel(exMonkeys)

// part one answer
val lines = readLines("input/day11.txt")
val attrs = lines.grouped(7).map(MonkeyAttributes.parse).toVector
val monkeys = attrs.map(Monkey(_))
for i <- 1 to 20 do doRound(monkeys)
monkeyBusinessLevel(monkeys)
