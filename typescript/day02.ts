import { slurpLines } from './input.js';
import * as R from 'ramda';

const enum Shape { Rock, Paper, Scissors }

const yourShapeEncoding = new Map([
    ['A', Shape.Rock],
    ['B', Shape.Paper],
    ['C', Shape.Scissors],
])

const myShapeEncoding = new Map([
    ['X', Shape.Rock],
    ['Y', Shape.Paper],
    ['Z', Shape.Scissors],
])

const shapeScores = new Map([
    [Shape.Rock, 1],
    [Shape.Paper, 2],
    [Shape.Scissors, 3],
])

const enum Outcome { Lose, Draw, Win }

const outcomeEncoding = new Map([
    ['X', Outcome.Lose],
    ['Y', Outcome.Draw],
    ['Z', Outcome.Win],
])

const outcomeScores = new Map([
    [Outcome.Lose, 0],
    [Outcome.Draw, 3],
    [Outcome.Win, 6],
])

function parseRoundPartOne(line: string): [Shape, Shape] {
    return [
        yourShapeEncoding.get(line.charAt(0))!,
        myShapeEncoding.get(line.charAt(line.length - 1))!,
    ];
}

function parseRoundPartTwo(line: string): [Shape, Outcome] {
    return [
        yourShapeEncoding.get(line.charAt(0))!,
        outcomeEncoding.get(line.charAt(line.length - 1))!,
    ];
}

const winningRounds = [
    [Shape.Rock, Shape.Paper],
    [Shape.Paper, Shape.Scissors],
    [Shape.Scissors, Shape.Rock],
];

function convertRound(round: [Shape, Outcome]): [Shape, Shape] {
    if (round[1] === Outcome.Win) {
        return [round[0], winningRounds.find((x) => x[0] === round[0])![1]];
    } else if (round[1] === Outcome.Lose) {
        return [round[0], winningRounds.find((x) => x[1] === round[0])![0]];
    }
    return [round[0], round[0]];
}

function outcomeOf(round: [Shape, Shape]): Outcome {
    if (R.includes(round, winningRounds)) {
        return Outcome.Win;
    } else if (round[0] === round[1]) {
        return Outcome.Draw;
    }
    return Outcome.Lose
}

function totalScore(round: [Shape, Shape]): number {
    return shapeScores.get(round[1])! + outcomeScores.get(outcomeOf(round))!;
}

async function partOneSolution(path: string): Promise<number> {
    const rounds = (await slurpLines(path)).map(parseRoundPartOne);
    const scores = rounds.map(totalScore);
    return R.sum(scores);
}

async function partTwoSolution(path: string): Promise<number> {
    const rounds = (await slurpLines(path)).map(parseRoundPartTwo);
    const scores = rounds.map((x) => totalScore(convertRound(x)));
    return R.sum(scores);
}

async function main() {
    const examplePath = '../examples/day02.txt';
    const inputPath = '../input/day02.txt';

    console.assert(await partOneSolution(examplePath) === 15);
    console.log(`part one: ${await partOneSolution(inputPath)}`);

    console.assert(await partTwoSolution(examplePath) === 12);
    console.log(`part two: ${await partTwoSolution(inputPath)}`);
}

main();