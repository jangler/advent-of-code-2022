var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __asyncValues = (this && this.__asyncValues) || function (o) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var m = o[Symbol.asyncIterator], i;
    return m ? m.call(o) : (o = typeof __values === "function" ? __values(o) : o[Symbol.iterator](), i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i);
    function verb(n) { i[n] = o[n] && function (v) { return new Promise(function (resolve, reject) { v = o[n](v), settle(resolve, reject, v.done, v.value); }); }; }
    function settle(resolve, reject, d, v) { Promise.resolve(v).then(function(v) { resolve({ value: v, done: d }); }, reject); }
};
import { assert } from 'node:console';
import { open } from 'node:fs/promises';
function slurpLines(path) {
    var _b, e_1, _c, _d;
    return __awaiter(this, void 0, void 0, function* () {
        const file = yield open(path);
        const lines = [];
        try {
            for (var _e = true, _f = __asyncValues(file.readLines()), _g; _g = yield _f.next(), _b = _g.done, !_b;) {
                _d = _g.value;
                _e = false;
                try {
                    const line = _d;
                    lines.push(line);
                }
                finally {
                    _e = true;
                }
            }
        }
        catch (e_1_1) { e_1 = { error: e_1_1 }; }
        finally {
            try {
                if (!_e && !_b && (_c = _f.return)) yield _c.call(_f);
            }
            finally { if (e_1) throw e_1.error; }
        }
        return lines;
    });
}
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
function sum(xs) {
    return xs.reduce((pv, cv, _ci, _a) => pv + cv);
}
function top(n, xs) {
    return [...xs].sort((a, b) => a - b).slice(-n);
}
function partOneSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const lines = yield slurpLines(path);
        const elves = parseElves(lines);
        return Math.max(...elves.map(sum));
    });
}
function partTwoSolution(path) {
    return __awaiter(this, void 0, void 0, function* () {
        const lines = yield slurpLines(path);
        const elves = parseElves(lines);
        return sum(top(3, elves.map(sum)));
    });
}
function main() {
    return __awaiter(this, void 0, void 0, function* () {
        const examplePath = '../examples/day01.txt';
        const inputPath = '../input/day01.txt';
        assert((yield partOneSolution(examplePath)) == 24000);
        console.log(`part one: ${yield partOneSolution(inputPath)}`);
        assert((yield partTwoSolution(examplePath)) == 45000);
        console.log(`part two: ${yield partTwoSolution(inputPath)}`);
    });
}
main();
