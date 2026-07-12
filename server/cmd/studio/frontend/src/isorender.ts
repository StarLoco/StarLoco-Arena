// Shared isometric tile renderer, extracted so both the map viewer and the
// map builder draw identically using the client's exact, PNG-verified math:
//   projection  px=(x-y)*43, py=(x+y)*21.5  (final screen space, Y-down)
//   elevation   sprite lifted by altitude*10 px
//   draw order  by depth diagonal (x+y), then x, then within-cell altitudeOrder
//   anchor      sprite (originX,originY) pins onto the projected cell point
import type { MapDrawable, MapGfx } from "./backend";

export const TILE_HX = 43; // cellWidth/2
export const TILE_HY = 21.5; // cellHeight/2
export const ELEVATION_UNIT = 10; // DEFAULT_ELEVATION_UNIT

export function project(x: number, y: number): { px: number; py: number } {
  return { px: (x - y) * TILE_HX, py: (x + y) * TILE_HY };
}

// unproject a screen point (already un-panned/un-zoomed) to grid coords.
export function unproject(px: number, py: number): { x: number; y: number } {
  const a = px / TILE_HX; // x - y
  const b = py / TILE_HY; // x + y
  return { x: Math.round((a + b) / 2), y: Math.round((b - a) / 2) };
}

// sortDrawables orders sprites back-to-front for painting using the client's
// exact single continuous z-value (DisplayedCell.updateDisplayedElements,
// verified in tools/zorder-re):
//   zValue = (x+y)*cellHeight/2 + altitudeOrder,  painted ascending.
// This must NOT be bucketed by (x+y) then x -- bucketing prevents a tall back
// element from correctly interleaving in front of a nearer low tile.
export function sortDrawables(drawables: MapDrawable[]): MapDrawable[] {
  const zValue = (d: MapDrawable) => (d.x + d.y) * TILE_HY + d.altitudeOrder;
  return drawables.slice().sort((a, b) => {
    const za = zValue(a);
    const zb = zValue(b);
    if (za !== zb) return za - zb;
    return a.order - b.order;
  });
}

// loadGfxImages turns a decoded gfx batch into ready HTMLImageElements.
export async function loadGfxImages(batch: MapGfx[]): Promise<Map<number, HTMLImageElement>> {
  const out = new Map<number, HTMLImageElement>();
  await Promise.all(
    batch.map(
      (g) =>
        new Promise<void>((resolve) => {
          const img = new Image();
          img.onload = () => {
            out.set(g.gfxId, img);
            resolve();
          };
          img.onerror = () => resolve();
          img.src = g.dataUrl;
        })
    )
  );
  return out;
}

// drawTiles paints all sprites onto ctx with the given pan/zoom.
export function drawTiles(
  ctx: CanvasRenderingContext2D,
  drawables: MapDrawable[],
  gfx: Map<number, HTMLImageElement>,
  zoom: number,
  panX: number,
  panY: number
) {
  const sorted = sortDrawables(drawables);
  for (const d of sorted) {
    const img = gfx.get(d.gfxId);
    if (!img) continue;
    const { px, py } = project(d.x, d.y);
    const sx = px * zoom + panX;
    const sy = (py - d.altitude * ELEVATION_UNIT) * zoom + panY;
    const w = Math.round(img.width * zoom);
    const h = Math.round(img.height * zoom);
    const dy = Math.round(sy - d.originY * zoom);
    const drawX = Math.round(sx - d.originX * zoom);
    if (d.flip) {
      // The client flips via texture coords only: the pixels mirror WITHIN the
      // same quad, the sprite does not move. So a flipped sprite occupies the
      // same box [drawX, drawX+w]; mirror in place about that box.
      ctx.save();
      ctx.translate(drawX + w, dy);
      ctx.scale(-1, 1);
      ctx.drawImage(img, 0, 0, w, h);
      ctx.restore();
    } else {
      ctx.drawImage(img, drawX, dy, w, h);
    }
  }
}

// drawCellDiamond strokes/fills a single cell's ground diamond (for overlays
// like selection/hover/grid in the builder). `alt` lifts the diamond by
// alt*ELEVATION_UNIT screen px so it sits on top of the rendered tile
// surface (matching drawTiles' elevation); pass 0 for a ground-level diamond.
export function drawCellDiamond(
  ctx: CanvasRenderingContext2D,
  x: number,
  y: number,
  zoom: number,
  panX: number,
  panY: number,
  fill: string | null,
  stroke: string | null,
  lineWidth = 1,
  alt = 0
) {
  const { px, py } = project(x, y);
  const sx = px * zoom + panX;
  const sy = (py - alt * ELEVATION_UNIT) * zoom + panY;
  const hx = TILE_HX * zoom;
  const hy = TILE_HY * zoom;
  ctx.beginPath();
  ctx.moveTo(sx, sy - hy);
  ctx.lineTo(sx + hx, sy);
  ctx.lineTo(sx, sy + hy);
  ctx.lineTo(sx - hx, sy);
  ctx.closePath();
  if (fill) {
    ctx.fillStyle = fill;
    ctx.fill();
  }
  if (stroke) {
    ctx.strokeStyle = stroke;
    ctx.lineWidth = lineWidth;
    ctx.stroke();
  }
}
