// Phase 4 isometric map viewer. Renders parsed map cells on a Canvas using
// the client's exact isometric projection (IsoWorldScene: px=(x-y)*43,
// py=-(x+y)*21.5), colouring diamonds by walkability / altitude, overlaying
// fight-start & coach-start markers, with pan/zoom, toggleable overlays and
// a hover inspector. Pure viewer (no editing) -- editing is Phase 7.

import {
  listMaps,
  getMap,
  getSpecialCells,
  saveSpecialCells,
  exportMapToClientJar,
  getMapRender,
  getMapGfxBatch,
  listClientMaps,
  importMapFromClient,
  type MapData,
  type MapCell,
  type MapSummary,
  type SpecialCellDTO,
  type MapRender,
  type MapDrawable,
} from "./backend";
import { t } from "./i18n";

// pendingMapId lets a cross-link (e.g. a Diagnostics "Fight map 5" issue)
// deep-link straight to a specific map: main.ts calls focusMap(id) when
// navigating to the Maps view, and viewMaps selects that map after loading
// instead of defaulting to the first one. Consumed once, then cleared.
let pendingMapId: number | null = null;

// focusMap requests that the Maps view select map `id` on its next mount.
export function focusMap(id: number): void {
  pendingMapId = id;
}

// Special-cell types (match gamedata.SpecialCellTypeName) + a paint colour.
// `base` is the client render-template id (staticEffects.dat SPECIAL area id),
// which equals the magnitude of the tile's baked negative Bonus gfx and maps
// 1:1 to the gameplay effect -- see gamedata.specialCellBaseIDToType.
const SPECIAL_TYPES: { type: string; label: string; color: string; base: number }[] = [
  { type: "killer", label: "Killer", color: "#c0392b", base: 1002 },
  { type: "trap", label: "Trap", color: "#ff6b6b", base: 1003 },
  { type: "eagle_eye", label: "Eagle Eye", color: "#7c5cff", base: 1004 },
  { type: "shield", label: "Shield", color: "#4f9dff", base: 1005 },
  { type: "panacea", label: "Panacea", color: "#3ecf8e", base: 1006 },
  { type: "enthusiasm", label: "Enthusiasm", color: "#ffb454", base: 1007 },
  { type: "motivation", label: "Motivation", color: "#e6c84f", base: 1008 },
  { type: "healing_heart", label: "Healing Heart", color: "#ff8fb0", base: 1009 },
];

// Client projection constants (see project memory / IsoWorldScene).
const TILE_HX = 43; // half-width of a cell diamond in px (cellWidth/2)
const TILE_HY = 21.5; // half-height (cellHeight/2)
// Client's IsoWorldScene elevation unit: each altitude unit lifts a sprite
// this many screen pixels (DEFAULT_ELEVATION_UNIT = 10.0).
const ELEVATION_UNIT = 10;

interface ViewState {
  zoom: number;
  panX: number;
  panY: number;
  overlays: {
    tiles: boolean;
    walkable: boolean;
    nonwalkable: boolean;
    altitude: boolean;
    los: boolean;
    markers: boolean;
    grid: boolean;
    debug: boolean;
  };
  hover: MapCell | null;
  // editing
  editMode: boolean;
  paintType: string; // active special-cell type
  special: SpecialCellDTO[];
  dirty: boolean;
  // tile rendering
  render: MapRender | null;
  gfx: Map<number, HTMLImageElement>; // gfxId -> decoded sprite image
  tilesLoading: boolean;
}

