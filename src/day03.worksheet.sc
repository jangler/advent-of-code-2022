type Item = Char

object Item:
  def seqFromString(s: String): Seq[Item] = for c <- s yield c

def priority(i: Item): Int =
  1 + ('a' to 'z').concat('A' to 'Z').indexOf(i)

case class Rucksack(first: Seq[Item], second: Seq[Item]):
  def commonItem: Option[Item] =
    first.toSet.intersect(second.toSet).headOption
  
  def all: Seq[Item] = first ++ second

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
val rucksacks = inputLines.map(Rucksack.fromString(_))
rucksacks.map(r => priority(r.commonItem.get)).sum

def badge(group: Seq[Rucksack]): Option[Item] =
  group.map(_.all.toSet).reduce(_ intersect _).headOption

def badges(rs: Seq[Rucksack]): Seq[Option[Item]] =
  rs.grouped(3).map(badge).toList

// example
badges(exampleRucksacks)

// part two answer
badges(rucksacks).map(o => priority(o.get)).sum