val examples = Seq(
  "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
  "bvwbjplbgvbhsrlpgdmjqwftvncz",
  "nppdvjthqldpwncqszvftbrmjlhg",
  "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
  "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
)

def markerIndex(s: String, windowSize: Int): Option[Int] =
  s.sliding(windowSize).indexWhere(_.distinct.size == windowSize) match
    case -1 => None
    case i  => Some(i + windowSize)

def packetMarkerIndex(s: String): Option[Int] = markerIndex(s, 4)

examples.map(packetMarkerIndex)

val input = io.Source.fromFile("input/day06.txt").mkString

// part one answer
packetMarkerIndex(input)

def messageMarkerIndex(s: String): Option[Int] = markerIndex(s, 14)

examples.map(messageMarkerIndex)

// part two answer
messageMarkerIndex(input)
