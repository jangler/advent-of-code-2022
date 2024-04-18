use std::fs;

pub mod day1;

fn main() {
    let input = fs::read_to_string("../input/day01.txt")
        .expect("file should be present");
    println!("{}", day1::solve_part1(&input));
    println!("{}", day1::solve_part2(&input));
}
