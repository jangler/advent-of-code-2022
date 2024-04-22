fn common_item(xs: &[&str]) -> char {
    xs[0]
        .chars()
        .find(|c| xs[1..].iter().all(|s| s.contains(*c)))
        .expect("should find common item")
}

fn common_rucksack_item(rucksack: &str) -> char {
    let midpoint = rucksack.len() / 2;
    common_item(&[&rucksack[..midpoint], &rucksack[midpoint..]])
}

fn priority(item: char) -> i64 {
    if item >= 'a' && item <= 'z' {
        item as i64 - 'a' as i64 + 1
    } else if item >= 'A' && item <= 'z' {
        item as i64 - 'A' as i64 + 27
    } else {
        panic!("invalid item");
    }
}

pub fn solve_part1(input: &str) -> String {
    input
        .lines()
        .map(|s| priority(common_rucksack_item(s)))
        .sum::<i64>()
        .to_string()
}

pub fn solve_part2(input: &str) -> String {
    let lines: Vec<&str> = input.lines().collect();
    (0..lines.len() / 3)
        .map(|i| priority(common_item(&lines[i * 3..(i + 1) * 3])))
        .sum::<i64>()
        .to_string()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day3::{solve_part1, solve_part2};

    fn example_input() -> String {
        fs::read_to_string("../examples/day03.txt").expect("example should be present")
    }

    #[test]
    fn test_part1() {
        assert_eq!(solve_part1(&example_input()), "157");
    }

    #[test]
    fn test_part2() {
        assert_eq!(solve_part2(&example_input()), "70");
    }
}
