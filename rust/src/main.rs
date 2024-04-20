use core::panic;
use std::env;
use std::fs;

pub mod day1;
pub mod day2;

fn main() {
    // parse CLI args
    let args: Vec<String> = env::args().collect();
    if args.len() < 2 {
        panic!("missing argument: day")
    }
    let day: usize = args[1]
        .parse()
        .expect("day argument should be a valid integer");

    // read input
    let input =
        fs::read_to_string(format!("../input/day{:02}.txt", day)).expect("file should be present");

    let solutions: Vec<(fn(&str) -> i64, fn(&str) -> i64)> = vec![
        (day1::solve_part1, day1::solve_part2),
        (day2::solve_part1, day2::solve_part2),
    ];

    if let Some((part1, part2)) = solutions.get(day - 1) {
        println!("{}", part1(&input));
        println!("{}", part2(&input));
    } else {
        println!("day not yet implemented");
    }
}
