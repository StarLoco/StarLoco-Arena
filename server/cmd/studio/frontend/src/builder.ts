// Map Builder: create/duplicate fight maps and edit them tile-by-tile.
// Renders the working (unsaved) map live via the backend PreviewMapRender
// (which reuses the exact server-side altitude/gfx resolution), so what you
// paint is what the real renderer shows. Byte-exact save is guaranteed by the
// .amw encoder round-trip (verified in builder_test.go).

import {
  listMaps,
  listPaletteElements,
  getMapEditData,
  saveMapEditData,
  previewMapRender,
  duplicateMap,
  createBlankMap,
  deleteMap,
  getMarkerElements,
  getSprite,
  getMapGfxBatch,
  exportMapToClientJar,
  type MapSummary,
  type PaletteElement,
  type MapEditData,
  type MapEditCell,
  type MapEditElement,
  type MarkerSpec,
} from "./backend";
import {
  project,
  unproject,
  drawTiles,
  drawCellDiamond,
  loadGfxImages,
  TILE_HY,
} from "./isorender";

type Tool = "select" | "paint" | "stack" | "erase" | "marker";

interface BuilderState {
  maps: MapSummary[];
  mapID: number | null;
  edit: MapEditData | null;
  cellIndex: Map<string, MapEditCell>; // "x,y" -> cell (same refs as edit.cells)
  palette: PaletteElement[];
  markers: MarkerSpec[];
  selectedGfxElement: PaletteElement | null;
  selectedMarker: MarkerSpec | null;
  tool: Tool;
  gfx: Map<number, HTMLImageElement>;
  drawables: import("./backend").MapDrawable[];
  zoom: number;
  panX: number;
  panY: number;
  selectedCell: { x: number; y: number } | null;
  hoverCell: { x: number; y: number } | null;
  dirty: boolean;
  showGrid: boolean;
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

const ck = (x: number, y: number) => `${x},${y}`;

export function viewBuilder(container: HTMLElement) {
  const st: BuilderState = {
    maps: [],
    mapID: null,
    edit: null,
    cellIndex: new Map(),
    palette: [],
    markers: [],
    selectedGfxElement: null,
    selectedMarker: null,
    tool: "select",
    gfx: new Map(),
    drawables: [],
    zoom: 1,
    panX: 0,
    panY: 0,
    selectedCell: null,
    hoverCell: null,
    dirty: false,
    showGrid: true,
  };

  container.innerHTML = `
    <div class="page-head"><h1>Map Builder</h1><span class="sub">create &amp; edit fight maps</span></div>
    <div class="builder-toolbar" id="bToolbar"></div>
    <div class="builder-layout">
      <div class="builder-palette" id="bPalette"></div>
      <div class="builder-stage">
        <canvas id="bCanvas"></canvas>
        <div class="builder-hud" id="bHud"></div>
      </div>
      <div class="builder-inspector" id="bInspector"></div>
    </div>`;

  const canvas = container.querySelector<HTMLCanvasElement>("#bCanvas")!;
  const toolbarEl = container.querySelector<HTMLElement>("#bToolbar")!;
  const paletteEl = container.querySelector<HTMLElement>("#bPalette")!;
  const inspectorEl = container.querySelector<HTMLElement>("#bInspector")!;
  const hudEl = container.querySelector<HTMLElement>("#bHud")!;

  // ---------- data loading ----------
  Promise.all([listMaps().catch(() => []), listPaletteElements().catch(() => []), getMarkerElements().catch(() => [])])
    .then(([maps, palette, markers]) => {
      st.maps = maps;
      st.palette = palette;
      st.markers = markers;
      drawToolbar();
      drawPalette();
      if (maps.length) selectMap(maps[0].id);
    })
    .catch((err) => {
      toolbarEl.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
    });

  function selectMap(id: number) {
    if (st.dirty && !confirm("Discard unsaved changes?")) return;
    st.mapID = id;
    st.dirty = false;
    st.selectedCell = null;
    inspectorEl.innerHTML = `<div class="loading">Loading map\u2026</div>`;
    getMapEditData(id)
      .then((ed) => {
        st.edit = ed;
        rebuildIndex();
        drawToolbar();
        resetView();
        refreshPreview(true);
        drawInspector();
      })
      .catch((err) => {
        inspectorEl.innerHTML = `<div class="preview-error">${esc((err as Error).message)}</div>`;
      });
  }

  function rebuildIndex() {
    st.cellIndex = new Map();
    if (!st.edit) return;
    for (const c of st.edit.cells) st.cellIndex.set(ck(c.x, c.y), c);
  }

  // ---------- preview (server-resolved drawables) ----------
  let previewTimer: number | null = null;
  function refreshPreview(immediate = false) {
    if (previewTimer) {
      clearTimeout(previewTimer);
      previewTimer = null;
    }
    const run = () => {
      if (!st.edit) return;
      previewMapRender(st.edit.cells)
        .then(async (mr) => {
          st.drawables = mr.drawables;
          const missing = mr.gfxIds.filter((g) => !st.gfx.has(g));
          if (missing.length) {
            const batch = await getMapGfxBatch(missing);
            const imgs = await loadGfxImages(batch);
            imgs.forEach((v, k) => st.gfx.set(k, v));
          }
          render();
        })
        .catch(() => render());
    };
    if (immediate) run();
    else previewTimer = window.setTimeout(run, 60);
  }

  // ---------- rendering ----------
  function resize() {
    const dpr = window.devicePixelRatio || 1;
    canvas.width = Math.floor(canvas.clientWidth * dpr);
    canvas.height = Math.floor(canvas.clientHeight * dpr);
    const ctx = canvas.getContext("2d")!;
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
  }

  function resetView() {
    if (!st.edit) return;
    st.zoom = 1;
    const cx = (st.edit.minX + st.edit.maxX) / 2;
    const cy = (st.edit.minY + st.edit.maxY) / 2;
    const c = project(cx, cy);
    st.panX = canvas.clientWidth / 2 - c.px;
    st.panY = canvas.clientHeight / 2 - c.py;
  }

  function render() {
    const ctx = canvas.getContext("2d")!;
    ctx.clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);
    if (!st.edit) return;

    // grid (empty cells too, so you can paint onto the whole grid)
    if (st.showGrid) {
      for (const c of st.edit.cells) {
        drawCellDiamond(ctx, c.x, c.y, st.zoom, st.panX, st.panY, null, "rgba(255,255,255,0.06)");
      }
    }

    drawTiles(ctx, st.drawables, st.gfx, st.zoom, st.panX, st.panY);

    // markers overlay: cells containing a fight-start/coach element
    for (const c of st.edit.cells) {
      const m = markerOfCell(c);
      if (m) {
        drawCellDiamond(ctx, c.x, c.y, st.zoom, st.panX, st.panY, hexA(m.color, 0.5), "#fff", 1.5);
      }
    }

    // hover + selection
    if (st.hoverCell) {
      drawCellDiamond(ctx, st.hoverCell.x, st.hoverCell.y, st.zoom, st.panX, st.panY, "rgba(79,157,255,0.18)", "#4f9dff");
    }
    if (st.selectedCell) {
      drawCellDiamond(ctx, st.selectedCell.x, st.selectedCell.y, st.zoom, st.panX, st.panY, null, "#ffd166", 2);
    }
  }

