var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { slurpLines } from './input.js';
import * as R from 'ramda';
const yourShapeEncoding = new Map([
    ['A', 0 /* Shape.Rock */],
    ['B', 1 /* Shape.Paper */],
    ['C', 2 /* Shape.Scissors */],
]);
const myShapeEncoding = new Map([
    ['X', 0 /* Shape.Rock */],
    ['Y', 1 /* Shape.Paper */],
    ['Z', 2 /* Shape.Scissors */],
]);
const shapeScores = new Map([
    [0 /* Shape.Rock */, 1],
    [1 /* Shape.Paper */, 2],
    [2 /* Shape.Scissors */, 3],
]);
const outcomeEncoding = new Map([
    ['X', 0 /* Outcome.Lose */],
    ['Y', 1 /* Outcome.Draw */],
    ['Z', 2 /* Outcome.Win */],
]);
const outcomeScores = new Map([
    [0 /* Outcome.Lose */, 0],
    [1 /* Outcome.Draw */, 3],
    [2 /* Outcome.Win */, 6],
]);
function parseRoundPartOne(line) {
    return [
        yourShapeEncoding.get(line.charAt(0)),
        myShapeEncoding.get(line.charAt(line.length - 1)),
    ];
}
function parseRoundPartTwo(line) {
    return [
        yourShapeEncoding.get(line.charAt(0)),
        outcomeEncoding.get(line.charAt(line.length - 1)),
    ];
}
const winningRounds = [
    [0 /* Shape.Rock */, 1 /* Shape.Paper */],
    [1 /* Shape.Paper */, 2 /* Shape.Scissors */],
    [2 /* Shape.Scissors */, 0 /* Shape.Rock */],
];
function convertRound(round) {
    if (round[1] === 2 /* Outcome.Win */) {
        return [round[0], winningRounds.find((x) => x[0] === round[0])[1]];
    }
    else if (round[1] === 0 /* Outcome.Lose */) {
        return [round[0], winningRounds.find((x) => x[1] === round[0])[0]];
    }
    return [round[0], round[0]];
}
function outcomeOf(round) {
    if (R.includes(round, winningRounds)) {
        return 2 /* Outcome.Win */;
    }
    else if (round[0] === round[1]) {
        return 1 /* Outcome.Draw */;
    }
    return 0 /* Outcome.Lose */;
}
function totalScore(round) {
    return shapeScores.get(round[1]) + outcomeScores.get(outcomeOf(round));
}
function partOneSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const rounds = (yield slurpLines(path)).map(parseRoundPartOne);
        const scores = rounds.map(totalScore);
        return R.sum(scores);
    });
}
function partTwoSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const rounds = (yield slurpLines(path)).map(parseRoundPartTwo);
        const scores = rounds.map((x) => totalScore(convertRound(x)));
        console.log(scores);
        return R.sum(scores);
    });
}
function main() {
    return __awaiter(this, void 0, void 0, function* () {
        const examplePath = '../examples/day02.txt';
        const inputPath = '../input/day02.txt';
        console.assert((yield partOneSolution(examplePath)) === 15);
        console.log(`part one: ${yield partOneSolution(inputPath)}`);
        console.assert((yield partTwoSolution(examplePath)) === 12);
        console.log(`part two: ${yield partTwoSolution(inputPath)}`);
    });
}
main();
