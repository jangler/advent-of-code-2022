pub fn parse_elves(input: &str) -> Vec<Vec<i64>> {
    let mut elves: Vec<Vec<i64>> = vec![];
    let mut cur_elf: Vec<i64> = vec![];
    for line in input.lines() {
        if line.is_empty() {
            elves.push(cur_elf);
            cur_elf = vec![];
        } else {
            cur_elf.push(line.parse().expect("should parse line"));
        }
    }
    if !cur_elf.is_empty() {
        elves.push(cur_elf);
    }
    return elves;
}

pub fn solve_part1(input: &str) -> i64 {
    return parse_elves(input).iter().map(|x| x.iter().sum()).max().unwrap();
}

pub fn solve_part2(input: &str) -> i64 {
    let mut sums = parse_elves(input).iter().map(|x| x.iter().sum()).collect::<Vec<i64>>();
    sums.sort();
    return sums.iter().rev().take(3).sum();
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day1::{solve_part1, solve_part2};

    fn example_input() -> String {
        return fs::read_to_string("../examples/day01.txt").expect("file should be present");
    }

    #[test]
    fn test_part1() {
        assert_eq!(solve_part1(&example_input()), 24000);
    }

    #[test]
    fn test_part2() {
        assert_eq!(solve_part2(&example_input()), 45000);
    }
}
