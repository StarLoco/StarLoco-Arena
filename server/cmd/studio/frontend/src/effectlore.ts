// effectlore.ts -- turns raw EffectDef records into human, game-tooltip-style
// HTML: "deals 2d6+3 (5-15) Fire damage to enemies in a 1-cell cross, for 3
// turns". It combines the backend semantic table (actionID -> kind/verb/stat/
// element/polarity) with the params->value convention and a visual area grid.
//
// This is the heart of making the data readable at a glance instead of forcing
// anyone to memorize action/param/area/target numbers.

import {
  getEffectLore,
  getGameIcons,
  type EffectDef,
  type EffectSemantic,
} from "./backend";
import { link as xlink } from "./crosslink";

let lore: Record<number, EffectSemantic> = {};
let areaShapes: Record<number, string> = {};
let targetConds: Record<number, string> = {};
let gameIcons: Record<string, string> = {};
let loaded = false;

// loadLore fetches the semantic tables + authentic game icons once. Safe to
// call repeatedly.
export async function loadLore(): Promise<void> {
  if (loaded) return;
  try {
    const [l, icons] = await Promise.all([getEffectLore(), getGameIcons()]);
    lore = {};
    for (const e of l.effects) lore[e.actionId] = e;
    areaShapes = l.areaShapes ?? {};
    targetConds = l.targetConditions ?? {};
    gameIcons = icons ?? {};
  } catch {
    /* leave empty; decoder degrades to raw ids */
  }
  loaded = true;
}

// gameIcon returns an <img> tag for a named client icon, or "" if unavailable.
export function gameIcon(name: string, size = 16, cls = ""): string {
  const url = gameIcons[name];
  if (!url) return "";
  return `<img class="game-icon ${cls}" src="${url}" width="${size}" height="${size}" alt="${name}" />`;
}

// hasGameIcons reports whether the authentic icon set loaded (else emoji).
export function hasGameIcons(): boolean {
  return Object.keys(gameIcons).length > 0;
}

// ---- element / stat visual tokens -------------------------------------------

const ELEMENT_META: Record<string, { color: string; glyph: string; icon: string }> = {
  fire: { color: "#ff6b4a", glyph: "\uD83D\uDD25", icon: "fire" },
  water: { color: "#4f9dff", glyph: "\uD83D\uDCA7", icon: "water" },
  wind: { color: "#68d391", glyph: "\uD83D\uDCA8", icon: "wind" },
  earth: { color: "#c9a06a", glyph: "\u26F0", icon: "earth" },
  physical: { color: "#d0d3da", glyph: "\u2694", icon: "" },
};

// STAT_ICON maps a human stat label to a game-icon name where one exists.
const STAT_ICON: Record<string, string> = {
  HP: "hp",
  AP: "ap",
  MP: "mp",
  Initiative: "init",
  "Fire resistance": "fire",
  "Water resistance": "water",
  "Air resistance": "wind",
  "Earth resistance": "earth",
  "Fire damage": "fire",
  "Water damage": "water",
  "Air damage": "wind",
  "Earth damage": "earth",
};

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// valueText renders an effect's magnitude from its params, matching the engine's
// rollBaseValue convention: [] -> "", [v] -> "v", [dice,faces,mod] -> a dice
// string with its min-max range so both the formula and the outcome are legible.
function valueText(params: number[] | null): { main: string; range: string } {
  if (!params || params.length === 0) return { main: "", range: "" };
  if (params.length === 1) return { main: `${round(params[0])}`, range: "" };
  const [dice, faces, mod] = params;
  if (dice <= 0 || faces <= 0) {
    return { main: `${round(mod)}`, range: "" };
  }
  const lo = dice * 1 + mod;
  const hi = dice * faces + mod;
  const modTxt = mod ? (mod > 0 ? `+${round(mod)}` : `${round(mod)}`) : "";
  const formula = `${round(dice)}d${round(faces)}${modTxt}`;
  return { main: formula, range: lo === hi ? `${lo}` : `${round(lo)}\u2013${round(hi)}` };
}

function round(n: number): number {
  return Math.round(n * 1000) / 1000;
}

function durationText(d: number[] | null): string {
  if (!d || d.length === 0) return "";
  const v = d[0];
  if (v <= 0) return "";
  if (v >= 63) return "permanently";
  return v === 1 ? "for 1 turn" : `for ${v} turns`;
}

