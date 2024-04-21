use std::ops::RangeInclusive;

use regex::Regex;

#[derive(Debug)]
struct Pair {
    first: RangeInclusive<i64>,
    second: RangeInclusive<i64>,
}

impl Pair {
    fn has_overlap(self: &Self) -> bool {
        self.first.start() <= self.second.end() && self.first.end() >= self.second.start()
    }

    fn has_full_overlap(self: &Self) -> bool {
        (self.first.start() <= self.second.start() && self.first.end() >= self.second.end())
            || (self.second.start() <= self.first.start() && self.second.end() >= self.first.end())
    }
}

fn parse(input: &str) -> Vec<Pair> {
    let re = Regex::new(r"(\d+)-(\d+),(\d+)-(\d+)").unwrap();
    input
        .lines()
        .map(|s| {
            let ints: Vec<i64> = re
                .captures(s)
                .unwrap()
                .iter()
                .skip(1)
                .map(|s| s.unwrap().as_str().parse().unwrap())
                .collect();
            Pair {
                first: ints[0]..=ints[1],
                second: ints[2]..=ints[3],
            }
        })
        .collect()
}

pub fn solve_part1(input: &str) -> i64 {
    parse(input).iter().filter(|p| p.has_full_overlap()).count() as i64
}

pub fn solve_part2(input: &str) -> i64 {
    parse(input).iter().filter(|p| p.has_overlap()).count() as i64
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day4::{solve_part1, solve_part2};

    fn example_input() -> String {
        fs::read_to_string("../examples/day04.txt").expect("example should be present")
    }

    #[test]
    fn test_part1() {
        assert_eq!(solve_part1(&example_input()), 2);
    }

    #[test]
    fn test_part2() {
        assert_eq!(solve_part2(&example_input()), 4);
    }
}