  function markerOfCell(c: MapEditCell): MarkerSpec | null {
    for (const lvl of c.levels) {
      for (const el of lvl) {
        const m = st.markers.find((mk) => mk.element.elementId === el.elementId);
        if (m) return m;
      }
    }
    return null;
  }

  function hexA(hex: string, a: number): string {
    const m = /^#?([0-9a-f]{6})$/i.exec(hex);
    if (!m) return hex;
    const n = parseInt(m[1], 16);
    return `rgba(${(n >> 16) & 255}, ${(n >> 8) & 255}, ${n & 255}, ${a})`;
  }

  // ---------- toolbar ----------
  function drawToolbar() {
    const opts = st.maps
      .map((m) => `<option value="${m.id}" ${m.id === st.mapID ? "selected" : ""}>Map ${m.id} (${m.cellCount})</option>`)
      .join("");
    toolbarEl.innerHTML = `
      <select id="bMapSel" class="pref-select">${opts}</select>
      <button data-act="new">New blank\u2026</button>
      <button data-act="dup">Duplicate\u2026</button>
      <button data-act="del">Delete</button>
      <span class="tb-sep"></span>
      ${tool("select", "Select")}
      ${tool("paint", "Paint")}
      ${tool("stack", "Stack")}
      ${tool("erase", "Erase")}
      <label class="map-toggle"><input type="checkbox" id="bGrid" ${st.showGrid ? "checked" : ""}/> Grid</label>
      <span class="tb-sep"></span>
      <span class="spacer"></span>
      ${st.dirty ? '<span class="tb-badge warn">unsaved</span>' : ""}
      <button class="primary" data-act="save">Save</button>
      <button data-act="export">Export \u2192 jar</button>
      <div class="map-zoom">
        <button data-zoom="-1">\u2212</button>
        <button data-zoom="0">Reset</button>
        <button data-zoom="1">+</button>
      </div>
      <span class="edit-status" id="bStatus"></span>`;

    toolbarEl.querySelector<HTMLSelectElement>("#bMapSel")?.addEventListener("change", (e) => {
      selectMap(Number((e.target as HTMLSelectElement).value));
    });
    toolbarEl.querySelectorAll<HTMLElement>("[data-tool]").forEach((b) =>
      b.addEventListener("click", () => {
        st.tool = b.dataset.tool as Tool;
        drawToolbar();
      })
    );
    toolbarEl.querySelector<HTMLInputElement>("#bGrid")?.addEventListener("change", (e) => {
      st.showGrid = (e.target as HTMLInputElement).checked;
      render();
    });
    toolbarEl.querySelectorAll<HTMLButtonElement>("[data-zoom]").forEach((b) =>
      b.addEventListener("click", () => {
        const z = Number(b.dataset.zoom);
        if (z === 0) resetView();
        else st.zoom = Math.min(4, Math.max(0.25, st.zoom * (z > 0 ? 1.2 : 1 / 1.2)));
        render();
      })
    );
    toolbarEl.querySelector('[data-act="new"]')?.addEventListener("click", onNewBlank);
    toolbarEl.querySelector('[data-act="dup"]')?.addEventListener("click", onDuplicate);
    toolbarEl.querySelector('[data-act="del"]')?.addEventListener("click", onDelete);
    toolbarEl.querySelector('[data-act="save"]')?.addEventListener("click", onSave);
    toolbarEl.querySelector('[data-act="export"]')?.addEventListener("click", onExport);
  }

