var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { assert } from 'node:console';
import { slurpLines } from './input.js';
import * as R from 'ramda';
function parseElves(lines) {
    const elves = [[]];
    for (const line of lines) {
        if (line == '') {
            elves.push([]);
        }
        else {
            elves[elves.length - 1].push(parseInt(line));
        }
    }
    return elves;
}
function top(n, xs) {
    return [...xs].sort((a, b) => a - b).slice(-n);
}
function partOneSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const lines = yield slurpLines(path);
        const elves = parseElves(lines);
        return Math.max(...elves.map(R.sum));
    });
}
function partTwoSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const lines = yield slurpLines(path);
        const elves = parseElves(lines);
        return R.sum(top(3, elves.map(R.sum)));
    });
}
function main() {
    return __awaiter(this, void 0, void 0, function* () {
        const examplePath = '../examples/day01.txt';
        const inputPath = '../input/day01.txt';
        assert((yield partOneSolution(examplePath)) === 24000);
        console.log(`part one: ${yield partOneSolution(inputPath)}`);
        assert((yield partTwoSolution(examplePath)) === 45000);
        console.log(`part two: ${yield partTwoSolution(inputPath)}`);
    });
}
main();
