enum Outcome {
    Win,
    Lose,
    Draw,
}

#[derive(Copy, Clone)]
enum Shape {
    Rock,
    Paper,
    Scissors,
}

impl Shape {
    fn score(self: &Self) -> i64 {
        match self {
            Shape::Rock => 1,
            Shape::Paper => 2,
            Shape::Scissors => 3,
        }
    }

    fn beats(self: &Self, other: &Self) -> bool {
        match (self, other) {
            (Shape::Rock, Shape::Scissors)
            | (Shape::Paper, Shape::Rock)
            | (Shape::Scissors, Shape::Paper) => true,
            _ => false,
        }
    }

    fn with_outcome(other: &Self, outcome: Outcome) -> Self {
        match outcome {
            Outcome::Win => [Shape::Rock, Shape::Paper, Shape::Scissors]
                .into_iter()
                .find(|s| s.beats(other))
                .expect("should be able to find a winning shape"),
            Outcome::Lose => [Shape::Rock, Shape::Paper, Shape::Scissors]
                .into_iter()
                .find(|s| other.beats(s))
                .expect("should be able to find a losing shape"),
            Outcome::Draw => *other,
        }
    }
}

struct Round {
    p1: Shape,
    p2: Shape,
}

impl Round {
    fn parse_part1(line: &str) -> Self {
        let chars: Vec<char> = line.chars().collect();
        Round {
            p1: match chars[2] {
                'X' => Shape::Rock,
                'Y' => Shape::Paper,
                'Z' => Shape::Scissors,
                _ => panic!("could not parse round"),
            },
            p2: match chars[0] {
                'A' => Shape::Rock,
                'B' => Shape::Paper,
                'C' => Shape::Scissors,
                _ => panic!("could not parse round"),
            },
        }
    }

    fn parse_part2(line: &str) -> Self {
        let chars: Vec<char> = line.chars().collect();
        let p2 = match chars[0] {
            'A' => Shape::Rock,
            'B' => Shape::Paper,
            'C' => Shape::Scissors,
            _ => panic!("could not parse round"),
        };
        Round {
            p1: match chars[2] {
                'X' => Shape::with_outcome(&p2, Outcome::Lose),
                'Y' => Shape::with_outcome(&p2, Outcome::Draw),
                'Z' => Shape::with_outcome(&p2, Outcome::Win),
                _ => panic!("could not parse round"),
            },
            p2: p2,
        }
    }

    fn score(self: &Self) -> i64 {
        let outcome_score = if self.p1.beats(&self.p2) {
            6
        } else if self.p2.beats(&self.p1) {
            0
        } else {
            3
        };
        self.p1.score() + outcome_score
    }
}

fn parse_part1(input: &str) -> Vec<Round> {
    input.lines().map(|s| Round::parse_part1(s)).collect()
}

pub fn solve_part1(input: &str) -> i64 {
    parse_part1(input).iter().map(|r| r.score()).sum()
}

fn parse_part2(input: &str) -> Vec<Round> {
    input.lines().map(|s| Round::parse_part2(s)).collect()
}

pub fn solve_part2(input: &str) -> i64 {
    parse_part2(input).iter().map(|r| r.score()).sum()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day2::{solve_part1, solve_part2};

    fn example_input() -> String {
        fs::read_to_string("../examples/day02.txt").expect("example should be present")
    }

    #[test]
    fn test_part1() {
        assert_eq!(solve_part1(&example_input()), 15);
    }

    #[test]
    fn test_part2() {
        assert_eq!(solve_part2(&example_input()), 12);
    }
}