  function tool(id: Tool, label: string): string {
    return `<button class="btool ${st.tool === id ? "active" : ""}" data-tool="${id}">${esc(label)}</button>`;
  }

  function status(msg: string, kind: "ok" | "err" | "" = "") {
    const el = toolbarEl.querySelector<HTMLElement>("#bStatus");
    if (el) el.innerHTML = kind ? `<span class="${kind}">${esc(msg)}</span>` : esc(msg);
  }

  // ---------- palette ----------
  let paletteFilter = "";
  function drawPalette() {
    const markerBtns = st.markers
      .map(
        (m, i) => `
      <button class="pal-marker ${st.tool === "marker" && st.selectedMarker === m ? "active" : ""}"
        data-marker="${i}" style="--pc:${m.color}">${esc(m.label)}</button>`
      )
      .join("");

    const q = paletteFilter.trim();
    const filtered = q
      ? st.palette.filter((p) => String(p.id).includes(q) || String(p.gfxId).includes(q))
      : st.palette;
    const capped = filtered.slice(0, 600);

    paletteEl.innerHTML = `
      <div class="pane-title">Markers</div>
      <div class="pal-markers">${markerBtns || '<span class="pal-hint">none</span>'}</div>
      <div class="pane-title">Tiles (${filtered.length})</div>
      <input class="tb-search" id="palFilter" placeholder="filter by id / gfx\u2026" value="${esc(paletteFilter)}" />
      <div class="pal-grid" id="palGrid">
        ${capped
          .map(
            (p) => `
          <div class="pal-tile ${st.selectedGfxElement?.id === p.id ? "sel" : ""}" data-el="${p.id}" title="element ${p.id} \u00b7 gfx ${p.gfxId}${p.walkable ? " \u00b7 walkable" : ""}">
            <div class="pal-thumb" data-gfx="${p.gfxId}"><img alt=""/></div>
            <div class="pal-id">#${p.id}</div>
          </div>`
          )
          .join("")}
        ${filtered.length > capped.length ? `<div class="entry-more">\u2026 ${filtered.length - capped.length} more (filter)</div>` : ""}
      </div>`;

    const fi = paletteEl.querySelector<HTMLInputElement>("#palFilter")!;
    fi.addEventListener("input", () => {
      paletteFilter = fi.value;
      const pos = fi.selectionStart;
      drawPalette();
      const ni = paletteEl.querySelector<HTMLInputElement>("#palFilter")!;
      ni.focus();
      if (pos != null) ni.setSelectionRange(pos, pos);
    });
    paletteEl.querySelectorAll<HTMLElement>("[data-el]").forEach((el) =>
      el.addEventListener("click", () => {
        const id = Number(el.dataset.el);
        st.selectedGfxElement = st.palette.find((p) => p.id === id) ?? null;
        st.selectedMarker = null;
        if (st.tool !== "stack") st.tool = "paint";
        drawToolbar();
        drawPalette();
      })
    );
    paletteEl.querySelectorAll<HTMLElement>("[data-marker]").forEach((el) =>
      el.addEventListener("click", () => {
        st.selectedMarker = st.markers[Number(el.dataset.marker)];
        st.selectedGfxElement = null;
        st.tool = "marker";
        drawToolbar();
        drawPalette();
      })
    );
    hydratePaletteThumbs();
  }

