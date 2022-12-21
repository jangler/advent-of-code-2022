import { open } from 'node:fs/promises';

export async function slurpLines(path: string): Promise<string[]> {
    const file = await open(path);
    const lines = [];
    for await (const line of file.readLines()) {
        lines.push(line);
    }
    return lines;
}