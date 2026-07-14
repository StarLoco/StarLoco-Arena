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
import { encodeGif, type GifFrame } from "./gifenc";
import { t } from "./i18n";

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
    <div class="page-head"><h1>${t("nav.animations")}</h1><span class="sub">${t("view.animations.sub")}</span></div>
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
            <button class="anim-compare" title="Compare against the current animation (B)" data-jar="${esc(
              a.jar
            )}" data-path="${esc(a.path)}" data-name="${esc(a.name)}">vs B</button>
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
        const t = ev.target as HTMLElement;
        if (t.classList.contains("anim-addlayer") || t.classList.contains("anim-compare")) return;
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
    listEl.querySelectorAll<HTMLButtonElement>(".anim-compare").forEach((btn) => {
      btn.addEventListener("click", (ev) => {
        ev.stopPropagation();
        if (!base) {
          // Nothing loaded yet: treat the clicked item as A instead.
          base = { jar: btn.dataset.jar!, path: btn.dataset.path!, name: btn.dataset.name! };
          open();
          return;
        }
        void player.loadCompare({
          jar: btn.dataset.jar!,
          path: btn.dataset.path!,
          name: btn.dataset.name!,
        });
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

  // A/B compare: an optional second playback overlaid or shown side-by-side,
  // synced to A by progress fraction.
  private pbB: AnimationPlayback | null = null;
  private imagesB = new Map<number, HTMLImageElement>();
  private layerB: Layer | null = null;
  private compareMode: "off" | "overlay" | "side" = "off";
  private compareAlpha = 0.5;

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

  // loadCompare loads a second animation ("B") for A/B comparison and switches
  // to overlay mode. Called by the view when the user picks a compare target.
  async loadCompare(layer: Layer) {
    if (!this.pb) return;
    this.layerB = layer;
    try {
      const pb = await getAnimationPlayback(layer.jar, layer.path, ANIM_DEFAULT_SYMBOL);
      await this.preloadInto(pb.bitmaps, this.imagesB);
      this.pbB = pb;
      if (this.compareMode === "off") this.compareMode = "overlay";
      this.renderShell();
      this.fit();
      this.start();
    } catch {
      this.pbB = null;
      this.layerB = null;
    }
  }

  private clearCompare() {
    this.pbB = null;
    this.layerB = null;
    this.imagesB.clear();
    this.compareMode = "off";
    this.renderShell();
    this.fit();
    this.start();
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
    return this.preloadInto(bitmaps, this.images);
  }

  // preloadInto decodes each bitmap's data URL into the given image map.
  private preloadInto(
    bitmaps: PlayerBitmap[],
    map: Map<number, HTMLImageElement>
  ): Promise<void> {
    map.clear();
    const jobs = bitmaps.map(
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
      <div class="anim-stage bg-${this.bg}" tabindex="0" title="Click here, then use \u2190/\u2192 to step frames, space to play/pause">
        <canvas id="animCanvas" width="640" height="420"></canvas>
      </div>
      <div class="anim-transport">
        <button id="animStepBack" class="anim-step" title="Previous frame (\u2190)">\u23EE</button>
        <button id="animPlay" class="anim-play">${this.playing ? "\u23F8" : "\u25B6"}</button>
        <button id="animStepFwd" class="anim-step" title="Next frame (\u2192)">\u23ED</button>
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
          <button id="animExportGif" title="Download the animation as a looping GIF">\u2B07 GIF</button>
        </div>
        <div class="anim-group">
          ${
            this.pbB
              ? `<label class="anim-ctl">compare
                  <select id="animCompareMode">
                    <option value="overlay" ${this.compareMode === "overlay" ? "selected" : ""}>overlay</option>
                    <option value="side" ${this.compareMode === "side" ? "selected" : ""}>side by side</option>
                    <option value="off" ${this.compareMode === "off" ? "selected" : ""}>off</option>
                  </select></label>
                <label class="anim-ctl">B \u03B1 <input type="range" id="animCompareAlpha" min="0.1" max="1" step="0.05" value="${this.compareAlpha}" ${this.compareMode === "overlay" ? "" : "disabled"} /></label>
                <span class="anim-tag" title="Compared animation (B)">B: ${esc(this.layerB?.name ?? "")}</span>
                <button id="animClearCompare" title="Stop comparing">\u00D7</button>`
              : `<span class="anim-hint-inline">Use <b>vs B</b> in the list to compare two animations.</span>`
          }
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
    $("#animStepBack").addEventListener("click", () => this.stepFrame(-1));
    $("#animStepFwd").addEventListener("click", () => this.stepFrame(1));
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
    this.root.querySelector<HTMLButtonElement>("#animExportGif")?.addEventListener("click", (e) =>
      this.exportGif(e.currentTarget as HTMLButtonElement)
    );
    const clear = this.root.querySelector<HTMLButtonElement>("#animClearLayers");
    if (clear && this.clearEquip) clear.addEventListener("click", this.clearEquip);

    // Keyboard frame stepping, scoped to the view root (dies with the DOM on
    // navigation, so no cross-view listener leak). Left/right step frames;
    // space toggles play. Ignored while typing in a field.
    this.root.addEventListener("keydown", (e) => {
      const t = e.target as HTMLElement;
      if (t && /^(INPUT|SELECT|TEXTAREA)$/.test(t.tagName)) return;
      if (e.key === "ArrowLeft") {
        e.preventDefault();
        this.stepFrame(-1);
      } else if (e.key === "ArrowRight") {
        e.preventDefault();
        this.stepFrame(1);
      } else if (e.key === " ") {
        e.preventDefault();
        this.playing = !this.playing;
        const pbtn = this.root.querySelector<HTMLButtonElement>("#animPlay");
        if (pbtn) pbtn.textContent = this.playing ? "\u23F8 Pause" : "\u25B6 Play";
        this.lastTs = 0;
      }
    });

    // A/B compare controls.
    this.root.querySelector<HTMLSelectElement>("#animCompareMode")?.addEventListener("change", (e) => {
      this.compareMode = (e.target as HTMLSelectElement).value as "off" | "overlay" | "side";
      const alpha = this.root.querySelector<HTMLInputElement>("#animCompareAlpha");
      if (alpha) alpha.disabled = this.compareMode !== "overlay";
      this.draw();
    });
    this.root
      .querySelector<HTMLInputElement>("#animCompareAlpha")
      ?.addEventListener("input", (e) => {
        this.compareAlpha = parseFloat((e.target as HTMLInputElement).value) || 0.5;
        this.draw();
      });
    this.root
      .querySelector<HTMLButtonElement>("#animClearCompare")
      ?.addEventListener("click", () => this.clearCompare());

    // Drag-to-pan.
    let dragging = false;
    let sx = 0;
    let sy = 0;
    this.canvas.addEventListener("pointerdown", (e) => {
      // Focus the stage so arrow-key frame stepping works after interacting.
      this.root.querySelector<HTMLElement>(".anim-stage")?.focus();
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
    // In side-by-side compare, nudge A to the left half of the stage.
    const sideShift = this.pbB && this.compareMode === "side" ? cw / 4 : 0;
    const vx = cw / 2 - vs * this.baseCenterX + this.panX - sideShift;
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

    // A/B compare: render the second animation, synced to A by progress.
    if (this.pbB && this.compareMode !== "off" && this.pbB.frames.length) {
      const frac = pb.frames.length > 1 ? this.frame / (pb.frames.length - 1) : 0;
      const bIdx = Math.min(
        this.pbB.frames.length - 1,
        Math.round(frac * (this.pbB.frames.length - 1))
      );
      const frameB = this.pbB.frames[bIdx];
      if (frameB) {
        if (this.compareMode === "overlay") {
          this.drawFrameOps(ctx, frameB, vs, vx, vy, this.compareAlpha, null, this.imagesB);
        } else {
          // Side-by-side: draw B centered in the right half.
          const bvx = cw / 2 - vs * this.baseCenterX + this.panX + cw / 4;
          this.drawFrameOps(ctx, frameB, vs, bvx, vy, 1, null, this.imagesB);
          // Divider line.
          ctx.setTransform(1, 0, 0, 1, 0, 0);
          ctx.globalAlpha = 0.35;
          ctx.strokeStyle = "#4f9dff";
          ctx.beginPath();
          ctx.moveTo(cw / 2, 0);
          ctx.lineTo(cw / 2, ch);
          ctx.stroke();
        }
      }
    }

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
    ghostColor: string | null,
    imageMap?: Map<number, HTMLImageElement>
  ) {
    const images = imageMap ?? this.images;
    for (const op of frame.ops) {
      const img = images.get(op.bitmapId);
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

  // exportGif renders every frame content-fit onto a fixed offscreen canvas and
  // encodes them into a single looping GIF89a (dependency-free encoder). Per-
  // frame delay honors the playback fps and each frame's own duration.
  private async exportGif(btn?: HTMLButtonElement) {
    const pb = this.pb;
    if (!pb || pb.frames.length === 0) return;
    const label = btn?.textContent ?? "";
    if (btn) {
      btn.disabled = true;
      btn.textContent = "\u2026 GIF";
    }
    // Yield so the button label repaints before the (synchronous) encode.
    await new Promise((r) => setTimeout(r, 0));
    try {
      const [minX, minY, maxX, maxY] = pb.bounds;
      const pad = 4;
      const bw = Math.max(1, maxX - minX);
      const bh = Math.max(1, maxY - minY);
      // Cap dimensions so the GIF stays reasonable; scale to fit.
      const maxDim = 320;
      const scale = Math.min(1, maxDim / Math.max(bw, bh));
      const W = Math.max(1, Math.ceil((bw + pad * 2) * scale));
      const H = Math.max(1, Math.ceil((bh + pad * 2) * scale));

      const off = document.createElement("canvas");
      off.width = W;
      off.height = H;
      const octx = off.getContext("2d", { willReadFrequently: true })!;

      // View maps world -> canvas with the same single Y flip as draw(), scaled.
      const ox = (-minX + pad) * scale;
      const oy = (maxY + pad) * scale;

      const gifFrames: GifFrame[] = [];
      for (let i = 0; i < pb.frames.length; i++) {
        octx.clearRect(0, 0, W, H);
        this.drawFrameOps(octx, pb.frames[i], scale, ox, oy, 1, null);
        octx.setTransform(1, 0, 0, 1, 0, 0);
        octx.globalAlpha = 1;
        const img = octx.getImageData(0, 0, W, H);
        const frameDur = Math.max(1, pb.frames[i]?.duration || 1);
        const delayMs = (1000 / this.fps) * frameDur;
        gifFrames.push({ rgba: img.data, delayMs });
      }

      const bytes = encodeGif(W, H, gifFrames, 0);
      const blob = new Blob([bytes], { type: "image/gif" });
      this.download(blob, `${this.animBaseName()}.gif`);
    } finally {
      if (btn) {
        btn.disabled = false;
        btn.textContent = label;
      }
    }
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

  // stepFrame pauses playback and advances the current frame by delta, wrapping
  // around the clip. Used by the prev/next buttons and the arrow keys for
  // precise frame-by-frame inspection (pairs well with onion-skinning).
  private stepFrame(delta: number) {
    const pb = this.pb;
    if (!pb || pb.frames.length === 0) return;
    if (this.playing) {
      this.playing = false;
      const pbtn = this.root.querySelector<HTMLButtonElement>("#animPlay");
      if (pbtn) pbtn.textContent = "\u25B6 Play";
    }
    const n = pb.frames.length;
    this.frame = ((this.frame + delta) % n + n) % n;
    this.draw();
    this.updateFrameNo();
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
