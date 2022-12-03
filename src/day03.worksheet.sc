type Item = Char

object Item:
  def seqFromString(s: String): Seq[Item] = for c <- s yield c

def priority(i: Item): Int =
  1 + ('a' to 'z').concat('A' to 'Z').indexOf(i)

case class Rucksack(first: Seq[Item], second: Seq[Item]):
  def commonItem: Option[Item] =
    first.toSet.intersect(second.toSet).headOption

object Rucksack:
  def fromString(s: String): Rucksack =
    val halves = s.splitAt(s.size / 2)
    Rucksack(Item.seqFromString(halves(0)), Item.seqFromString(halves(1)))

// example
var exampleLines = List(
  "vJrwpWtwJgWrhcsFMMfFFhFp",
  "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
  "PmmdzqPrVvPwwTWBwg",
  "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
  "ttgJtRGJQctTZtZT",
  "CrZsJsPPZsGzwwsLwLmpwMDw"
)
val exampleRucksacks = exampleLines.map(Rucksack.fromString)
val exampleCommonItems = exampleRucksacks.flatMap(_.commonItem)
exampleCommonItems.map(priority)

// part one answer
val inputLines = io.Source.fromFile("input/day03.txt").getLines.toList
inputLines.map(line => priority(Rucksack.fromString(line).commonItem.get)).sum