// targetsText resolves the FightTargetValidator condition bits to a phrase.
function targetsText(targets: number[] | null): string {
  if (!targets || targets.length === 0) return "";
  const labels = new Set<string>();
  for (const t of targets) {
    for (const bitStr of Object.keys(targetConds)) {
      const bit = Number(bitStr);
      if (t & bit) labels.add(targetConds[bit]);
    }
  }
  labels.delete("in area");
  if (labels.has("enemy")) return "enemies";
  if (labels.has("ally")) return "allies";
  if (labels.has("self (caster)")) return "self";
  if (labels.has("summon")) return "summons";
  return [...labels].join(", ");
}

// ---- area-of-effect mini-grid -----------------------------------------------

// areaCells returns the set of (dx,dy) offsets an area shape covers, centered on
// the target, so we can draw a tiny isometric-agnostic top-down preview.
function areaCells(shape: number, size: number[] | null): Array<[number, number]> {
  const s = size && size.length ? size[0] : 0;
  const cells: Array<[number, number]> = [];
  const name = areaShapes[shape] ?? "point";
  if (name === "point" || name === "none" || s === 0) return [[0, 0]];
  if (name === "circle") {
    for (let dx = -s; dx <= s; dx++)
      for (let dy = -s; dy <= s; dy++) if (dx * dx + dy * dy <= s * s) cells.push([dx, dy]);
    return cells;
  }
  if (name === "cross") {
    for (let d = -s; d <= s; d++) {
      cells.push([d, 0]);
      if (d !== 0) cells.push([0, d]);
    }
    return cells;
  }
  if (name === "T") {
    const h = size && size.length ? size[0] : 1;
    const w = size && size.length > 1 ? size[1] : h;
    for (let dy = 0; dy <= h; dy++) cells.push([0, dy]);
    for (let dx = -w; dx <= w; dx++) cells.push([dx, h]);
    return cells;
  }
  return [[0, 0]];
}

// areaGrid renders a small square-cell diagram of the affected pattern.
export function areaGrid(shape: number, size: number[] | null, color: string): string {
  const cells = areaCells(shape, size);
  let minX = 0, maxX = 0, minY = 0, maxY = 0;
  for (const [x, y] of cells) {
    minX = Math.min(minX, x); maxX = Math.max(maxX, x);
    minY = Math.min(minY, y); maxY = Math.max(maxY, y);
  }
  const cols = maxX - minX + 1;
  const rows = maxY - minY + 1;
  const set = new Set(cells.map(([x, y]) => `${x},${y}`));
  const cell = 9;
  let rects = "";
  for (let y = minY; y <= maxY; y++) {
    for (let x = minX; x <= maxX; x++) {
      const on = set.has(`${x},${y}`);
      const center = x === 0 && y === 0;
      const px = (x - minX) * cell;
      const py = (y - minY) * cell;
      const fill = center ? color : on ? hexA(color, 0.42) : "rgba(255,255,255,0.05)";
      rects += `<rect x="${px + 0.5}" y="${py + 0.5}" width="${cell - 1}" height="${cell - 1}" rx="1.5" fill="${fill}" ${
        center ? `stroke="#fff" stroke-width="1"` : ""
      }/>`;
    }
  }
  const w = cols * cell;
  const h = rows * cell;
  return `<svg class="aoe-grid" width="${w}" height="${h}" viewBox="0 0 ${w} ${h}">${rects}</svg>`;
}

function hexA(hex: string, a: number): string {
  const m = /^#?([0-9a-f]{6})$/i.exec(hex);
  if (!m) return hex;
  const n = parseInt(m[1], 16);
  return `rgba(${(n >> 16) & 255}, ${(n >> 8) & 255}, ${n & 255}, ${a})`;
}

// ---- one-effect decode ------------------------------------------------------

const POLARITY_COLOR: Record<string, string> = {
  good: "#3ecf8e",
  bad: "#ff6b6b",
  neutral: "#7db4ff",
};

// decodeEffectText returns a plain, single-line human sentence for an effect
// (no HTML) -- used for search text and summaries.
export function decodeEffectText(e: EffectDef): string {
  const s = lore[e.ActionID];
  const val = valueText(e.Params);
  const parts: string[] = [];
  const verb = s ? s.verb : `action ${e.ActionID}`;
  parts.push(cap(verb));
  if (val.main) parts.push(val.range ? `${val.main} (${val.range})` : val.main);
  if (s?.element) parts.push(`${s.element} damage`);
  else if (s?.stat) parts.push(s.stat);
  const tgt = targetsText(e.Targets);
  if (tgt) parts.push(`to ${tgt}`);
  const dur = durationText(e.Duration);
  if (dur) parts.push(dur);
  return parts.join(" ");
}

