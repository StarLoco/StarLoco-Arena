// spellarea.ts -- an isometric "spell footprint" diagram. Given a spell's cast
// parameters (range min/max, line-only flag) it draws the reachable cast tiles
// around the caster as game-style iso diamonds, so the spell's reach is legible
// at a glance instead of two bare numbers. Optionally overlays an effect's area
// shape at an example target so you can see range + AoE together.
//
// Range in DofusArena is Manhattan distance (|dx|+|dy|) between rangeMin and
// rangeMax; castOnlyLine restricts casts to the 4 orthogonal lines. This mirrors
// how the client highlights castable cells.

import { areaCells } from "./effectlore";

const HX = 20; // half tile width (compact)
const HY = 10; // half tile height

interface SpellAreaOpts {
  rangeMin: number;
  rangeMax: number;
  onlyLine: boolean;
  // Optional AoE overlay at an example target cell (rangeMax straight ahead).
  aoeShape?: number;
  aoeSize?: number[] | null;
}

// iso projects a grid (dx,dy) offset to screen coords within the diagram.
function iso(dx: number, dy: number): { x: number; y: number } {
  return { x: (dx - dy) * HX, y: (dx + dy) * HY };
}

// diamond returns an SVG polygon for the tile centered at (cx,cy).
function diamond(cx: number, cy: number, fill: string, stroke: string, sw = 1): string {
  const pts = [
    [cx, cy - HY],
    [cx + HX, cy],
    [cx, cy + HY],
    [cx - HX, cy],
  ]
    .map((p) => p.join(","))
    .join(" ");
  return `<polygon points="${pts}" fill="${fill}" stroke="${stroke}" stroke-width="${sw}" />`;
}

// spellAreaSVG renders the spell's reachable cast tiles (+ optional AoE) as an
// iso diagram. Colors: caster (accent), in-range (blue), AoE (red-ish).
export function spellAreaSVG(o: SpellAreaOpts): string {
  const rMax = Math.max(0, Math.min(12, o.rangeMax)); // clamp for a sane diagram
  const rMin = Math.max(0, Math.min(rMax, o.rangeMin));

  // Collect castable tiles (Manhattan range, optionally line-only).
  const cast: Array<[number, number]> = [];
  for (let dx = -rMax; dx <= rMax; dx++) {
    for (let dy = -rMax; dy <= rMax; dy++) {
      const d = Math.abs(dx) + Math.abs(dy);
      if (d < rMin || d > rMax) continue;
      if (o.onlyLine && dx !== 0 && dy !== 0) continue;
      cast.push([dx, dy]);
    }
  }

  // Example target for the AoE overlay: straight "north-east" at rangeMax.
  const aoe = new Set<string>();
  let tgt: [number, number] | null = null;
  if (o.aoeShape != null) {
    tgt = [rMax, 0];
    for (const [ax, ay] of areaCells(o.aoeShape, o.aoeSize ?? null)) {
      aoe.add(`${tgt[0] + ax},${tgt[1] + ay}`);
    }
  }

  // Bounds over everything we draw (cast tiles + caster + AoE).
  const all: Array<[number, number]> = [[0, 0], ...cast];
  for (const k of aoe) {
    const [x, y] = k.split(",").map(Number);
    all.push([x, y]);
  }
  let minSX = Infinity,
    maxSX = -Infinity,
    minSY = Infinity,
    maxSY = -Infinity;
  for (const [dx, dy] of all) {
    const p = iso(dx, dy);
    minSX = Math.min(minSX, p.x - HX);
    maxSX = Math.max(maxSX, p.x + HX);
    minSY = Math.min(minSY, p.y - HY);
    maxSY = Math.max(maxSY, p.y + HY);
  }
  const pad = 4;
  const w = maxSX - minSX + pad * 2;
  const h = maxSY - minSY + pad * 2;
  const ox = -minSX + pad;
  const oy = -minSY + pad;

  // Paint order: range tiles, then AoE, then caster on top (so it's never
  // hidden), then target ring.
  let svg = "";
  for (const [dx, dy] of cast) {
    const p = iso(dx, dy);
    const inAoe = aoe.has(`${dx},${dy}`);
    svg += diamond(
      p.x + ox,
      p.y + oy,
      inAoe ? "rgba(255,107,107,0.5)" : "rgba(79,157,255,0.28)",
      "rgba(255,255,255,0.12)"
    );
  }
  // AoE cells that fall outside cast range still get drawn (faint).
  for (const k of aoe) {
    const [dx, dy] = k.split(",").map(Number);
    if (cast.some(([cx, cy]) => cx === dx && cy === dy)) continue;
    const p = iso(dx, dy);
    svg += diamond(p.x + ox, p.y + oy, "rgba(255,107,107,0.35)", "rgba(255,255,255,0.1)");
  }
  // Caster.
  const c = iso(0, 0);
  svg += diamond(c.x + ox, c.y + oy, "var(--accent, #4f9dff)", "#fff", 1.5);
  // Target marker.
  if (tgt) {
    const p = iso(tgt[0], tgt[1]);
    svg += `<circle cx="${p.x + ox}" cy="${p.y + oy}" r="3" fill="#ffb454" stroke="#000" stroke-width="0.5"/>`;
  }

  return `<svg class="spell-area-svg" width="${Math.ceil(w)}" height="${Math.ceil(
    h
  )}" viewBox="0 0 ${Math.ceil(w)} ${Math.ceil(h)}">${svg}</svg>`;
}

// spellAreaBlock renders the full labelled diagram block for a spell drawer.
export function spellAreaBlock(o: SpellAreaOpts): string {
  const rangeTxt =
    o.rangeMin === o.rangeMax ? `${o.rangeMax}` : `${o.rangeMin}\u2013${o.rangeMax}`;
  const flags = [o.onlyLine ? "line only" : "", o.aoeShape != null ? "with area of effect" : ""]
    .filter(Boolean)
    .join(" \u00B7 ");
  return `
    <div class="detail-block">
      <div class="detail-title">Cast footprint</div>
      <div class="spell-area">
        ${spellAreaSVG(o)}
        <div class="spell-area-legend">
          <div><span class="sa-swatch caster"></span> caster</div>
          <div><span class="sa-swatch range"></span> range ${rangeTxt}</div>
          ${o.aoeShape != null ? `<div><span class="sa-swatch aoe"></span> area of effect</div>` : ""}
          ${flags ? `<div class="sa-flags">${flags}</div>` : ""}
        </div>
      </div>
    </div>`;
}