  const thumbCache = new Map<number, string>();
  let thumbObserver: IntersectionObserver | null = null;
  function hydratePaletteThumbs() {
    if (!thumbObserver) {
      thumbObserver = new IntersectionObserver(
        (entries) => {
          for (const e of entries) {
            if (!e.isIntersecting) continue;
            const el = e.target as HTMLElement;
            thumbObserver!.unobserve(el);
            const gfxId = Number(el.dataset.gfx);
            const img = el.querySelector("img")!;
            const cached = thumbCache.get(gfxId);
            if (cached) {
              img.src = cached;
              return;
            }
            getSprite(`gfx/${gfxId}.tga`)
              .then((s) => {
                thumbCache.set(gfxId, s.dataUrl);
                img.src = s.dataUrl;
              })
              .catch(() => {});
          }
        },
        { rootMargin: "120px" }
      );
    }
    paletteEl.querySelectorAll<HTMLElement>(".pal-thumb[data-gfx]").forEach((el) => thumbObserver!.observe(el));
  }

  // ---------- inspector ----------
  function drawInspector() {
    if (!st.selectedCell) {
      inspectorEl.innerHTML = `<div class="pane-title">Cell</div><div class="pal-hint" style="padding:12px">Select a cell to inspect its element stack.</div>`;
      return;
    }
    const cell = st.cellIndex.get(ck(st.selectedCell.x, st.selectedCell.y));
    if (!cell) {
      inspectorEl.innerHTML = `<div class="pane-title">Cell</div><div class="pal-hint" style="padding:12px">Cell (${st.selectedCell.x}, ${st.selectedCell.y}) is outside the grid.</div>`;
      return;
    }
    const levelsHtml = cell.levels
      .map(
        (lvl, li) => `
      <div class="insp-level">
        <div class="insp-level-head">Level ${li} <button class="mini" data-dellvl="${li}">\u2715 level</button></div>
        ${lvl
          .map(
            (el, ei) => `
          <div class="insp-el">
            <span class="insp-el-gfx" data-gfx="${el.gfxId}">${el.gfxId ? '<img alt=""/>' : "\u25A0"}</span>
            <span class="insp-el-info">el #${el.elementId}${el.gfxId ? ` \u00b7 gfx ${el.gfxId}` : ""}${el.state ? ` \u00b7 st ${el.state}` : ""}</span>
            <button class="mini" data-del="${li}:${ei}">\u2715</button>
          </div>`
          )
          .join("")}
      </div>`
      )
      .join("");

    inspectorEl.innerHTML = `
      <div class="pane-title">Cell (${cell.x}, ${cell.y})</div>
      <div class="insp-body">
        ${cell.levels.length ? levelsHtml : '<div class="pal-hint">empty cell</div>'}
      </div>
      <div class="insp-actions">
        <button data-clearcell>Clear cell</button>
      </div>`;

    inspectorEl.querySelectorAll<HTMLElement>("[data-del]").forEach((b) =>
      b.addEventListener("click", () => {
        const [li, ei] = b.dataset.del!.split(":").map(Number);
        cell.levels[li].splice(ei, 1);
        if (cell.levels[li].length === 0) cell.levels.splice(li, 1);
        afterEdit();
        drawInspector();
      })
    );
    inspectorEl.querySelectorAll<HTMLElement>("[data-dellvl]").forEach((b) =>
      b.addEventListener("click", () => {
        cell.levels.splice(Number(b.dataset.dellvl), 1);
        afterEdit();
        drawInspector();
      })
    );
    inspectorEl.querySelector<HTMLElement>("[data-clearcell]")?.addEventListener("click", () => {
      cell.levels = [];
      afterEdit();
      drawInspector();
    });

    // hydrate inspector element thumbnails
    inspectorEl.querySelectorAll<HTMLElement>(".insp-el-gfx[data-gfx]").forEach((el) => {
      const gfxId = Number(el.dataset.gfx);
      if (!gfxId) return;
      const img = el.querySelector("img");
      if (!img) return;
      const cached = thumbCache.get(gfxId);
      if (cached) {
        img.src = cached;
        return;
      }
      getSprite(`gfx/${gfxId}.tga`)
        .then((s) => {
          thumbCache.set(gfxId, s.dataUrl);
          img.src = s.dataUrl;
        })
        .catch(() => {});
    });
  }

