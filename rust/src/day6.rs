use std::collections::HashSet;

const PACKET_MARKER_LENGTH: usize = 4;
const MESSAGE_MARKER_LENGTH: usize = 14;

fn marker_index(input: &str, length: usize) -> usize {
    for i in length..input.len() {
        let chars: HashSet<char> = HashSet::from_iter(input[i-length..i].chars());
        if chars.len() == length {
            return i;
        }
    }
    panic!("could not find marker index");
}

pub fn solve_part1(input: &str) -> String {
    marker_index(input, PACKET_MARKER_LENGTH).to_string()
}

pub fn solve_part2(input: &str) -> String {
    marker_index(input, MESSAGE_MARKER_LENGTH).to_string()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day6::{solve_part1, solve_part2};

    fn examples() -> Vec<String> {
        fs::read_to_string("../examples/day06.txt")
            .expect("example should be present")
            .lines()
            .map(String::from)
            .collect()
    }

    #[test]
    fn test_part1() {
        let results = ["7", "5", "6", "10", "11"];
        for (i, example) in examples().iter().enumerate() {
            assert_eq!(solve_part1(&example), results[i]);
        }
    }

    #[test]
    fn test_part2() {
        let results = ["19", "23", "23", "29", "26"];
        for (i, example) in examples().iter().enumerate() {
            assert_eq!(solve_part2(&example), results[i]);
        }
    }
}
