// Phase 3 sprite gallery: lists gfx.jar's .tga sprites and lazily decodes
// each (Go-side TGA->PNG) as its thumbnail scrolls into view, so opening the
// gallery stays instant even with 500+ sprites. Clicking a tile opens a
// larger inspector with dimensions + the source path.

import { listSprites, getSprite, type SpriteInfo } from "./backend";
import { t } from "./i18n";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// Simple in-session cache of decoded data URLs so re-scrolling is instant.
const spriteCache = new Map<string, string>();

export function viewSprites(container: HTMLElement) {
  container.innerHTML = `
    <div class="page-head"><h1>${esc(t("view.sprites.title"))}</h1><span class="sub">${esc(t("view.sprites.sub"))}</span></div>
    <div class="loading">${esc(t("common.loading"))}</div>`;

  listSprites()
    .then((sprites) => render(container, sprites))
    .catch((err) => {
      container.innerHTML =
        `<div class="page-head"><h1>${esc(t("view.sprites.title"))}</h1><span class="sub">gfx.jar</span></div>` +
        `<div class="placeholder"><div class="big">\u26A0</div><div>${esc(t("common.couldNotLoad", { what: t("view.sprites.title").toLowerCase() }))}</div><div class="mono" style="margin-top:6px;font-size:12.5px">${esc(
          (err as Error).message
        )}</div></div>`;
    });
}

function render(container: HTMLElement, sprites: SpriteInfo[]) {
  let filter = "";
  let tileSize = 128;

  container.innerHTML = `
    <div class="page-head"><h1>${esc(t("view.sprites.title"))}</h1><span class="sub">${esc(t("view.sprites.count", { n: sprites.length }))}</span></div>
    <div class="sprite-toolbar">
      <div class="tb-search-wrap">
        <span class="tb-search-ico">\u2315</span>
        <input class="tb-search" id="spriteFilter" placeholder="Filter by id / name\u2026" />
      </div>
      <span class="tb-count"><b id="spriteCount">${sprites.length}</b> / ${sprites.length}</span>
      <label class="sprite-zoom">
        <span>size</span>
        <input type="range" id="spriteZoom" min="72" max="220" step="4" value="${tileSize}" />
      </label>
    </div>
    <div class="sprite-grid" id="spriteGrid"></div>
    <div class="sprite-inspector" id="spriteInspector" hidden></div>`;

  const grid = container.querySelector<HTMLElement>("#spriteGrid")!;
  const countEl = container.querySelector<HTMLElement>("#spriteCount")!;
  const inspector = container.querySelector<HTMLElement>("#spriteInspector")!;
  const filterInput = container.querySelector<HTMLInputElement>("#spriteFilter")!;
  const zoomInput = container.querySelector<HTMLInputElement>("#spriteZoom")!;

  function applyTileSize() {
    grid.style.setProperty("--tile", `${tileSize}px`);
  }
  applyTileSize();
  zoomInput.addEventListener("input", () => {
    tileSize = parseInt(zoomInput.value, 10) || 128;
    applyTileSize();
  });

  // Lazy-decode thumbnails as tiles intersect the viewport.
  const io = new IntersectionObserver(
    (entries) => {
      for (const e of entries) {
        if (!e.isIntersecting) continue;
        const tile = e.target as HTMLElement;
        io.unobserve(tile);
        loadThumb(tile);
      }
    },
    { root: null, rootMargin: "200px" }
  );

  function loadThumb(tile: HTMLElement) {
    const p = tile.dataset.path!;
    const imgEl = tile.querySelector("img")!;
    const cached = spriteCache.get(p);
    if (cached) {
      imgEl.src = cached;
      tile.classList.add("loaded");
      return;
    }
    getSprite(p)
      .then((s) => {
        spriteCache.set(p, s.dataUrl);
        imgEl.src = s.dataUrl;
        tile.classList.add("loaded");
        tile.dataset.w = String(s.width);
        tile.dataset.h = String(s.height);
      })
      .catch(() => {
        tile.classList.add("failed");
      });
  }

  function draw() {
    const q = filter.trim().toLowerCase();
    const list = q
      ? sprites.filter((s) => s.name.toLowerCase().includes(q) || String(s.id).includes(q))
      : sprites;
    countEl.textContent = String(list.length);
    grid.innerHTML = list
      .map(
        (s) => `
      <div class="sprite-tile" data-path="${esc(s.path)}" title="${esc(s.name)}">
        <div class="sprite-thumb"><img alt="${esc(s.name)}" /></div>
        <div class="sprite-id">${s.id >= 0 ? "#" + s.id : esc(s.name)}</div>
      </div>`
      )
      .join("");

    grid.querySelectorAll<HTMLElement>(".sprite-tile").forEach((tile) => {
      io.observe(tile);
      tile.addEventListener("click", () => openInspector(tile.dataset.path!));
    });
  }

  function openInspector(p: string) {
    inspector.hidden = false;
    inspector.innerHTML = `<div class="loading">Decoding\u2026</div>`;
    getSprite(p)
      .then((s) => {
        const idMatch = /(\d+)\.tga$/.exec(s.path);
        const gfxId = idMatch ? idMatch[1] : "";
        inspector.innerHTML = `
          <div class="insp-head">
            <div class="insp-title mono">${esc(s.path)}</div>
            <button id="inspClose">Close</button>
          </div>
          <div class="insp-body">
            <div class="insp-image insp-checker"><img src="${s.dataUrl}" alt="${esc(s.path)}" /></div>
            <div class="insp-meta">
              <div><span>Dimensions</span><b>${s.width} \u00D7 ${s.height}</b></div>
              ${gfxId ? `<div><span>Gfx ID</span><b>${esc(gfxId)}</b></div>` : ""}
              <div><span>Path</span><b class="mono">${esc(s.path)}</b></div>
              <div class="insp-btns">
                <button id="inspDownload" class="primary">\u2B07 Download PNG</button>
                <button id="inspCopy">Copy</button>
              </div>
            </div>
          </div>`;
        inspector.querySelector<HTMLButtonElement>("#inspClose")!.addEventListener("click", () => {
          inspector.hidden = true;
        });
        inspector.querySelector<HTMLButtonElement>("#inspDownload")!.addEventListener("click", () => {
          const a = document.createElement("a");
          a.href = s.dataUrl;
          a.download = (gfxId || s.path.split("/").pop()?.replace(/\.tga$/, "") || "sprite") + ".png";
          a.click();
        });
        inspector.querySelector<HTMLButtonElement>("#inspCopy")!.addEventListener("click", async (ev) => {
          const btn = ev.currentTarget as HTMLButtonElement;
          try {
            const blob = await (await fetch(s.dataUrl)).blob();
            // @ts-ignore ClipboardItem may be missing in older lib types
            await navigator.clipboard.write([new ClipboardItem({ "image/png": blob })]);
            btn.textContent = "Copied!";
          } catch {
            await navigator.clipboard.writeText(s.dataUrl);
            btn.textContent = "Copied data URL";
          }
          setTimeout(() => (btn.textContent = "Copy"), 1400);
        });
      })
      .catch((err) => {
        inspector.innerHTML = `<div class="preview-error"><b>Decode failed.</b><div class="mono">${esc(
          (err as Error).message
        )}</div></div>`;
      });
  }

  filterInput.addEventListener("input", () => {
    filter = filterInput.value;
    const pos = filterInput.selectionStart;
    draw();
    filterInput.focus();
    if (pos != null) filterInput.setSelectionRange(pos, pos);
  });

  draw();
}
