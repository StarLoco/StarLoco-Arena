// luahl.ts -- a tiny, dependency-free Lua syntax highlighter. It tokenizes Lua
// source into HTML spans for use as a highlight layer behind a transparent
// textarea (the classic overlay technique). It is deliberately conservative:
// comments, strings (incl. long-bracket forms), numbers, keywords, booleans and
// the standard globals used by the client's spell scripts get colored; anything
// else is left as plain text. Highlighting is cosmetic, so imperfect edge cases
// never affect what gets saved.

const KEYWORDS = new Set([
  "and", "break", "do", "else", "elseif", "end", "false", "for", "function",
  "goto", "if", "in", "local", "nil", "not", "or", "repeat", "return", "then",
  "true", "until", "while",
]);

// Globals/library names common in the client's Lua spell scripts. Coloring them
// as "builtin" makes the API surface pop without a full symbol table.
const BUILTINS = new Set([
  "self", "target", "caster", "spell", "effect", "fight", "cell", "math",
  "table", "string", "pairs", "ipairs", "tostring", "tonumber", "type",
  "print", "pcall", "error", "setmetatable", "getmetatable", "rawget", "rawset",
]);

function escHTML(s: string): string {
  return s.replace(
    /[&<>]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;" }[c] as string)
  );
}

function span(cls: string, text: string): string {
  return `<span class="lh-${cls}">${escHTML(text)}</span>`;
}

// highlightLua returns HTML for the given Lua source. Newlines are preserved
// verbatim so the overlay lines up 1:1 with the textarea.
export function highlightLua(src: string): string {
  let out = "";
  let i = 0;
  const n = src.length;

  const isIdentStart = (ch: string) => /[A-Za-z_]/.test(ch);
  const isIdent = (ch: string) => /[A-Za-z0-9_]/.test(ch);
  const isDigit = (ch: string) => /[0-9]/.test(ch);

  // matchLongBracket returns the level of a long bracket opener at pos i
  // ("[[", "[=[", ...), or -1 if none.
  const longOpenLevel = (pos: number): number => {
    if (src[pos] !== "[") return -1;
    let j = pos + 1;
    let eq = 0;
    while (src[j] === "=") {
      eq++;
      j++;
    }
    return src[j] === "[" ? eq : -1;
  };
  // consumeLong reads a long-bracket body from an opener at pos with `level`
  // equals signs, returning the end index (exclusive of matching close).
  const consumeLong = (pos: number, level: number): number => {
    const close = "]" + "=".repeat(level) + "]";
    const at = src.indexOf(close, pos);
    return at === -1 ? n : at + close.length;
  };

  while (i < n) {
    const ch = src[i];

    // Comments: -- ... (line) or --[[ ... ]] (long).
    if (ch === "-" && src[i + 1] === "-") {
      const lvl = longOpenLevel(i + 2);
      if (lvl >= 0) {
        const end = consumeLong(i + 2, lvl);
        out += span("comment", src.slice(i, end));
        i = end;
        continue;
      }
      let j = i;
      while (j < n && src[j] !== "\n") j++;
      out += span("comment", src.slice(i, j));
      i = j;
      continue;
    }

    // Long-bracket string.
    const lvl = longOpenLevel(i);
    if (lvl >= 0) {
      const end = consumeLong(i, lvl);
      out += span("string", src.slice(i, end));
      i = end;
      continue;
    }

    // Quoted string.
    if (ch === '"' || ch === "'") {
      const quote = ch;
      let j = i + 1;
      while (j < n) {
        if (src[j] === "\\") {
          j += 2;
          continue;
        }
        if (src[j] === quote || src[j] === "\n") {
          j++;
          break;
        }
        j++;
      }
      out += span("string", src.slice(i, j));
      i = j;
      continue;
    }

    // Number (decimal or hex; simple form).
    if (isDigit(ch) || (ch === "." && isDigit(src[i + 1]))) {
      let j = i;
      if (ch === "0" && (src[i + 1] === "x" || src[i + 1] === "X")) {
        j = i + 2;
        while (j < n && /[0-9a-fA-F]/.test(src[j])) j++;
      } else {
        while (j < n && /[0-9.eE+\-]/.test(src[j])) {
          // stop a trailing +/- that isn't part of an exponent
          if ((src[j] === "+" || src[j] === "-") && !/[eE]/.test(src[j - 1])) break;
          j++;
        }
      }
      out += span("number", src.slice(i, j));
      i = j;
      continue;
    }

    // Identifier / keyword / builtin.
    if (isIdentStart(ch)) {
      let j = i;
      while (j < n && isIdent(src[j])) j++;
      const word = src.slice(i, j);
      if (KEYWORDS.has(word)) out += span("kw", word);
      else if (BUILTINS.has(word)) out += span("builtin", word);
      else out += escHTML(word);
      i = j;
      continue;
    }

    // Everything else (whitespace, punctuation) passes through escaped.
    out += escHTML(ch);
    i++;
  }
  return out;
}