function specialColor(type: string): string {
  return SPECIAL_TYPES.find((s) => s.type === type)?.color ?? "#fff";
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

export function viewMaps(container: HTMLElement) {
  container.innerHTML = `
    <div class="page-head"><h1>${t("nav.maps")}</h1><span class="sub">${t("view.maps.sub")}</span></div>
    <div class="map-layout">
      <div class="map-list" id="mapList"><div class="loading">Loading maps\u2026</div></div>
      <div class="map-stage">
        <div class="map-controls" id="mapControls"></div>
        <canvas id="mapCanvas"></canvas>
        <div class="map-hud" id="mapHud"></div>
      </div>
    </div>`;

  const listEl = container.querySelector<HTMLElement>("#mapList")!;
  const controls = container.querySelector<HTMLElement>("#mapControls")!;
  const canvas = container.querySelector<HTMLCanvasElement>("#mapCanvas")!;
  const hud = container.querySelector<HTMLElement>("#mapHud")!;

  const view: ViewState = {
    zoom: 1,
    panX: 0,
    panY: 0,
    overlays: { tiles: true, walkable: false, nonwalkable: false, altitude: false, los: false, markers: true, grid: false, debug: false },
    hover: null,
    editMode: false,
    paintType: SPECIAL_TYPES[0].type,
    special: [],
    dirty: false,
    render: null,
    gfx: new Map(),
    tilesLoading: false,
  };
  let mapData: MapData | null = null;
  // Lazy (x,y)->standingAlt lookup for lifting overlays onto the tile
  // surface; rebuilt whenever a new map is loaded (see altAt / where mapData
  // is assigned).
  let altLookup: Map<number, number> | null = null;

  listMaps()
    .then((maps) => {
      drawList(maps);
      // Honor a deep-link target (Diagnostics jump) if it exists in this map
      // set, else default to the first map. Consumed once.
      const want = pendingMapId;
      pendingMapId = null;
      const target = want != null && maps.some((m) => m.id === want) ? want : maps[0]?.id;
      if (target != null) select(target);
    })
    .catch((err) => {
      listEl.innerHTML = `<div class="preview-error"><b>No maps.</b><div class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
    });

  let allMaps: MapSummary[] = [];
  let mapFilter = "";
  let selectedMapId = -1;

  function drawList(maps: MapSummary[]) {
    allMaps = maps;
    const q = mapFilter.trim().toLowerCase();
    const list = q
      ? maps.filter(
          (m) =>
            String(m.id).includes(q) ||
            (m.isFight ? "fight" : "world").includes(q) ||
            (m.id === 0 ? "overworld" : m.id === 1 ? "world" : "").includes(q)
        )
      : maps;
    listEl.innerHTML =
      `<div class="map-list-toolbar">
         <div class="tb-search-wrap">
           <span class="tb-search-ico">\u2315</span>
           <input class="tb-search" id="mapFilter" placeholder="Filter ${maps.length} maps\u2026" value="${esc(
        mapFilter
      )}" />
         </div>
       </div>` +
      list
        .map(
          (m) => `
        <div class="map-item ${m.id === selectedMapId ? "active" : ""}" data-id="${m.id}">
          <div class="map-item-id">Map ${m.id}${m.id === 0 ? " \u00b7 Overworld" : m.id === 1 ? " \u00b7 World" : ""}</div>
          <div class="map-item-meta">${m.cellCount} cells ${
            m.isFight
              ? '<span class="tb-badge on">fight</span>'
              : '<span class="tb-badge">world</span>'
          }</div>
        </div>`
        )
        .join("") +
      `<div class="map-import"><button data-import>Import from client\u2026</button></div>`;
    const fi = listEl.querySelector<HTMLInputElement>("#mapFilter");
    fi?.addEventListener("input", () => {
      mapFilter = fi.value;
      const pos = fi.selectionStart;
      drawList(allMaps);
      const ni = listEl.querySelector<HTMLInputElement>("#mapFilter");
      ni?.focus();
      if (ni && pos != null) ni.setSelectionRange(pos, pos);
    });
    listEl.querySelectorAll<HTMLElement>("[data-id]").forEach((el) => {
      el.addEventListener("click", () => select(Number(el.dataset.id)));
    });
    listEl.querySelector<HTMLElement>("[data-import]")?.addEventListener("click", onImportFromClient);
  }

  // onImportFromClient lists maps in the client data.jar (incl. the non-fight
  // world/island maps 0 & 1) and imports the chosen one into local data/maps.
  function onImportFromClient() {
    listClientMaps()
      .then((cmaps) => {
        const importable = cmaps.filter((c) => !c.alreadyLocal);
        if (importable.length === 0) {
          alert("All client maps are already imported locally.");
          return;
        }
        const lines = importable
          .map((c) => `  ${c.id}${c.id === 0 ? " (Overworld)" : c.id === 1 ? " (World)" : ""} \u2014 ${c.chunkCount} chunks`)
          .join("\n");
        const pick = prompt(`Import which map id from the client?\n\nAvailable:\n${lines}`, String(importable[0].id));
        if (pick == null) return;
        const id = Number(pick);
        importMapFromClient(id)
          .then((n) => {
            alert(`Imported map ${id} (${n} chunks) into data/maps/${id}.`);
            listMaps().then((maps) => {
              drawList(maps);
              select(id);
            });
          })
          .catch((err) => alert(`Import failed: ${(err as Error).message}`));
      })
      .catch((err) => alert(`Could not list client maps: ${(err as Error).message}`));
  }

  function select(id: number) {
    selectedMapId = id;
    listEl.querySelectorAll<HTMLElement>("[data-id]").forEach((el) => {
      el.classList.toggle("active", Number(el.dataset.id) === id);
    });
    hud.innerHTML = "";
    view.special = [];
    view.dirty = false;
    view.render = null;
    view.gfx = new Map();
    view.tilesLoading = true;
    Promise.all([getMap(id), getSpecialCells(id).catch(() => [] as SpecialCellDTO[])])
      .then(([md, special]) => {
        mapData = md;
        altLookup = null; // rebuilt lazily by altAt for the new map
        view.special = special;
        drawControls();
        resetView();
        resize();
        render();
        loadTiles(id); // async: decode tile sprites, then re-render
      })
      .catch((err) => {
        hud.innerHTML = `<div class="preview-error">${esc((err as Error).message)}</div>`;
      });
  }

  // loadTiles fetches the map's drawable list + batch-decodes its gfx sprites,
  // building an HTMLImageElement per gfx id for fast canvas drawing.
  function loadTiles(id: number) {
    getMapRender(id)
      .then(async (mr) => {
        view.render = mr;
        const batch = await getMapGfxBatch(mr.gfxIds);
        await Promise.all(
          batch.map(
            (g) =>
              new Promise<void>((resolve) => {
                const img = new Image();
                img.onload = () => {
                  view.gfx.set(g.gfxId, img);
                  resolve();
                };
                img.onerror = () => resolve();
                img.src = g.dataUrl;
              })
          )
        );
        view.tilesLoading = false;
        drawControls();
        render();
      })
      .catch(() => {
        // Tile rendering is best-effort; fall back to analytical overlays.
        view.tilesLoading = false;
        view.overlays.tiles = false;
        view.overlays.walkable = true;
        drawControls();
        render();
      });
  }

  function drawControls() {
    if (!mapData) return;
    const o = view.overlays;
    const toggle = (k: keyof typeof o, label: string) =>
      `<label class="map-toggle"><input type="checkbox" data-ov="${k}" ${o[k] ? "checked" : ""}/> ${label}</label>`;
    const palette = view.editMode
      ? `<div class="map-palette">${SPECIAL_TYPES.map(
          (s) =>
            `<button class="pal ${s.type === view.paintType ? "active" : ""}" data-pal="${s.type}" style="--pc:${s.color}">${esc(
              s.label
            )}</button>`
        ).join("")}<span class="pal-hint">click a cell to place/remove</span></div>`
      : "";

    controls.innerHTML = `
      <div class="map-title">Map ${mapData.id} \u00B7 ${mapData.cells.length} cells${
      view.dirty ? ' <span class="tb-badge warn">unsaved</span>' : ""
    }</div>
      <div class="map-toggles">
        <label class="map-toggle"><input type="checkbox" data-ov="tiles" ${
          view.overlays.tiles ? "checked" : ""
        }/> <b>Tiles</b>${view.tilesLoading ? ' <span class="tiles-spin">\u25CC</span>' : ""}</label>
        ${toggle("walkable", "Walkable")}
        ${toggle("nonwalkable", "Non-walkable")}
        ${toggle("altitude", "Altitude")}
        ${toggle("los", "LOS blockers")}
        ${toggle("markers", "Start cells")}
        ${toggle("grid", "Grid")}
        ${toggle("debug", "Debug anchors")}
        <label class="map-toggle"><input type="checkbox" id="editToggle" ${
          view.editMode ? "checked" : ""
        }/> <b>Edit special cells</b></label>
      </div>
      <div class="map-zoom">
        <button data-zoom="-1">\u2212</button>
        <button data-zoom="0">Reset</button>
        <button data-zoom="1">+</button>
      </div>
      ${palette}
      ${
        view.editMode
          ? `<div class="map-editbar">
              <span class="edit-count">${view.special.length} special cells</span>
              <button data-act="save">Save JSON</button>
              <button class="primary" data-act="export">Export map \u2192 data.jar</button>
              <span class="edit-status" id="mapStatus"></span>
            </div>`
          : ""
      }`;

    controls.querySelectorAll<HTMLInputElement>("[data-ov]").forEach((cb) => {
      cb.addEventListener("change", () => {
        (view.overlays as any)[cb.dataset.ov!] = cb.checked;
        render();
      });
    });
    controls.querySelectorAll<HTMLButtonElement>("[data-zoom]").forEach((b) => {
      b.addEventListener("click", () => {
        const z = Number(b.dataset.zoom);
        if (z === 0) resetView();
        else view.zoom = Math.min(4, Math.max(0.25, view.zoom * (z > 0 ? 1.2 : 1 / 1.2)));
        render();
      });
    });
    const editToggle = controls.querySelector<HTMLInputElement>("#editToggle");
    editToggle?.addEventListener("change", () => {
      view.editMode = editToggle.checked;
      drawControls();
      render();
    });
    controls.querySelectorAll<HTMLButtonElement>("[data-pal]").forEach((b) => {
      b.addEventListener("click", () => {
        view.paintType = b.dataset.pal!;
        drawControls();
      });
    });
    controls.querySelector<HTMLButtonElement>('[data-act="save"]')?.addEventListener("click", doSave);
    controls
      .querySelector<HTMLButtonElement>('[data-act="export"]')
      ?.addEventListener("click", doExport);
  }

  function status(msg: string, kind: "ok" | "err" | "" = "") {
    const el = controls.querySelector<HTMLElement>("#mapStatus");
    if (el) el.innerHTML = kind ? `<span class="${kind}">${esc(msg)}</span>` : esc(msg);
  }

  function doSave() {
    if (!mapData) return;
    status("Saving\u2026");
    saveSpecialCells(mapData.id, view.special)
      .then(() => {
        view.dirty = false;
        drawControls();
        status("Saved specialcells.json (backup created)", "ok");
      })
      .catch((err) => status((err as Error).message, "err"));
  }

  function doExport() {
    if (!mapData) return;
    status("Exporting map to data.jar\u2026");
    exportMapToClientJar(mapData.id)
      .then((res) => {
        const n = res.replaced?.length ?? 0;
        status(`Exported ${n} chunk(s) to data.jar (backup created)`, "ok");
      })
      .catch((err) => status((err as Error).message, "err"));
  }

  // world (grid) -> screen (pre pan/zoom) projection, in the client's FINAL
  // screen space (Y-down): screenX=(x-y)*hx, screenY=(x+y)*hy. (The client's
  // world space is Y-up = -(x+y)*hy; its camera flips Y to screen. Using the
  // final-screen form directly means larger (x+y) = lower on screen = nearer
  // the camera, so back-to-front painting matches depth.)
  function project(x: number, y: number): { px: number; py: number } {
    return { px: (x - y) * TILE_HX, py: (x + y) * TILE_HY };
  }

  function resetView() {
    if (!mapData) return;
    view.zoom = 1;
    // Center on the map centroid.
    const cx = (mapData.minX + mapData.maxX) / 2;
    const cy = (mapData.minY + mapData.maxY) / 2;
    const c = project(cx, cy);
    view.panX = canvas.clientWidth / 2 - c.px;
    view.panY = canvas.clientHeight / 2 - c.py;
  }

  function resize() {
    const dpr = window.devicePixelRatio || 1;
    const w = canvas.clientWidth;
    const h = canvas.clientHeight;
    canvas.width = Math.floor(w * dpr);
    canvas.height = Math.floor(h * dpr);
    const ctx = canvas.getContext("2d")!;
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
    ctx.imageSmoothingEnabled = true;
    ctx.imageSmoothingQuality = "high";
  }

  // altitude -> colour ramp (blue low -> red high) for the altitude overlay.
  function altColor(alt: number, minA: number, maxA: number): string {
    const t = maxA === minA ? 0.5 : (alt - minA) / (maxA - minA);
    const hue = 220 - t * 220; // 220(blue) -> 0(red)
    return `hsl(${hue}, 65%, 52%)`;
  }

  // drawTiles paints the real map sprites in the client's exact isometric
  // draw order. The client (DisplayedCell.updateDisplayedElements) assigns
  // each sprite a scene z-order of `zValue = -screenY + altitudeOrder`,
  // where screenY = -(x+y)*cellHeight/2, i.e. zValue = (x+y)*TILE_HY +
  // altitudeOrder, and paints ascending (higher zValue = in front).
  // `altitudeOrder` (computed in parser.ResolveCellGfx) is the monotonic
  // per-cell top-altitude order that keeps stacked pieces layered correctly.
  // Elevation: a sprite is lifted `altitude * ELEVATION_UNIT` screen px.
  function drawTiles(ctx: CanvasRenderingContext2D, z: number, ox: number, oy: number) {
    const r = view.render!;
    // Client draw priority (DisplayedCell.updateDisplayedElements):
    //   zValue = -screenY + altitudeOrder = (x+y)*cellHeight/2 + altitudeOrder
    // painted ascending (higher = in front). This is a CONTINUOUS value: a
    // tall element's altitudeOrder (its top altitude) trades off against the
    // (x+y) diagonal depth, so a wall correctly draws in front of a nearer
    // low tile it visually overlaps. Bucketing by (x+y) first breaks exactly
    // that, so we sort purely on the continuous zValue.
    // Draw order = the client's cell scan order + within-cell stacking.
    //   1. isometric depth diagonal (x+y) ascending  -> back rows first
    //   2. within a diagonal, cells further "screen-right" (larger x, i.e.
    //      smaller y) are NEARER the camera, so they paint LATER (on top);
    //      we order by x ascending so screen-left/back paints first.
    //   3. within one cell, by resolved stacking order (altitudeOrder), then
    //      the raw file order.
    // altitudeOrder is (correctly) ~0 for flat ground, so it only matters for
    // genuinely stacked pieces on the SAME cell -- cross-cell ordering is
    // carried entirely by (x+y) then x, which is what the client's nested
    // row/col cell loop does deterministically.
    // Client z-order (DisplayedCell.updateDisplayedElements, verified in
    // tools/zorder-re): each element's scene depth is the SINGLE continuous
    // value
    //   zValue = (-screenY + altitudeOrder) / frustumHeight
    //          = ((x+y)*cellHeight/2 + altitudeOrder) / frustumHeight
    // and DisplayedElementComparator paints ascending (higher zValue in front).
    // This must be ONE continuous key, NOT bucketed by (x+y) then x: bucketing
    // separates cells by diagonal before altitude is considered, so a tall
    // element on a back cell can't correctly interleave in front of a nearer
    // low tile it visually overlaps (the reported overlap bug). The `order`
    // tiebreak reproduces the client's per-cell element insertion order for the
    // (rare) exact-zValue ties.
    const zValue = (d: MapDrawable) => (d.x + d.y) * TILE_HY + d.altitudeOrder;
    const sorted = r.drawables.slice().sort((a, b) => {
      const za = zValue(a);
      const zb = zValue(b);
      if (za !== zb) return za - zb;
      return a.order - b.order;
    });

    for (const d of sorted) {
      const img = view.gfx.get(d.gfxId);
      if (!img) continue; // undecoded/missing gfx -> skip (overlay still shows the cell)
      const { px, py } = project(d.x, d.y);
      // altitude raises the sprite on screen (screen-Y decreases upward).
      const sx = px * z + ox;
      const sy = (py - d.altitude * ELEVATION_UNIT) * z + oy;

      const w = img.width * z;
      const h = img.height * z;
      // OriginX/OriginY is the sprite's hot point (the pixel that sits on the
      // cell anchor); draw the image so that point lands on (sx,sy).
      const dy = sy - d.originY * z;

      // Round to whole device pixels to avoid sub-pixel seams between
      // adjacent tiles.
      const drawX = Math.round(sx - d.originX * z);
      const drawYr = Math.round(dy);
      const wr = Math.round(w);
      const hr = Math.round(h);

      if (d.flip) {
        // The client flips via texture coords only (Mesh2D.computeTextureCoordinate
        // swaps left/right u), which mirrors the PIXELS WITHIN THE SAME quad --
        // it does NOT move the sprite. So a flipped sprite occupies the exact
        // same box as its unflipped counterpart ([drawX, drawX+wr]); we mirror
        // in place about that box (translate to its right edge, scale -1).
        ctx.save();
        ctx.translate(drawX + wr, drawYr);
        ctx.scale(-1, 1);
        ctx.drawImage(img, 0, 0, wr, hr);
        ctx.restore();
      } else {
        ctx.drawImage(img, drawX, drawYr, wr, hr);
      }

      // Debug: draw the sprite's bounding box + its anchor (the cell point
      // the hot point is pinned to) so alignment can be inspected visually.
      if (view.overlays.debug) {
        ctx.strokeStyle = "rgba(255,0,255,0.7)";
        ctx.lineWidth = 1;
        ctx.strokeRect(drawX + 0.5, drawYr + 0.5, wr, hr);
        // anchor = projected cell point at this sprite's altitude
        const ay = (py - d.altitude * ELEVATION_UNIT) * z + oy;
        ctx.fillStyle = "#ffee00";
        ctx.beginPath();
        ctx.arc(sx, ay, 2.5, 0, Math.PI * 2);
        ctx.fill();
      }
    }
  }

  function render() {
    if (!mapData) return;
    const ctx = canvas.getContext("2d")!;
    const w = canvas.clientWidth;
    const h = canvas.clientHeight;
    ctx.clearRect(0, 0, w, h);

    const z = view.zoom;
    const ox = view.panX;
    const oy = view.panY;

    // altitude range for the ramp
    let minA = Infinity,
      maxA = -Infinity;
    for (const c of mapData.cells) {
      if (!c.hasStanding) continue;
      minA = Math.min(minA, c.standingAlt);
      maxA = Math.max(maxA, c.standingAlt);
    }

    // Draw a cell's overlay diamond LIFTED to its standing altitude so it
    // sits ON TOP of the rendered tile surface, exactly like the tile
    // sprites (drawTiles lifts by altitude*ELEVATION_UNIT). Without the lift
    // the coloured walkable/LOS/altitude diamonds float at ground level
    // while the actual walkable surface is drawn `alt*ELEVATION_UNIT` px
    // higher -- the reported "overlay cells are offset above the tiles" gap.
    const drawDiamond = (x: number, y: number, alt: number, fill: string, stroke: string | null) => {
      const { px, py } = project(x, y);
      const sx = px * z + ox;
      const sy = (py - alt * ELEVATION_UNIT) * z + oy;
      const hx = TILE_HX * z;
      const hy = TILE_HY * z;
      ctx.beginPath();
      ctx.moveTo(sx, sy - hy);
      ctx.lineTo(sx + hx, sy);
      ctx.lineTo(sx, sy + hy);
      ctx.lineTo(sx - hx, sy);
      ctx.closePath();
      ctx.fillStyle = fill;
      ctx.fill();
      if (stroke) {
        ctx.strokeStyle = stroke;
        ctx.lineWidth = 1;
        ctx.stroke();
      }
    };

    // --- tile sprites (real gfx), drawn first, back-to-front ---
    if (view.overlays.tiles && view.render) {
      drawTiles(ctx, z, ox, oy);
    }

    // cells overlay (analytical). When tiles are on, only draw diamonds for
    // overlays the user explicitly enabled (so tiles stay visible).
    const wantCellOverlay =
      view.overlays.walkable ||
      view.overlays.nonwalkable ||
      view.overlays.altitude ||
      view.overlays.los ||
      view.overlays.grid;
    if (!view.overlays.tiles || wantCellOverlay) {
      for (const c of mapData.cells) {
        // EMPTY (0-surface) cells are off-map grid slots with no tile. They
        // have nothing to inspect (no walkability, no altitude), and drawing
        // them just scattered floating diamonds across the void (they can't
        // be reliably placed since there's no surface to sit on). Skip them
        // entirely for the analytical overlays; only the Grid overlay draws
        // them (as a bare wireframe) if the user explicitly wants the full
        // grid. Real BLOCKED TERRAIN (water/wall) has a surface and is still
        // shown in red.
        const empty = c.surfaceCount === 0;
        if (empty && !view.overlays.grid) continue;

        let fill: string | null = view.overlays.tiles ? null : "rgba(60,70,90,0.35)";
        let stroke: string | null = view.overlays.grid ? "rgba(0,0,0,0.35)" : null;

        if (empty) {
          // grid-only wireframe for off-map slots; no fill.
          fill = null;
        } else if (view.overlays.altitude && c.hasStanding) {
          fill = altColor(c.standingAlt, minA, maxA);
        } else if (view.overlays.walkable && c.walkable) {
          // "Walkable" overlay highlights ONLY walkable cells (green). Blocked
          // cells are shown by the separate "Non-walkable" toggle, not here.
          fill = "rgba(62,207,142,0.45)";
        }

        // Non-walkable filter: show ONLY blocked TERRAIN cells (solid red).
        if (view.overlays.nonwalkable && !empty && !c.walkable) {
          fill = "rgba(224,64,64,0.6)";
        }

        if (view.overlays.los && c.blocksLos) {
          fill = "rgba(255,107,107,0.5)";
        }
        // Lift the diamond to the cell's visual TOP FACE (topAlt) so it sits
        // on the surface of the tile -- including blocked cells (water/wall)
        // whose surface isn't walkable but still sits at a real height. Empty
        // slots have topAlt 0 (nothing to sit on).
        if (fill || stroke) drawDiamond(c.x, c.y, c.topAlt, fill ?? "rgba(0,0,0,0)", stroke);
      }
    }

    // markers
    if (view.overlays.markers) {
      for (const mk of mapData.fightStart) {
        drawDiamond(mk.x, mk.y, altAt(mk.x, mk.y), mk.side === 1 ? "rgba(79,157,255,0.5)" : "rgba(124,92,255,0.5)", "rgba(255,255,255,0.6)");
      }
      for (const mk of mapData.coachStart) {
        const { px, py } = project(mk.x, mk.y);
        const sx = px * z + ox;
        const sy = (py - altAt(mk.x, mk.y) * ELEVATION_UNIT) * z + oy;
        ctx.beginPath();
        ctx.arc(sx, sy, 6 * Math.max(0.6, z), 0, Math.PI * 2);
        ctx.fillStyle = mk.side === 1 ? "#4f9dff" : "#7c5cff";
        ctx.fill();
        ctx.strokeStyle = "#fff";
        ctx.lineWidth = 2;
        ctx.stroke();
      }
    }

    // special cells (edited layer) -- only shown while the "Edit special
    // cells" mode is active; otherwise they're an editing aid, not part of the
    // map, so they must not obscure the tiles.
    if (view.editMode) {
      for (const sc of view.special) {
        const col = specialColor(sc.type);
        drawDiamond(sc.x, sc.y, altAt(sc.x, sc.y), hexWithAlpha(col, 0.72), "#000");
      }
    }

    // hover highlight
    if (view.hover) {
      drawDiamond(view.hover.x, view.hover.y, altAt(view.hover.x, view.hover.y), "rgba(255,255,255,0.25)", "#fff");
    }
  }

  // altAt returns a cell's VISUAL TOP-FACE altitude (topAlt), used to lift
  // overlay diamonds / start markers so they sit ON the top face of the tile,
  // exactly where the client anchors its cell-highlight diamonds
  // (DisplayedElement.getScreenTopY = screenY + (altitude+height)*elevationUnit).
  // renderAlt is only the tile's BASE, so using it made markers float below the
  // visible surface of raised/blocked tiles. Returns 0 for cells not in the map.
  // Backed by a lazily-built lookup so the per-cell scan happens once per loaded
  // map rather than on every draw.
  function altAt(x: number, y: number): number {
    if (!altLookup) {
      altLookup = new Map();
      if (mapData) {
        for (const c of mapData.cells) altLookup.set(c.y * 100000 + c.x, c.topAlt);
      }
    }
    return altLookup.get(y * 100000 + x) ?? 0;
  }

  // hexWithAlpha converts #rrggbb + alpha into an rgba() string.
  function hexWithAlpha(hex: string, a: number): string {
    const m = /^#?([0-9a-f]{6})$/i.exec(hex);
    if (!m) return hex;
    const n = parseInt(m[1], 16);
    return `rgba(${(n >> 16) & 255}, ${(n >> 8) & 255}, ${n & 255}, ${a})`;
  }

  // paintCell places/removes a special cell of the active type at (x,y).
  function paintCell(cell: MapCell) {
    const idx = view.special.findIndex((s) => s.x === cell.x && s.y === cell.y);
    if (idx >= 0) {
      // toggle: same type removes; different type replaces
      if (view.special[idx].type === view.paintType) {
        view.special.splice(idx, 1);
      } else {
        const t = SPECIAL_TYPES.find((s) => s.type === view.paintType)!;
        view.special[idx] = { x: cell.x, y: cell.y, type: t.type, cellBaseId: t.base };
      }
    } else {
      const t = SPECIAL_TYPES.find((s) => s.type === view.paintType)!;
      view.special.push({ x: cell.x, y: cell.y, type: t.type, cellBaseId: t.base });
    }
    view.dirty = true;
    drawControls();
    render();
  }

  // screen -> grid, altitude-aware. Because overlay diamonds/tiles are lifted
  // by their standing altitude, a plain inverse projection would pick the
  // ground-level cell under the cursor, not the RAISED cell the user is
  // actually pointing at. Instead, test the cursor against every cell's
  // LIFTED diamond and return the front-most match (highest depth x+y, then
  // highest altitude) so hovering a raised tile selects that raised tile.
  // Falls back to the flat inverse projection if no lifted diamond contains
  // the point (e.g. cursor over a bare ground cell).
  function pick(clientX: number, clientY: number): MapCell | null {
    if (!mapData) return null;
    const rect = canvas.getBoundingClientRect();
    const sx = clientX - rect.left;
    const sy = clientY - rect.top;
    const z = view.zoom;
    const hx = TILE_HX * z;
    const hy = TILE_HY * z;

    let best: MapCell | null = null;
    let bestKey = -Infinity;
    for (const c of mapData.cells) {
      const alt = c.renderAlt;
      const { px, py } = project(c.x, c.y);
      const cx = px * z + view.panX;
      const cy = (py - alt * ELEVATION_UNIT) * z + view.panY;
      // point-in-diamond: |dx|/hx + |dy|/hy <= 1
      const inside = Math.abs(sx - cx) / hx + Math.abs(sy - cy) / hy <= 1;
      if (!inside) continue;
      // Prefer the front-most cell: larger (x+y) depth wins, then higher
      // altitude (a raised tile drawn on top of the ground beneath it).
      const key = (c.x + c.y) * 1000 + alt;
      if (key > bestKey) {
        bestKey = key;
        best = c;
      }
    }
    if (best) return best;

    // Fallback: flat inverse projection (no lifted diamond matched).
    const px = (sx - view.panX) / z;
    const py = (sy - view.panY) / z;
    const a = px / TILE_HX; // x - y
    const b = py / TILE_HY; // x + y
    const x = Math.round((a + b) / 2);
    const y = Math.round((b - a) / 2);
    return mapData.cells.find((c) => c.x === x && c.y === y) ?? null;
  }

  // interactions: drag to pan, wheel to zoom, hover inspector
  let dragging = false;
  let lastX = 0,
    lastY = 0;
  canvas.addEventListener("mousedown", (e) => {
    if (view.editMode) {
      const cell = pick(e.clientX, e.clientY);
      if (cell) {
        paintCell(cell);
        return; // don't start a pan when painting
      }
    }
    dragging = true;
    lastX = e.clientX;
    lastY = e.clientY;
  });
  window.addEventListener("mouseup", () => (dragging = false));
  canvas.addEventListener("mousemove", (e) => {
    if (dragging) {
      view.panX += e.clientX - lastX;
      view.panY += e.clientY - lastY;
      lastX = e.clientX;
      lastY = e.clientY;
      render();
      return;
    }
    const cell = pick(e.clientX, e.clientY);
    if (cell !== view.hover) {
      view.hover = cell;
      render();
      updateHud(cell);
    }
  });
  canvas.addEventListener(
    "wheel",
    (e) => {
      e.preventDefault();
      const factor = e.deltaY < 0 ? 1.1 : 1 / 1.1;
      view.zoom = Math.min(4, Math.max(0.25, view.zoom * factor));
      render();
    },
    { passive: false }
  );

  function updateHud(cell: MapCell | null) {
    if (!cell) {
      hud.innerHTML = `<div class="hud-hint">Drag to pan \u00B7 wheel to zoom \u00B7 hover a cell</div>`;
      return;
    }
    // Distinguish the three cases so blocked-terrain vs off-map-border is
    // obvious: walkable, blocked (has a non-walkable surface), or empty
    // (no surface at all -- a border slot with no tile).
    const empty = cell.surfaceCount === 0;
    const status = cell.walkable ? "walkable" : empty ? "empty (no tile)" : "blocked";
    const statusClass = cell.walkable ? "on" : "off";
    // Standing alt when walkable; top-surface alt when a non-walkable surface
    // exists; else the neighbour-inherited ground alt for an empty border cell.
    const altLabel = cell.hasStanding
      ? cell.standingAlt
      : cell.surfaceCount > 0
      ? `${cell.renderAlt} (top)`
      : `${cell.renderAlt} (~ground)`;
    hud.innerHTML = `
      <div class="hud-cell">
        <b>(${cell.x}, ${cell.y})</b>
        <span>alt ${altLabel}</span>
        <span class="${statusClass}">${status}</span>
        ${cell.blocksLos ? '<span class="off">LOS</span>' : ""}
        <span>${cell.surfaceCount} surf</span>
      </div>`;
  }

  window.addEventListener("resize", () => {
    resize();
    render();
  });
}