// decodeEffectHTML renders a rich, colored effect line for the detail drawer.
export function decodeEffectHTML(e: EffectDef): string {
  const s = lore[e.ActionID];
  const color = s ? POLARITY_COLOR[s.polarity] : "#9aa0aa";
  const val = valueText(e.Params);
  const elemMeta = s?.element ? ELEMENT_META[s.element] : null;

  const chips: string[] = [];
  // magnitude chip (formula + range)
  if (val.main) {
    chips.push(
      `<span class="fx-val" style="color:${color}">${esc(val.main)}${
        val.range ? ` <span class="fx-range">(${esc(val.range)})</span>` : ""
      }</span>`
    );
  }
  // element or stat chip -- prefer the authentic client icon, fall back to emoji.
  if (elemMeta) {
    const ico = gameIcon(elemMeta.icon, 15) || elemMeta.glyph;
    chips.push(
      `<span class="fx-chip" style="--c:${elemMeta.color}">${ico} ${esc(s!.element)}</span>`
    );
  } else if (s?.stat) {
    const pct = s.note === "percent" ? "%" : "";
    const iconName = STAT_ICON[s.stat];
    const ico = iconName ? gameIcon(iconName, 15) : "";
    chips.push(`<span class="fx-chip fx-stat">${ico}${esc(s.stat)}${pct}</span>`);
  }

  const verb = s ? cap(s.verb) : `Action ${e.ActionID}`;
  const tgt = targetsText(e.Targets);
  const dur = durationText(e.Duration);
  const shapeName = areaShapes[e.AreaShape] ?? "";
  const sizeTxt = e.AreaSize && e.AreaSize.length ? e.AreaSize.join("\u00D7") : "";

  // Cross-link: summon effects reference a Summoning template via Params[0].
  const isSummon =
    s && (s.kind === "summon" || s.kind === "summon_double" || s.kind === "summon_mirror");
  if (isSummon && e.Params && e.Params.length) {
    const sid = Math.round(e.Params[0]);
    chips.push(xlink("summonings", String(sid), `summon #${sid}`, "\u2726"));
  }

  const meta: string[] = [];
  if (tgt) meta.push(`<span class="fx-meta-t">${esc(tgt)}</span>`);
  if (dur) meta.push(`<span class="fx-meta-d">${esc(dur)}</span>`);
  if (e.IsCritical) meta.push(`<span class="fx-crit">crit</span>`);
  if (shapeName && shapeName !== "point" && shapeName !== "none")
    meta.push(`<span class="fx-area-name">${esc(shapeName)}${sizeTxt ? " " + esc(sizeTxt) : ""}</span>`);

  const grid =
    shapeName && shapeName !== "none" && shapeName !== "point"
      ? `<div class="fx-aoe">${areaGrid(e.AreaShape, e.AreaSize, color)}</div>`
      : "";

  return `
    <div class="fx-line ${s ? "" : "fx-unknown"}">
      <span class="fx-dot" style="background:${color}"></span>
      <div class="fx-body">
        <div class="fx-headline">
          <b class="fx-verb">${esc(verb)}</b>
          ${chips.join("")}
          <span class="fx-action mono" title="raw actionID">#${e.ActionID}</span>
        </div>
        ${meta.length ? `<div class="fx-meta">${meta.join("")}</div>` : ""}
      </div>
      ${grid}
    </div>`;
}

// decodeEffectsHTML renders a whole effect list (spell/card/event/static).
export function decodeEffectsHTML(effects: EffectDef[] | null): string {
  if (!effects || effects.length === 0) {
    return `<div class="detail-empty">No attached effects.</div>`;
  }
  const lines = effects.map((e) => decodeEffectHTML(e)).join("");
  return `<div class="fx-list">${lines}</div>`;
}

// effectsSummary gives a compact one-liner for a table cell (first effect +N).
export function effectsSummary(effects: EffectDef[] | null): string {
  if (!effects || effects.length === 0) return "\u2014";
  const first = decodeEffectText(effects[0]);
  const extra = effects.length > 1 ? ` <span class="fx-more">+${effects.length - 1}</span>` : "";
  return `<span class="fx-sum">${esc(first)}</span>${extra}`;
}

function cap(s: string): string {
  return s.charAt(0).toUpperCase() + s.slice(1);
}
