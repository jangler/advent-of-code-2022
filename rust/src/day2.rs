enum Shape {
    Rock,
    Paper,
    Scissors,
}

impl Shape {
    fn score(self: &Self) -> i64 {
        return match self {
            Shape::Rock => 1,
            Shape::Paper => 2,
            Shape::Scissors => 3,
        };
    }

    fn beats(self: &Self, other: &Self) -> bool {
        return match (self, other) {
            (Shape::Rock, Shape::Scissors)
            | (Shape::Paper, Shape::Rock)
            | (Shape::Scissors, Shape::Paper) => true,
            _ => false,
        };
    }
}

struct Round {
    p1: Shape,
    p2: Shape,
}

impl Round {
    fn parse(line: &str) -> Self {
        let chars: Vec<char> = line.chars().collect();
        return Round {
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
        };
    }

    fn score(self: &Self) -> i64 {
        let outcome_score = if self.p1.beats(&self.p2) {
            6
        } else if self.p2.beats(&self.p1) {
            0
        } else {
            3
        };
        return self.p1.score() + outcome_score;
    }
}

fn parse(input: &str) -> Vec<Round> {
    return input.lines().map(|s| Round::parse(s)).collect();
}

pub fn solve_part1(input: &str) -> i64 {
    return parse(input).iter().map(|r| r.score()).sum();
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day2::solve_part1;

    fn example_input() -> String {
        return fs::read_to_string("../examples/day02.txt").expect("example should be present");
    }

    #[test]
    fn test_part1() {
        assert_eq!(solve_part1(&example_input()), 15);
    }
}
