import { assert } from 'node:console';
import { open } from 'node:fs/promises';

async function slurpLines(path: string): Promise<string[]> {
    const file = await open(path);
    const lines = [];
    for await (const line of file.readLines()) {
        lines.push(line);
    }
    return lines;
}

function parseElves(lines: string[]): number[][] {
    const elves: number[][] = [[]];
    for (const line of lines) {
        if (line == '') {
            elves.push([]);
        } else {
            elves[elves.length - 1].push(parseInt(line));
        }
    }
    return elves;
}

function sum(xs: number[]): number {
    return xs.reduce((pv, cv, _ci, _a) => pv + cv);
}

function max(xs: number[]): number {
    return xs.reduce((pv, cv, _ci, _a) => cv > pv ? cv : pv);
}

function top(n: number, xs: number[]): number[] {
    return [...xs].sort((a, b) => a - b).slice(-n);
}

async function partOneSolution(path: string): Promise<number> {
    const lines = await slurpLines(path);
    const elves = parseElves(lines);
    return max(elves.map(sum));
}

async function partTwoSolution(path: string): Promise<number> {
    const lines = await slurpLines(path);
    const elves = parseElves(lines);
    return sum(top(3, elves.map(sum)));
}

async function main() {
    const examplePath = '../examples/day01.txt';
    const inputPath = '../input/day01.txt';

    assert(await partOneSolution(examplePath) == 24000);
    console.log(`part one: ${await partOneSolution(inputPath)}`);

    assert(await partTwoSolution(examplePath) == 45000);
    console.log(`part two: ${await partTwoSolution(inputPath)}`);
}

main();