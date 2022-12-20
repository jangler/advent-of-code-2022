"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var __asyncValues = (this && this.__asyncValues) || function (o) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var m = o[Symbol.asyncIterator], i;
    return m ? m.call(o) : (o = typeof __values === "function" ? __values(o) : o[Symbol.iterator](), i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i);
    function verb(n) { i[n] = o[n] && function (v) { return new Promise(function (resolve, reject) { v = o[n](v), settle(resolve, reject, v.done, v.value); }); }; }
    function settle(resolve, reject, d, v) { Promise.resolve(v).then(function(v) { resolve({ value: v, done: d }); }, reject); }
};
var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
exports.__esModule = true;
var node_console_1 = require("node:console");
var promises_1 = require("node:fs/promises");
function slurpLines(path) {
    var _b, e_1, _c, _d;
    return __awaiter(this, void 0, void 0, function () {
        var file, lines, _e, _f, _g, line, e_1_1;
        return __generator(this, function (_h) {
            switch (_h.label) {
                case 0: return [4 /*yield*/, (0, promises_1.open)(path)];
                case 1:
                    file = _h.sent();
                    lines = [];
                    _h.label = 2;
                case 2:
                    _h.trys.push([2, 7, 8, 13]);
                    _e = true, _f = __asyncValues(file.readLines());
                    _h.label = 3;
                case 3: return [4 /*yield*/, _f.next()];
                case 4:
                    if (!(_g = _h.sent(), _b = _g.done, !_b)) return [3 /*break*/, 6];
                    _d = _g.value;
                    _e = false;
                    try {
                        line = _d;
                        lines.push(line);
                    }
                    finally {
                        _e = true;
                    }
                    _h.label = 5;
                case 5: return [3 /*break*/, 3];
                case 6: return [3 /*break*/, 13];
                case 7:
                    e_1_1 = _h.sent();
                    e_1 = { error: e_1_1 };
                    return [3 /*break*/, 13];
                case 8:
                    _h.trys.push([8, , 11, 12]);
                    if (!(!_e && !_b && (_c = _f["return"]))) return [3 /*break*/, 10];
                    return [4 /*yield*/, _c.call(_f)];
                case 9:
                    _h.sent();
                    _h.label = 10;
                case 10: return [3 /*break*/, 12];
                case 11:
                    if (e_1) throw e_1.error;
                    return [7 /*endfinally*/];
                case 12: return [7 /*endfinally*/];
                case 13: return [2 /*return*/, lines];
            }
        });
    });
}
function parseElves(lines) {
    var elves = [[]];
    for (var _i = 0, lines_1 = lines; _i < lines_1.length; _i++) {
        var line = lines_1[_i];
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
    return xs.reduce(function (pv, cv, _ci, _a) { return pv + cv; });
}
function max(xs) {
    return xs.reduce(function (pv, cv, _ci, _a) { return cv > pv ? cv : pv; });
}
function top(n, xs) {
    return __spreadArray([], xs, true).sort(function (a, b) { return a - b; }).slice(-n);
}
function partOneSolution(path) {
    return __awaiter(this, void 0, void 0, function () {
        var lines, elves;
        return __generator(this, function (_b) {
            switch (_b.label) {
                case 0: return [4 /*yield*/, slurpLines(path)];
                case 1:
                    lines = _b.sent();
                    elves = parseElves(lines);
                    return [2 /*return*/, max(elves.map(sum))];
            }
        });
    });
}
function partTwoSolution(path) {
    return __awaiter(this, void 0, void 0, function () {
        var lines, elves;
        return __generator(this, function (_b) {
            switch (_b.label) {
                case 0: return [4 /*yield*/, slurpLines(path)];
                case 1:
                    lines = _b.sent();
                    elves = parseElves(lines);
                    return [2 /*return*/, sum(top(3, elves.map(sum)))];
            }
        });
    });
}
function main() {
    return __awaiter(this, void 0, void 0, function () {
        var examplePath, inputPath, _b, _c, _d, _e, _f, _g, _h, _j;
        return __generator(this, function (_k) {
            switch (_k.label) {
                case 0:
                    examplePath = '../examples/day01.txt';
                    inputPath = '../input/day01.txt';
                    _b = node_console_1.assert;
                    return [4 /*yield*/, partOneSolution(examplePath)];
                case 1:
                    _b.apply(void 0, [(_k.sent()) == 24000]);
                    _d = (_c = console).log;
                    _e = "part one: ".concat;
                    return [4 /*yield*/, partOneSolution(inputPath)];
                case 2:
                    _d.apply(_c, [_e.apply("part one: ", [_k.sent()])]);
                    _f = node_console_1.assert;
                    return [4 /*yield*/, partTwoSolution(examplePath)];
                case 3:
                    _f.apply(void 0, [(_k.sent()) == 45000]);
                    _h = (_g = console).log;
                    _j = "part two: ".concat;
                    return [4 /*yield*/, partTwoSolution(inputPath)];
                case 4:
                    _h.apply(_g, [_j.apply("part two: ", [_k.sent()])]);
                    return [2 /*return*/];
            }
        });
    });
}
main();
