import { assert } from 'node:console';
import { slurpLines } from './input.js';
import * as R from 'ramda';

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

function top(n: number, xs: number[]): number[] {
    return [...xs].sort((a, b) => a - b).slice(-n);
}

async function partOneSolution(path: string): Promise<number> {
    const lines = await slurpLines(path);
    const elves = parseElves(lines);
    return Math.max(...elves.map(R.sum));
}

async function partTwoSolution(path: string): Promise<number> {
    const lines = await slurpLines(path);
    const elves = parseElves(lines);
    return R.sum(top(3, elves.map(R.sum)));
}

async function main() {
    const examplePath = '../examples/day01.txt';
    const inputPath = '../input/day01.txt';

    assert(await partOneSolution(examplePath) === 24000);
    console.log(`part one: ${await partOneSolution(inputPath)}`);

    assert(await partTwoSolution(examplePath) === 45000);
    console.log(`part two: ${await partTwoSolution(inputPath)}`);
}

main();