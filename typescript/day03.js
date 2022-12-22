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
function compartments(rucksack) {
    const halfway = rucksack.length / 2;
    return [rucksack.slice(0, halfway), rucksack.slice(halfway)];
}
function commonChar(strs) {
    for (const char of strs[0]) {
        if (strs.slice(1).every((s) => s.includes(char))) {
            return char;
        }
    }
    throw Error(`no common char in strings: ${strs}`);
}
function commonItem(rucksack) {
    return commonChar(compartments(rucksack));
}
function priority(char) {
    if (char >= 'a' && char <= 'z') {
        return 1 + char.charCodeAt(0) - 'a'.charCodeAt(0);
    }
    else if (char >= 'A' && char <= 'Z') {
        return 27 + char.charCodeAt(0) - 'A'.charCodeAt(0);
    }
    throw Error(`no priority for character: ${char}`);
}
function partOneSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const lines = yield slurpLines(path);
        const commonItems = lines.map(commonItem);
        return R.sum(commonItems.map(priority));
    });
}
function partTwoSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const lines = yield slurpLines(path);
        const groups = R.splitEvery(3, lines);
        const commonItems = groups.map(commonChar);
        return R.sum(commonItems.map(priority));
    });
}
function main() {
    return __awaiter(this, void 0, void 0, function* () {
        const examplePath = '../examples/day03.txt';
        const inputPath = '../input/day03.txt';
        console.assert((yield partOneSolution(examplePath)) === 157);
        console.log(`part one: ${yield partOneSolution(inputPath)}`);
        console.assert((yield partTwoSolution(examplePath)) === 70);
        console.log(`part two: ${yield partTwoSolution(inputPath)}`);
    });
}
main();
