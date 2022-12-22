import { slurpLines } from './input.js';
import * as R from 'ramda';

function compartments(rucksack: string): [string, string] {
    const halfway = rucksack.length / 2;
    return [rucksack.slice(0, halfway), rucksack.slice(halfway)];
}

function commonChar(strs: string[]): string {
    for (const char of strs[0]) {
        if (strs.slice(1).every((s) => s.includes(char))) {
            return char;
        }
    }
    throw Error(`no common char in strings: ${strs}`)
}

function commonItem(rucksack: string): string {
    return commonChar(compartments(rucksack));
}

function priority(char: string): number {
    if (char >= 'a' && char <= 'z') {
        return 1 + char.charCodeAt(0) - 'a'.charCodeAt(0);
    } else if (char >= 'A' && char <= 'Z') {
        return 27 + char.charCodeAt(0) - 'A'.charCodeAt(0);
    }
    throw Error(`no priority for character: ${char}`)
}

async function partOneSolution(path: string): Promise<number> {
    const lines = await slurpLines(path);
    const commonItems = lines.map(commonItem);
    return R.sum(commonItems.map(priority));
}

async function partTwoSolution(path: string): Promise<number> {
    const lines = await slurpLines(path);
    const groups = R.splitEvery(3, lines);
    const commonItems = groups.map(commonChar);
    return R.sum(commonItems.map(priority));
}

async function main() {
    const examplePath = '../examples/day03.txt';
    const inputPath = '../input/day03.txt';

    console.assert(await partOneSolution(examplePath) === 157);
    console.log(`part one: ${await partOneSolution(inputPath)}`);

    console.assert(await partTwoSolution(examplePath) === 70);
    console.log(`part two: ${await partTwoSolution(inputPath)}`);
}

main();