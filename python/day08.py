from math import prod
from typing import Iterable

Grid = list[list[int]]


def slurp_lines(path: str) -> list[str]:
    with open(path) as f:
        return [line.strip() for line in f.readlines()]


def parse(lines: list[str]) -> Grid:
    return [[int(char) for char in line]
            for line in lines]


def lines_to_edge(x: int, y: int, grid: Grid) -> list[list[int]]:
    row = grid[y]
    col = [row[x] for row in grid]
    return [list(reversed(row[:x])),
            row[x+1:],
            list(reversed(col[:y])),
            col[y+1:]]


def visible(x: int, y: int, grid: Grid) -> bool:
    lines = lines_to_edge(x, y, grid)
    ref_height = grid[y][x]
    return any(all(height < ref_height for height in line)
               for line in lines)


def indices(grid: Grid) -> Iterable[tuple[int, int]]:
    for y in range(len(grid)):
        for x in range(len(grid)):
            yield x, y


def visible_count(grid: Grid) -> int:
    return sum(1 for x, y in indices(grid) if visible(x, y, grid))


def viewing_distance(line: list[int], ref_height: int) -> int:
    for i, x in enumerate(line):
        if x >= ref_height:
            return i + 1
    return len(line)


def scenic_score(x: int, y: int, grid: Grid) -> int:
    ref_height = grid[y][x]
    return prod(viewing_distance(line, ref_height)
                for line in lines_to_edge(x, y, grid))


def max_scenic_score(grid: Grid) -> int:
    return max(scenic_score(x, y, grid) for x, y in indices(grid))


def solutions() -> tuple[int, int]:
    input = parse(slurp_lines('../input/day08.txt'))
    return visible_count(input), max_scenic_score(input)
