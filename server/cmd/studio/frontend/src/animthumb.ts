// animthumb.ts -- a compact, self-contained animation thumbnail. Given a
// client animation (jar + .sba entry) it decodes the playback, preloads the
// bitmaps, and plays the default symbol looping in a small canvas, content-fit.
// Used to show a summon's actual creature sprite (from its Gfx id) instead of
// just a name. Reuses the same Y-flip transform the full animation player uses
// (setTransform(vs*a,-vs*b,vs*c,-vs*d,vs*e+vx,-vs*f+vy)), so it renders
// identically -- just smaller and standalone.

import { getAnimationPlayback, type AnimationPlayback } from "./backend";

const DEFAULT_SYMBOL = -999999; // animDefaultSymbol: pick the movie's root symbol

// mountAnimThumb renders animations/<...>.sba into host at the given size. If
// the animation can't be loaded it shows a muted fallback. Returns a disposer
// that stops the RAF loop (call when the host is torn down).
export function mountAnimThumb(
  host: HTMLElement,
  jar: string,
  entryPath: string,
  size = 120,
  fps = 12
): () => void {
  host.innerHTML = `<canvas width="${size}" height="${size}" class="anim-thumb-canvas"></canvas>`;
  const canvas = host.querySelector<HTMLCanvasElement>("canvas")!;
  const ctx = canvas.getContext("2d")!;
  let raf = 0;
  let disposed = false;
  const images = new Map<number, HTMLImageElement>();
  let pb: AnimationPlayback | null = null;
  let frame = 0;
  let lastTs = 0;

  const stop = () => {
    disposed = true;
    if (raf) cancelAnimationFrame(raf);
  };

  getAnimationPlayback(jar, entryPath, DEFAULT_SYMBOL)
    .then(async (playback) => {
      if (disposed) return;
      await preload(playback.bitmaps, images);
      if (disposed) return;
      pb = playback;
      loop(0);
    })
    .catch(() => {
      if (!disposed) host.innerHTML = `<div class="anim-thumb-empty">no sprite</div>`;
    });

  function loop(ts: number) {
    if (disposed) return;
    raf = requestAnimationFrame(loop);
    if (!pb || pb.frames.length === 0) return;
    const cur = pb.frames[frame];
    const frameDur = Math.max(1, cur?.duration || 1);
    const msPerFrame = (1000 / fps) * frameDur;
    if (lastTs === 0) lastTs = ts;
    if (ts - lastTs >= msPerFrame) {
      lastTs = ts;
      frame = (frame + 1) % pb.frames.length;
    }
    draw();
  }

  function draw() {
    if (!pb) return;
    ctx.clearRect(0, 0, size, size);
    const [minX, minY, maxX, maxY] = pb.bounds;
    const bw = Math.max(1, maxX - minX);
    const bh = Math.max(1, maxY - minY);
    const pad = size * 0.08;
    const vs = Math.min((size - pad * 2) / bw, (size - pad * 2) / bh);
    // Center the content box; Y is flipped (world up -> canvas down).
    const cx = (minX + maxX) / 2;
    const cy = (minY + maxY) / 2;
    const vx = size / 2 - vs * cx;
    const vy = size / 2 + vs * cy;
    const f = pb.frames[frame];
    if (!f) return;
    for (const op of f.ops) {
      const img = images.get(op.bitmapId);
      if (!img) continue;
      const [a, b, c, d, e, g] = op.matrix;
      ctx.setTransform(vs * a, -vs * b, vs * c, -vs * d, vs * e + vx, -vs * g + vy);
      ctx.globalAlpha = Math.max(0, Math.min(1, op.colorMul[3]));
      ctx.drawImage(img, 0, 0);
    }
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.globalAlpha = 1;
  }

  return stop;
}

function preload(
  bitmaps: AnimationPlayback["bitmaps"],
  map: Map<number, HTMLImageElement>
): Promise<void> {
  return Promise.all(
    bitmaps.map(
      (bm) =>
        new Promise<void>((resolve) => {
          if (!bm.dataUrl) return resolve();
          const img = new Image();
          img.onload = () => {
            map.set(bm.id, img);
            resolve();
          };
          img.onerror = () => resolve();
          img.src = bm.dataUrl;
        })
    )
  ).then(() => undefined);
}
