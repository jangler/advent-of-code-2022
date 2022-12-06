val examples = Seq(
  "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
  "bvwbjplbgvbhsrlpgdmjqwftvncz",
  "nppdvjthqldpwncqszvftbrmjlhg",
  "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
  "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" 
)

def packetMarkerIndex(s: String): Option[Int] =
  val windowSize = 4
  s.sliding(windowSize).indexWhere(_.distinct.size == windowSize) match
    case -1 => None
    case i => Some(i + windowSize)

examples.map(packetMarkerIndex)

val input = io.Source.fromFile("input/day06.txt").mkString

// part one answer
packetMarkerIndex(input)
