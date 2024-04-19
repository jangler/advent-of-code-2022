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
    let day: i64 = args[1]
        .parse()
        .expect("day argument should be a valid integer");

    // read input
    let input =
        fs::read_to_string(format!("../input/day{:02}.txt", day)).expect("file should be present");
    
    match day {
        1 => {
            println!("{}", day1::solve_part1(&input));
            println!("{}", day1::solve_part2(&input));
        },
        2 => {
            println!("{}", day2::solve_part1(&input));
        },
        _ => panic!("day not yet implemented"),
    }
}