  function afterEdit() {
    st.dirty = true;
    drawToolbar();
    refreshPreview();
  }

  // ---------- cell editing ----------
  function applyTool(x: number, y: number) {
    const cell = st.cellIndex.get(ck(x, y));
    if (!cell) return;

    if (st.tool === "erase") {
      cell.levels = [];
    } else if (st.tool === "paint" && st.selectedGfxElement) {
      // replace cell content with a single level holding this element
      cell.levels = [[makeGfxElement(st.selectedGfxElement)]];
    } else if (st.tool === "stack" && st.selectedGfxElement) {
      cell.levels.push([makeGfxElement(st.selectedGfxElement)]);
    } else if (st.tool === "marker" && st.selectedMarker) {
      // remove any existing marker on the cell, then add as a new level
      cell.levels = cell.levels.filter((lvl) => !lvl.some((e) => isMarker(e)));
      cell.levels.push([cloneEl(st.selectedMarker.element)]);
    } else {
      return; // select tool: no mutation
    }
    afterEdit();
    if (st.selectedCell && st.selectedCell.x === x && st.selectedCell.y === y) drawInspector();
  }

  function makeGfxElement(p: PaletteElement): MapEditElement {
    return {
      elementId: p.id,
      state: p.defaultState,
      groupId: 0,
      paramsB64: "",
      paramTypes: [],
      gfxId: p.gfxId,
      kind: p.kind,
    };
  }
  function cloneEl(e: MapEditElement): MapEditElement {
    return { ...e, paramTypes: [...e.paramTypes] };
  }
  function isMarker(e: MapEditElement): boolean {
    return st.markers.some((m) => m.element.elementId === e.elementId);
  }

  // ---------- interactions ----------
  function pick(clientX: number, clientY: number): { x: number; y: number } | null {
    const rect = canvas.getBoundingClientRect();
    const sx = clientX - rect.left;
    const sy = clientY - rect.top;
    const px = (sx - st.panX) / st.zoom;
    const py = (sy - st.panY) / st.zoom;
    const g = unproject(px, py);
    return st.cellIndex.has(ck(g.x, g.y)) ? g : null;
  }

  let dragging = false;
  let painting = false;
  let lastX = 0,
    lastY = 0;

  canvas.addEventListener("mousedown", (e) => {
    const cell = pick(e.clientX, e.clientY);
    if (st.tool === "select") {
      if (cell) {
        st.selectedCell = cell;
        drawInspector();
        render();
      }
      dragging = true;
      lastX = e.clientX;
      lastY = e.clientY;
      return;
    }
    // editing tools
    if (cell) {
      painting = true;
      st.selectedCell = cell;
      applyTool(cell.x, cell.y);
      render();
    } else {
      dragging = true;
      lastX = e.clientX;
      lastY = e.clientY;
    }
  });

