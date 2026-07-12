// Phase 6 animation player: lists .sba files from animations.jar /
// equipments.jar and renders them with a real Canvas timeline player
// (play/pause/scrub/loop, symbol picker) built on the fully reverse-engineered
// Go sba decoder. It also supports fighter "dress-up" — compositing a base
// animation with equipment .sba layers on top. A structure/debug view exposes
// the raw tag stream.

import {
  listAnimations,
  getAnimation,
  getAnimationPlayback,
  composeFighter,
  ANIM_DEFAULT_SYMBOL,
  type AnimationInfo,
  type AnimationDoc,
  type AnimationPlayback,
  type PlayerBitmap,
  type PlayerOp,
  type LayerRef,
} from "./backend";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

function fmtBytes(n: number): string {
  if (n < 1024) return `${n} B`;
  if (n < 1024 * 1024) return `${(n / 1024).toFixed(1)} KB`;
  return `${(n / 1024 / 1024).toFixed(2)} MB`;
}

interface Layer {
  jar: string;
  path: string;
  name: string;
}

export function viewAnimations(container: HTMLElement) {
  container.innerHTML = `
    <div class="page-head"><h1>Animations</h1><span class="sub">.sba (Sprite Byte Animation) &middot; player</span></div>
    <div class="assets-layout">
      <div class="assets-entries" id="animList"><div class="loading">Loading animations\u2026</div></div>
      <div class="assets-preview" id="animView" style="grid-column: span 2"><div class="preview-empty">Select an animation to play it.</div></div>
    </div>`;

  const listEl = container.querySelector<HTMLElement>("#animList")!;
  const viewEl = container.querySelector<HTMLElement>("#animView")!;

  let all: AnimationInfo[] = [];
  let filter = "";
  const player = new AnimationPlayer(viewEl);
  // Equipment layers the user has stacked for dress-up.
  const equipment: Layer[] = [];
  let base: Layer | null = null;

  listAnimations()
    .then((items) => {
      all = items;
      drawList();
    })
    .catch((err) => {
      listEl.innerHTML = `<div class="preview-error"><b>No animations.</b><div class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
    });

  function drawList() {
    const q = filter.trim().toLowerCase();
    const list = (q ? all.filter((a) => a.path.toLowerCase().includes(q)) : all).slice(0, 2000);
    listEl.innerHTML = `
      <div class="entry-toolbar">
        <input class="tb-search" id="animFilter" placeholder="Filter ${all.length} animations\u2026" value="${esc(
      filter
    )}" />
      </div>
      <div class="entry-scroll">
        ${list
          .map(
            (a) => `
          <div class="entry-item" data-jar="${esc(a.jar)}" data-path="${esc(a.path)}" data-name="${esc(a.name)}">
            <span class="entry-ico image">\u2637</span>
            <span class="entry-path">${esc(a.jar)} / ${esc(a.name)}</span>
            <span class="entry-size">${fmtBytes(a.bytes)}</span>
            <button class="anim-addlayer" title="Add as equipment layer" data-jar="${esc(
              a.jar
            )}" data-path="${esc(a.path)}" data-name="${esc(a.name)}">+layer</button>
          </div>`
          )
          .join("")}
      </div>`;
    const fi = listEl.querySelector<HTMLInputElement>("#animFilter")!;
    fi.addEventListener("input", () => {
      filter = fi.value;
      const pos = fi.selectionStart;
      drawList();
      const ni = listEl.querySelector<HTMLInputElement>("#animFilter")!;
      ni.focus();
      if (pos != null) ni.setSelectionRange(pos, pos);
    });
    listEl.querySelectorAll<HTMLElement>(".entry-item").forEach((el) => {
      el.addEventListener("click", (ev) => {
        if ((ev.target as HTMLElement).classList.contains("anim-addlayer")) return;
        base = { jar: el.dataset.jar!, path: el.dataset.path!, name: el.dataset.name! };
        open();
      });
    });
    listEl.querySelectorAll<HTMLButtonElement>(".anim-addlayer").forEach((btn) => {
      btn.addEventListener("click", (ev) => {
        ev.stopPropagation();
        equipment.push({ jar: btn.dataset.jar!, path: btn.dataset.path!, name: btn.dataset.name! });
        open();
      });
    });
  }

  function open() {
    if (!base) return;
    listEl.querySelectorAll<HTMLElement>(".entry-item").forEach((el) => {
      el.classList.toggle("sel", el.dataset.jar === base!.jar && el.dataset.path === base!.path);
    });
    player.load(base, equipment, () => {
      equipment.length = 0;
      open();
    });
  }
}

// AnimationPlayer owns the canvas render loop and all playback controls.
class AnimationPlayer {
  private root: HTMLElement;
  private canvas!: HTMLCanvasElement;
  private ctx!: CanvasRenderingContext2D;
  private pb: AnimationPlayback | null = null;
  private images = new Map<number, HTMLImageElement>();
  private frame = 0;
  private playing = true;
  private loop = true;
  private fps = 12;
  private zoom = 1;
  private panX = 0;
  private panY = 0;
  private bg: "checker" | "dark" | "light" = "checker";
  private onion = false;
  private accum = 0;
  private lastTs = 0;
  private raf = 0;
  private base: Layer | null = null;
  private equipment: Layer[] = [];
  private clearEquip: (() => void) | null = null;

  constructor(root: HTMLElement) {
    this.root = root;
  }

  async load(base: Layer, equipment: Layer[], clearEquip: () => void) {
    this.base = base;
    this.equipment = equipment.slice();
    this.clearEquip = clearEquip;
    this.stop();
    this.root.innerHTML = `<div class="loading">Decoding & compositing \u2026</div>`;
    try {
      const pb =
        equipment.length > 0
          ? await composeFighter(
              { jar: base.jar, path: base.path, symbolId: ANIM_DEFAULT_SYMBOL },
              equipment.map<LayerRef>((e) => ({
                jar: e.jar,
                path: e.path,
                symbolId: ANIM_DEFAULT_SYMBOL,
              }))
            )
          : await getAnimationPlayback(base.jar, base.path, ANIM_DEFAULT_SYMBOL);
      await this.setPlayback(pb);
    } catch (err) {
      this.root.innerHTML = `<div class="preview-error"><b>Decode failed.</b><div class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
    }
  }

  private async loadSymbol(symbolID: number) {
    if (!this.base) return;
    this.stop();
    const pb = await getAnimationPlayback(this.base.jar, this.base.path, symbolID);
    await this.setPlayback(pb);
  }

  private async setPlayback(pb: AnimationPlayback) {
    this.pb = pb;
    this.frame = 0;
    this.playing = true;
    this.accum = 0;
    await this.preload(pb.bitmaps);
    this.renderShell();
    this.fit();
    this.start();
  }

  private preload(bitmaps: PlayerBitmap[]): Promise<void> {
    this.images.clear();
    const jobs = bitmaps.map(
      (bm) =>
        new Promise<void>((resolve) => {
          if (!bm.dataUrl) return resolve();
          const img = new Image();
          img.onload = () => {
            this.images.set(bm.id, img);
            resolve();
          };
          img.onerror = () => resolve();
          img.src = bm.dataUrl;
        })
    );
    return Promise.all(jobs).then(() => undefined);
  }

  private renderShell() {
    const pb = this.pb!;
    const frameCount = pb.frames.length;
    const symOpts = pb.symbols
      .map(
        (s) =>
          `<option value="${s.id}" ${s.id === pb.selected ? "selected" : ""}>${
            s.id === -1 ? "root timeline" : `#${s.id}${s.linkage ? " " + esc(s.linkage) : ""}`
          } &middot; ${esc(s.kind)} (${s.frameCount}f)</option>`
      )
      .join("");
    const layerChips = this.equipment.length
      ? `<div class="anim-layers">base: <b>${esc(this.base!.name)}</b>${this.equipment
          .map((e) => ` + <span class="tb-badge">${esc(e.name)}</span>`)
          .join("")}<button id="animClearLayers">clear layers</button></div>`
      : "";

    this.root.innerHTML = `
      <div class="preview-head">
        <div class="preview-name">${esc(pb.jar)} / ${esc(pb.path)}</div>
        <div class="preview-meta">
          <span class="anim-tag">v${pb.version}</span>
          <span class="anim-tag">${pb.compressed ? "zlib" : "raw"}</span>
          <span class="anim-tag">${frameCount} frames</span>
          <span class="anim-tag">${pb.bitmaps.length} bitmaps</span>
          ${pb.loopCount ? `<span class="anim-tag">loop ${pb.loopCount}</span>` : ""}
          <span class="anim-tag mono">sig ${esc(pb.signature)}</span>
        </div>
      </div>
      ${layerChips}
      <div class="anim-stage bg-${this.bg}">
        <canvas id="animCanvas" width="640" height="420"></canvas>
      </div>
      <div class="anim-transport">
        <button id="animPlay" class="anim-play">${this.playing ? "\u23F8" : "\u25B6"}</button>
        <input type="range" id="animScrub" min="0" max="${Math.max(0, frameCount - 1)}" value="${
      this.frame
    }" />
        <span class="anim-frameno mono" id="animFrameNo">1 / ${frameCount}</span>
      </div>
      <div class="anim-controls">
        <div class="anim-group">
          <label class="anim-ctl">fps <input type="number" id="animFps" min="1" max="60" value="${
            this.fps
          }" /></label>
          <label class="anim-ctl"><input type="checkbox" id="animLoop" ${
            this.loop ? "checked" : ""
          } /> loop</label>
        </div>
        <div class="anim-group">
          <label class="anim-ctl">zoom <input type="range" id="animZoom" min="0.1" max="6" step="0.1" value="${
            this.zoom
          }" /></label>
          <button id="animFit" title="Reset zoom & pan">Fit</button>
        </div>
        <div class="anim-group">
          <label class="anim-ctl">bg
            <select id="animBg">
              <option value="checker" ${this.bg === "checker" ? "selected" : ""}>checker</option>
              <option value="dark" ${this.bg === "dark" ? "selected" : ""}>dark</option>
              <option value="light" ${this.bg === "light" ? "selected" : ""}>light</option>
            </select>
          </label>
          <label class="anim-ctl"><input type="checkbox" id="animOnion" ${
            this.onion ? "checked" : ""
          } /> onion</label>
        </div>
        <div class="anim-group">
          <button id="animExportFrame" title="Download the current frame as PNG">\u2B07 Frame</button>
          <button id="animExportSheet" title="Download all frames as a sprite sheet PNG">\u2B07 Sheet</button>
        </div>
        <div class="anim-group anim-group-right">
          <select id="animSymbol" class="anim-symsel" title="Symbol / timeline">${symOpts}</select>
          <button id="animStruct" title="Show the raw tag structure">Structure</button>
        </div>
      </div>
      <div class="anim-hint">Drag the stage to pan &middot; scroll to zoom &middot; use <b>+layer</b> in the list to stack equipment, then re-select a base to dress it up.</div>
      <div id="animStructPanel" hidden></div>`;

    this.canvas = this.root.querySelector<HTMLCanvasElement>("#animCanvas")!;
    this.ctx = this.canvas.getContext("2d")!;
    this.wireControls();
  }

  private wireControls() {
    const $ = <T extends HTMLElement>(id: string) => this.root.querySelector<T>(id)!;
    $("#animPlay").addEventListener("click", () => {
      this.playing = !this.playing;
      ($("#animPlay") as HTMLButtonElement).textContent = this.playing ? "\u23F8 Pause" : "\u25B6 Play";
      this.lastTs = 0;
    });
    const scrub = $("#animScrub") as HTMLInputElement;
    scrub.addEventListener("input", () => {
      this.playing = false;
      ($("#animPlay") as HTMLButtonElement).textContent = "\u25B6 Play";
      this.frame = parseInt(scrub.value, 10) || 0;
      this.draw();
      this.updateFrameNo();
    });
    ($("#animFps") as HTMLInputElement).addEventListener("input", (e) => {
      this.fps = Math.max(1, parseInt((e.target as HTMLInputElement).value, 10) || 12);
    });
    ($("#animZoom") as HTMLInputElement).addEventListener("input", (e) => {
      this.zoom = parseFloat((e.target as HTMLInputElement).value) || 1;
      this.draw();
    });
    ($("#animLoop") as HTMLInputElement).addEventListener("change", (e) => {
      this.loop = (e.target as HTMLInputElement).checked;
    });
    $("#animFit").addEventListener("click", () => {
      this.panX = 0;
      this.panY = 0;
      this.zoom = 1;
      const zi = this.root.querySelector<HTMLInputElement>("#animZoom");
      if (zi) zi.value = "1";
      this.fit();
      this.draw();
    });
    ($("#animBg") as HTMLSelectElement).addEventListener("change", (e) => {
      this.bg = (e.target as HTMLSelectElement).value as "checker" | "dark" | "light";
      const stage = this.root.querySelector<HTMLElement>(".anim-stage");
      if (stage) stage.className = `anim-stage bg-${this.bg}`;
      this.draw();
    });
    ($("#animSymbol") as HTMLSelectElement).addEventListener("change", (e) => {
      const id = parseInt((e.target as HTMLSelectElement).value, 10);
      void this.loadSymbol(id);
    });
    $("#animStruct").addEventListener("click", () => void this.toggleStructure());
    const onion = this.root.querySelector<HTMLInputElement>("#animOnion");
    onion?.addEventListener("change", () => {
      this.onion = onion.checked;
      this.draw();
    });
    this.root.querySelector<HTMLButtonElement>("#animExportFrame")?.addEventListener("click", () =>
      this.exportFrame()
    );
    this.root.querySelector<HTMLButtonElement>("#animExportSheet")?.addEventListener("click", () =>
      this.exportSheet()
    );
    const clear = this.root.querySelector<HTMLButtonElement>("#animClearLayers");
    if (clear && this.clearEquip) clear.addEventListener("click", this.clearEquip);

    // Drag-to-pan.
    let dragging = false;
    let sx = 0;
    let sy = 0;
    this.canvas.addEventListener("pointerdown", (e) => {
      dragging = true;
      sx = e.clientX;
      sy = e.clientY;
      this.canvas.setPointerCapture(e.pointerId);
    });
    this.canvas.addEventListener("pointermove", (e) => {
      if (!dragging) return;
      this.panX += e.clientX - sx;
      this.panY += e.clientY - sy;
      sx = e.clientX;
      sy = e.clientY;
      this.draw();
    });
    this.canvas.addEventListener("pointerup", () => (dragging = false));

    // Scroll-to-zoom, anchored so zooming feels centered.
    this.canvas.addEventListener(
      "wheel",
      (e) => {
        e.preventDefault();
        const factor = e.deltaY < 0 ? 1.1 : 1 / 1.1;
        this.zoom = Math.max(0.1, Math.min(6, this.zoom * factor));
        const zi = this.root.querySelector<HTMLInputElement>("#animZoom");
        if (zi) zi.value = this.zoom.toFixed(2);
        this.draw();
      },
      { passive: false }
    );
  }

  private async toggleStructure() {
    const panel = this.root.querySelector<HTMLElement>("#animStructPanel")!;
    if (!panel.hidden) {
      panel.hidden = true;
      return;
    }
    panel.hidden = false;
    panel.innerHTML = `<div class="loading">Decoding structure \u2026</div>`;
    try {
      const doc = await getAnimation(this.base!.jar, this.base!.path);
      panel.innerHTML = this.structureHTML(doc);
    } catch (err) {
      panel.innerHTML = `<div class="preview-error"><div class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
    }
  }

  private structureHTML(doc: AnimationDoc): string {
    const chips = Object.entries(doc.counts)
      .sort((a, b) => b[1] - a[1])
      .map(([name, n]) => `<span class="tb-badge">${esc(name)} \u00D7${n}</span>`)
      .join(" ");
    const rows = doc.tags
      .map(
        (t, i) =>
          `<tr><td>${i}</td><td>${t.code}</td><td>${esc(t.name)}</td><td>${
            t.hasId ? t.id : "\u2014"
          }</td><td>${t.length}</td></tr>`
      )
      .join("");
    return `
      <div class="anim-chips">${chips}</div>
      <div class="table-host"><div class="tb-scroll" style="max-height: 260px">
        <table class="tb"><thead><tr><th>#</th><th>code</th><th>tag</th><th>id</th><th>bytes</th></tr></thead>
        <tbody>${rows}</tbody></table>
      </div></div>`;
  }

  private fit() {
    if (!this.pb) return;
    const [minX, minY, maxX, maxY] = this.pb.bounds;
    const cw = this.canvas.width;
    const ch = this.canvas.height;
    const contentW = Math.max(1, maxX - minX);
    const contentH = Math.max(1, maxY - minY);
    const pad = 0.9;
    const s = Math.min(cw / contentW, ch / contentH) * pad;
    this.baseScale = s;
    this.baseCenterX = (minX + maxX) / 2;
    this.baseCenterY = (minY + maxY) / 2;
  }

  private baseScale = 1;
  private baseCenterX = 0;
  private baseCenterY = 0;

  private draw() {
    const pb = this.pb;
    if (!pb || !this.ctx) return;
    const ctx = this.ctx;
    const cw = this.canvas.width;
    const ch = this.canvas.height;
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, cw, ch);
    this.drawCheckerboard(ctx, cw, ch);

    const vs = this.baseScale * this.zoom;
    const vx = cw / 2 - vs * this.baseCenterX + this.panX;
    const vy = ch / 2 + vs * this.baseCenterY + this.panY;

    // Onion-skinning: ghost the neighbouring frames so motion is visible.
    if (this.onion) {
      const prev = pb.frames[this.frame - 1];
      const next = pb.frames[this.frame + 1];
      if (prev) this.drawFrameOps(ctx, prev, vs, vx, vy, 0.28, "#ff5f7a");
      if (next) this.drawFrameOps(ctx, next, vs, vx, vy, 0.28, "#5fd0ff");
    }

    const frame = pb.frames[this.frame];
    if (frame) this.drawFrameOps(ctx, frame, vs, vx, vy, 1, null);

    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.globalAlpha = 1;
  }

  // drawFrameOps paints one frame's ops with a global alpha (for onion-skin
  // ghosts) and an optional flat tint color (to color-code prev/next ghosts).
  // The view transform mirrors the client's Y-up->Y-down single flip.
  private drawFrameOps(
    ctx: CanvasRenderingContext2D,
    frame: { ops: PlayerOp[] },
    vs: number,
    vx: number,
    vy: number,
    globalAlpha: number,
    ghostColor: string | null
  ) {
    for (const op of frame.ops) {
      const img = this.images.get(op.bitmapId);
      if (!img) continue;
      const [a, b, c, d, e, f] = op.matrix;
      ctx.setTransform(vs * a, -vs * b, vs * c, -vs * d, vs * e + vx, -vs * f + vy);
      const src = ghostColor ? this.silhouette(op, img, ghostColor) : this.tint(op, img);
      const alpha = Math.max(0, Math.min(1, op.colorMul[3])) * globalAlpha;
      ctx.globalAlpha = alpha;
      ctx.drawImage(src, 0, 0);
    }
  }

  // silhouette renders a flat-colored ghost of a bitmap (its alpha shape filled
  // with `color`) for onion-skin frames. Cached by bitmapId+color.
  private silCache = new Map<string, HTMLCanvasElement>();
  private silhouette(op: PlayerOp, img: HTMLImageElement, color: string): CanvasImageSource {
    const key = `${op.bitmapId}|${color}`;
    const cached = this.silCache.get(key);
    if (cached) return cached;
    const w = img.naturalWidth || img.width;
    const h = img.naturalHeight || img.height;
    const off = document.createElement("canvas");
    off.width = w;
    off.height = h;
    const octx = off.getContext("2d")!;
    octx.drawImage(img, 0, 0);
    octx.globalCompositeOperation = "source-in";
    octx.fillStyle = color;
    octx.fillRect(0, 0, w, h);
    if (this.silCache.size > 256) this.silCache.clear();
    this.silCache.set(key, off);
    return off;
  }

  // exportFrame downloads the current frame exactly as shown on the stage.
  private exportFrame() {
    if (!this.pb) return;
    const name = `${this.animBaseName()}_frame${this.frame + 1}.png`;
    this.canvas.toBlob((b) => b && this.download(b, name), "image/png");
  }

  // exportSheet renders EVERY frame into one tightly-packed sprite sheet PNG,
  // using a shared, content-fit cell so frames line up. Great for importing an
  // animation into an external editor or documenting it.
  private async exportSheet() {
    const pb = this.pb;
    if (!pb || pb.frames.length === 0) return;
    // Fit all frames into a common cell using the playback bounds.
    const [minX, minY, maxX, maxY] = pb.bounds;
    const bw = Math.max(1, maxX - minX);
    const bh = Math.max(1, maxY - minY);
    const pad = 6;
    const cellW = Math.ceil(bw + pad * 2);
    const cellH = Math.ceil(bh + pad * 2);
    const n = pb.frames.length;
    const cols = Math.min(n, Math.ceil(Math.sqrt(n)));
    const rows = Math.ceil(n / cols);

    const sheet = document.createElement("canvas");
    sheet.width = cols * cellW;
    sheet.height = rows * cellH;
    const sctx = sheet.getContext("2d")!;

    // Center each frame in its cell using the bounds->cell mapping (no zoom).
    const cx = -minX + pad;
    const cy = maxY + pad; // Y flip: worldY maps to (maxY - y) downwards
    for (let i = 0; i < n; i++) {
      const col = i % cols;
      const row = Math.floor(i / cols);
      const ox = col * cellW + cx;
      const oy = row * cellH + cy;
      // Draw with vs=1, view (ox, oy) and the same single Y flip as draw().
      this.drawFrameOps(sctx, pb.frames[i], 1, ox, oy, 1, null);
      sctx.setTransform(1, 0, 0, 1, 0, 0);
      sctx.globalAlpha = 1;
    }
    const name = `${this.animBaseName()}_sheet_${cols}x${rows}.png`;
    sheet.toBlob((b) => b && this.download(b, name), "image/png");
  }

  private animBaseName(): string {
    const p = this.pb?.path ?? "anim";
    return p.replace(/[^a-z0-9]+/gi, "_").replace(/^_+|_+$/g, "") || "anim";
  }

  private download(blob: Blob, name: string) {
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = name;
    a.click();
    setTimeout(() => URL.revokeObjectURL(url), 1000);
  }

  // tint applies an op's RGB color transform (mult + add) to a bitmap via an
  // offscreen canvas, caching the result. Ops with an identity RGB transform
  // (the common case) return the source image untouched. Alpha is handled by
  // globalAlpha at draw time, so it's excluded here.
  private tintCache = new Map<string, HTMLCanvasElement>();
  private tint(op: PlayerOp, img: HTMLImageElement): CanvasImageSource {
    const [rm, gm, bm] = op.colorMul;
    const [ra, ga, ba] = op.colorAdd;
    const isIdentity =
      Math.abs(rm - 1) < 1e-3 &&
      Math.abs(gm - 1) < 1e-3 &&
      Math.abs(bm - 1) < 1e-3 &&
      ra === 0 &&
      ga === 0 &&
      ba === 0;
    if (isIdentity) return img;

    const key = `${op.bitmapId}|${rm.toFixed(3)},${gm.toFixed(3)},${bm.toFixed(
      3
    )},${ra},${ga},${ba}`;
    const cached = this.tintCache.get(key);
    if (cached) return cached;

    const w = img.naturalWidth || img.width;
    const h = img.naturalHeight || img.height;
    const off = document.createElement("canvas");
    off.width = w;
    off.height = h;
    const octx = off.getContext("2d")!;
    octx.drawImage(img, 0, 0);
    const id = octx.getImageData(0, 0, w, h);
    const px = id.data;
    for (let i = 0; i < px.length; i += 4) {
      px[i] = Math.max(0, Math.min(255, px[i] * rm + ra));
      px[i + 1] = Math.max(0, Math.min(255, px[i + 1] * gm + ga));
      px[i + 2] = Math.max(0, Math.min(255, px[i + 2] * bm + ba));
    }
    octx.putImageData(id, 0, 0);
    if (this.tintCache.size > 256) this.tintCache.clear();
    this.tintCache.set(key, off);
    return off;
  }

  private drawCheckerboard(ctx: CanvasRenderingContext2D, w: number, h: number) {
    if (this.bg === "dark") {
      ctx.fillStyle = "#0d0f14";
      ctx.fillRect(0, 0, w, h);
      return;
    }
    if (this.bg === "light") {
      ctx.fillStyle = "#d9dde6";
      ctx.fillRect(0, 0, w, h);
      return;
    }
    const s = 12;
    for (let y = 0; y < h; y += s) {
      for (let x = 0; x < w; x += s) {
        ctx.fillStyle = ((x / s + y / s) & 1) === 0 ? "#1b1d24" : "#22242c";
        ctx.fillRect(x, y, s, s);
      }
    }
  }

  private updateFrameNo() {
    const el = this.root.querySelector<HTMLElement>("#animFrameNo");
    const scrub = this.root.querySelector<HTMLInputElement>("#animScrub");
    if (el && this.pb) el.textContent = `${this.frame + 1} / ${this.pb.frames.length}`;
    if (scrub) scrub.value = String(this.frame);
  }

  private start() {
    this.stop();
    this.lastTs = 0;
    const tick = (ts: number) => {
      this.raf = requestAnimationFrame(tick);
      const pb = this.pb;
      if (!pb || pb.frames.length === 0) return;
      if (this.lastTs === 0) this.lastTs = ts;
      const dt = ts - this.lastTs;
      this.lastTs = ts;
      if (this.playing) {
        this.accum += dt;
        const frameDur = Math.max(1, pb.frames[this.frame]?.duration || 1);
        const msPerFrame = (1000 / this.fps) * frameDur;
        while (this.accum >= msPerFrame) {
          this.accum -= msPerFrame;
          this.frame++;
          if (this.frame >= pb.frames.length) {
            if (this.loop) this.frame = 0;
            else {
              this.frame = pb.frames.length - 1;
              this.playing = false;
              const pbtn = this.root.querySelector<HTMLButtonElement>("#animPlay");
              if (pbtn) pbtn.textContent = "\u25B6 Play";
            }
          }
        }
        this.updateFrameNo();
      }
      this.draw();
    };
    this.raf = requestAnimationFrame(tick);
  }

  private stop() {
    if (this.raf) cancelAnimationFrame(this.raf);
    this.raf = 0;
  }
}
