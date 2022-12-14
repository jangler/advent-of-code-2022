// Didn't like this one much.

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

enum Operation(val f: Long => Long):
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
  var items = attributes.startingItems.map(_.toLong).toList
  var inspectedItems = 0L

  override def toString: String =
    s"Monkey(${attributes.index}, $items, $inspectedItems)"

  def doRound(ms: IndexedSeq[Monkey], lcm: Int): Unit =
    for item <- items do
      val newLevel = attributes.operation.f(item) % lcm
      val dst =
        if newLevel % attributes.divisibleByTest == 0 then attributes.trueDst
        else attributes.falseDst
      ms(dst).items = ms(dst).items :+ newLevel
    inspectedItems += items.size
    items = Nil

def doRound(ms: IndexedSeq[Monkey], lcm: Int): Unit =
  ms.foreach(_.doRound(ms, lcm))

def monkeyBusinessLevel(ms: Seq[Monkey]): Long =
  ms.map(_.inspectedItems).sorted.takeRight(2).product

def readLines(path: String): Seq[String] =
  io.Source.fromFile(path).getLines.toSeq

def getLcm(xs: Seq[Int]): Int =
  LazyList.from(1).find(i => xs.forall(i % _ == 0)).get

// example
val exLines = readLines("examples/day11.txt")
val exAttrs = exLines.grouped(7).map(MonkeyAttributes.parse).toVector
val exMonkeys = exAttrs.map(Monkey(_))
val exLcm = getLcm(exAttrs.map(_.divisibleByTest))
for i <- 1 to 10000 do doRound(exMonkeys, exLcm)
monkeyBusinessLevel(exMonkeys)

// part two answer
// need to stop doing copy/paste, had an error because of it again
val lines = readLines("input/day11.txt")
val attrs = lines.grouped(7).map(MonkeyAttributes.parse).toVector
val monkeys = attrs.map(Monkey(_))
val lcm = getLcm(attrs.map(_.divisibleByTest))
for i <- 1 to 10000 do doRound(monkeys, lcm)
monkeyBusinessLevel(monkeys)
