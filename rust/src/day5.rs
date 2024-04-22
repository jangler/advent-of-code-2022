use regex::Regex;

type Stack = Vec<char>;

#[derive(Debug)]
struct Move {
    count: i64,
    src_index: usize,
    dst_index: usize,
}

fn parse(input: &str) -> (Vec<Stack>, Vec<Move>) {
    let mut stacks: Vec<Stack> = Vec::new();
    let mut moves: Vec<Move> = Vec::new();
    let re = Regex::new(r"move (\d+) from (\d+) to (\d+)").unwrap();
    for line in input.lines() {
        if line.contains("[") {
            let stack_count = (line.len() + 1) / 4;
            let chars: Stack = line.chars().collect();
            for i in 0..stack_count {
                let c = chars[i * 4 + 1];
                if c != ' ' {
                    while stacks.len() <= i {
                        stacks.push(Vec::new());
                    }
                    stacks[i].push(c);
                }
            }
        } else if line.starts_with("move") {
            let caps = re.captures(line).unwrap();
            moves.push(Move {
                count: caps.get(1).unwrap().as_str().parse().unwrap(),
                src_index: caps.get(2).unwrap().as_str().parse().unwrap(),
                dst_index: caps.get(3).unwrap().as_str().parse().unwrap(),
            });
        }
    }
    for stack in &mut stacks {
        stack.reverse();
    }
    (stacks, moves)
}

enum Model {
    CrateMover9000,
    CrateMover9001,
}

fn rearrange(stacks: &mut Vec<Stack>, moves: &Vec<Move>, model: Model) {
    for m in moves {
        match model {
            Model::CrateMover9000 => {
                for _ in 0..m.count {
                    let c = stacks[m.src_index - 1].pop().unwrap();
                    stacks[m.dst_index - 1].push(c);
                }
            }
            Model::CrateMover9001 => {
                let src = &mut stacks[m.src_index - 1];
                let crates = &mut src.split_off(src.len() - m.count as usize);
                stacks[m.dst_index - 1].append(crates);
            }
        }
    }
}

fn top_crates(stacks: Vec<Stack>) -> String {
    stacks.iter().map(|v| v.last().unwrap()).collect::<String>()
}

pub fn solve_part1(input: &str) -> String {
    let (mut stacks, moves) = parse(input);
    rearrange(&mut stacks, &moves, Model::CrateMover9000);
    top_crates(stacks)
}

pub fn solve_part2(input: &str) -> String {
    let (mut stacks, moves) = parse(input);
    rearrange(&mut stacks, &moves, Model::CrateMover9001);
    top_crates(stacks)
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::day5::{solve_part1, solve_part2};

    fn example_input() -> String {
        fs::read_to_string("../examples/day05.txt").expect("example should be present")
    }

    #[test]
    fn test_part1() {
        assert_eq!(solve_part1(&example_input()), "CMZ");
    }

    #[test]
    fn test_part2() {
        assert_eq!(solve_part2(&example_input()), "MCD");
    }
}