  window.addEventListener("mouseup", () => {
    dragging = false;
    painting = false;
  });

  canvas.addEventListener("mousemove", (e) => {
    if (dragging) {
      st.panX += e.clientX - lastX;
      st.panY += e.clientY - lastY;
      lastX = e.clientX;
      lastY = e.clientY;
      render();
      return;
    }
    const cell = pick(e.clientX, e.clientY);
    if (painting && cell) {
      applyTool(cell.x, cell.y);
    }
    const changed = (cell?.x ?? -999) !== (st.hoverCell?.x ?? -999) || (cell?.y ?? -999) !== (st.hoverCell?.y ?? -999);
    if (changed) {
      st.hoverCell = cell;
      render();
      hudEl.innerHTML = cell
        ? `<div class="hud-cell"><b>(${cell.x}, ${cell.y})</b> \u00b7 ${st.tool}</div>`
        : `<div class="hud-hint">Select a tool + palette entry, then click/drag to paint</div>`;
    }
  });

  canvas.addEventListener(
    "wheel",
    (e) => {
      e.preventDefault();
      st.zoom = Math.min(4, Math.max(0.25, st.zoom * (e.deltaY < 0 ? 1.1 : 1 / 1.1)));
      render();
    },
    { passive: false }
  );

  // ---------- map ops ----------
  function nextFreeID(): number {
    const used = new Set(st.maps.map((m) => m.id));
    let id = 100;
    while (used.has(id)) id++;
    return id;
  }

  function onNewBlank() {
    const idStr = prompt("New map id:", String(nextFreeID()));
    if (idStr == null) return;
    const sizeStr = prompt("Grid size (cells per side, 1..40):", "15");
    if (sizeStr == null) return;
    const id = Number(idStr);
    const size = Number(sizeStr);
    createBlankMap(id, size)
      .then(() => reloadMapsThenSelect(id))
      .catch((err) => status((err as Error).message, "err"));
  }

  function onDuplicate() {
    if (st.mapID == null) return;
    const idStr = prompt(`Duplicate map ${st.mapID} to new id:`, String(nextFreeID()));
    if (idStr == null) return;
    const id = Number(idStr);
    duplicateMap(st.mapID, id)
      .then(() => reloadMapsThenSelect(id))
      .catch((err) => status((err as Error).message, "err"));
  }

  function onDelete() {
    if (st.mapID == null) return;
    if (!confirm(`Delete map ${st.mapID}? (built-in maps 2..16 are protected)`)) return;
    const id = st.mapID;
    deleteMap(id)
      .then(() => {
        st.dirty = false;
        return listMaps();
      })
      .then((maps) => {
        st.maps = maps;
        if (maps.length) selectMap(maps[0].id);
        else {
          st.edit = null;
          render();
          drawToolbar();
        }
      })
      .catch((err) => status((err as Error).message, "err"));
  }

  function reloadMapsThenSelect(id: number) {
    st.dirty = false;
    listMaps().then((maps) => {
      st.maps = maps;
      selectMap(id);
    });
  }

  function onSave() {
    if (st.mapID == null || !st.edit) return;
    status("Saving\u2026");
    saveMapEditData(st.mapID, st.edit.cells)
      .then((res) => {
        st.dirty = false;
        drawToolbar();
        status(`Saved ${res.length} chunk(s) to data/maps/${st.mapID} (backup created)`, "ok");
      })
      .catch((err) => status((err as Error).message, "err"));
  }

  function onExport() {
    if (st.mapID == null) return;
    if (st.dirty && !confirm("Save first? Export uses the on-disk map. Continue exporting current saved version?")) return;
    status("Exporting to data.jar\u2026");
    exportMapToClientJar(st.mapID)
      .then((r) => status(`Exported ${r.replaced?.length ?? 0} chunk(s) to data.jar`, "ok"))
      .catch((err) => status((err as Error).message, "err"));
  }

  window.addEventListener("resize", () => {
    resize();
    render();
  });
  // initial canvas sizing once laid out
  requestAnimationFrame(() => {
    resize();
    render();
  });
}
