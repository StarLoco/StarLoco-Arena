// Phase 2 asset browser: pick a client jar, list its entries (filterable),
// and preview images (PNG) / text (XML/Lua/properties) / show metadata for
// opaque binaries.

import {
  listJars,
  listJarEntries,
  previewEntry,
  type JarInfo,
  type AssetEntry,
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

const kindIcon: Record<string, string> = {
  image: "\u25A3",
  text: "\u2637",
  binary: "\u25A0",
};

interface State {
  jars: JarInfo[];
  activeJar: string | null;
  entries: AssetEntry[];
  filter: string;
  kind: "" | "image" | "text" | "binary";
  ext: string;
  selected: string | null;
}

export function viewAssets(container: HTMLElement) {
  const state: State = {
    jars: [],
    activeJar: null,
    entries: [],
    filter: "",
    kind: "",
    ext: "",
    selected: null,
  };

  container.innerHTML = `
    <div class="page-head"><h1>Asset Browser</h1><span class="sub">client jars &middot; browse &amp; preview</span></div>
    <div class="assets-layout">
      <div class="assets-jars" id="jarList"><div class="loading">Loading jars\u2026</div></div>
      <div class="assets-entries" id="entryPane"></div>
      <div class="assets-preview" id="previewPane"><div class="preview-empty"><div class="big">\u25A6</div><div>Select an entry to preview.</div></div></div>
    </div>`;

  const jarList = container.querySelector<HTMLElement>("#jarList")!;
  const entryPane = container.querySelector<HTMLElement>("#entryPane")!;
  const previewPane = container.querySelector<HTMLElement>("#previewPane")!;

  listJars()
    .then((jars) => {
      state.jars = jars;
      drawJars();
    })
    .catch((err) => {
      jarList.innerHTML = `<div class="preview-error"><b>No jars.</b><div class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
    });

  function drawJars() {
    jarList.innerHTML =
      `<div class="pane-title">Jars</div>` +
      state.jars
        .map(
          (j) => `
        <div class="jar-item ${j.name === state.activeJar ? "active" : ""}" data-jar="${esc(j.name)}">
          <div class="jar-name">${esc(j.name)}</div>
          <div class="jar-meta">${
            j.error ? `<span class="pill err">error</span>` : `${j.entryCount} files \u00B7 ${fmtBytes(j.sizeBytes)}`
          }</div>
        </div>`
        )
        .join("");
    jarList.querySelectorAll<HTMLElement>("[data-jar]").forEach((el) => {
      el.addEventListener("click", () => selectJar(el.dataset.jar!));
    });
  }

  function selectJar(name: string) {
    state.activeJar = name;
    state.selected = null;
    state.filter = "";
    state.kind = "";
    state.ext = "";
    drawJars();
    entryPane.innerHTML = `<div class="loading">Loading entries\u2026</div>`;
    previewPane.innerHTML = `<div class="preview-empty">Select an entry to preview.</div>`;
    listJarEntries(name)
      .then((entries) => {
        state.entries = entries;
        drawEntries();
      })
      .catch((err) => {
        entryPane.innerHTML = `<div class="preview-error"><b>Failed.</b><div class="mono">${esc(
          (err as Error).message
        )}</div></div>`;
      });
  }

  // extCounts tallies file extensions in the active jar for the extension chips.
  function extCounts(): { ext: string; n: number }[] {
    const m = new Map<string, number>();
    for (const e of state.entries) {
      const dot = e.path.lastIndexOf(".");
      const ext = dot >= 0 ? e.path.slice(dot + 1).toLowerCase() : "(none)";
      m.set(ext, (m.get(ext) ?? 0) + 1);
    }
    return Array.from(m, ([ext, n]) => ({ ext, n }))
      .sort((a, b) => b.n - a.n)
      .slice(0, 8);
  }

  function drawEntries() {
    const q = state.filter.trim().toLowerCase();
    const list = state.entries.filter((e) => {
      if (q && !e.path.toLowerCase().includes(q)) return false;
      if (state.kind && e.kind !== state.kind) return false;
      if (state.ext) {
        const dot = e.path.lastIndexOf(".");
        const ext = dot >= 0 ? e.path.slice(dot + 1).toLowerCase() : "(none)";
        if (ext !== state.ext) return false;
      }
      return true;
    });
    const capped = list.slice(0, 2000); // guard very large jars in the DOM

    const kinds: Array<State["kind"]> = ["", "image", "text", "binary"];
    const kindChips = kinds
      .map(
        (k) =>
          `<button class="asset-chip ${state.kind === k ? "on" : ""}" data-kind="${k}">${
            k === "" ? "all" : `${kindIcon[k] ?? ""} ${k}`
          }</button>`
      )
      .join("");
    const extChips = extCounts()
      .map(
        ({ ext, n }) =>
          `<button class="asset-chip ext ${state.ext === ext ? "on" : ""}" data-ext="${esc(
            ext
          )}">.${esc(ext)} <span class="asset-chip-n">${n}</span></button>`
      )
      .join("");

    entryPane.innerHTML = `
      <div class="entry-toolbar">
        <div class="tb-search-wrap">
          <span class="tb-search-ico">\u2315</span>
          <input class="tb-search" id="entryFilter" placeholder="Filter ${state.entries.length} entries\u2026" value="${esc(
      state.filter
    )}" />
        </div>
        <span class="tb-count"><b>${list.length}</b></span>
      </div>
      <div class="asset-facets">
        <div class="asset-chip-row">${kindChips}</div>
        <div class="asset-chip-row">${extChips}</div>
      </div>
      <div class="entry-scroll">
        ${
          capped.length === 0
            ? `<div class="entry-more">No entries match.</div>`
            : capped
                .map(
                  (e) => `
          <div class="entry-item ${e.path === state.selected ? "sel" : ""}" data-path="${esc(e.path)}">
            <span class="entry-ico ${e.kind}">${kindIcon[e.kind] ?? "\u25A0"}</span>
            <span class="entry-path">${esc(e.path)}</span>
            <span class="entry-size">${fmtBytes(e.size)}</span>
          </div>`
                )
                .join("")
        }
        ${list.length > capped.length ? `<div class="entry-more">\u2026 ${list.length - capped.length} more (refine filter)</div>` : ""}
      </div>`;

    const filterInput = entryPane.querySelector<HTMLInputElement>("#entryFilter")!;
    filterInput.addEventListener("input", () => {
      state.filter = filterInput.value;
      const pos = filterInput.selectionStart;
      drawEntries();
      const ni = entryPane.querySelector<HTMLInputElement>("#entryFilter")!;
      ni.focus();
      if (pos != null) ni.setSelectionRange(pos, pos);
    });
    entryPane.querySelectorAll<HTMLElement>("[data-kind]").forEach((el) => {
      el.addEventListener("click", () => {
        state.kind = el.dataset.kind as State["kind"];
        drawEntries();
      });
    });
    entryPane.querySelectorAll<HTMLElement>("[data-ext]").forEach((el) => {
      el.addEventListener("click", () => {
        state.ext = state.ext === el.dataset.ext ? "" : el.dataset.ext!;
        drawEntries();
      });
    });
    entryPane.querySelectorAll<HTMLElement>("[data-path]").forEach((el) => {
      el.addEventListener("click", () => selectEntry(el.dataset.path!));
    });
  }

  function selectEntry(p: string) {
    state.selected = p;
    drawEntries();
    previewPane.innerHTML = `<div class="loading">Loading preview\u2026</div>`;
    previewEntry(state.activeJar!, p)
      .then((prev) => {
        const head = `
          <div class="preview-head">
            <div class="preview-name">${esc(prev.path)}</div>
            <div class="preview-meta">${prev.kind} \u00B7 ${fmtBytes(prev.size)}</div>
          </div>`;
        let bodyHtml: string;
        if (prev.kind === "image" && prev.dataUrl) {
          bodyHtml = `<div class="preview-image"><img src="${prev.dataUrl}" alt="${esc(prev.path)}" /></div>`;
        } else if (prev.kind === "text") {
          bodyHtml = `<pre class="preview-text">${esc(prev.text)}${
            prev.truncated ? "\n\u2026 (truncated)" : ""
          }</pre>`;
        } else {
          const note =
            prev.path.toLowerCase().endsWith(".tga")
              ? "TGA sprite \u2014 visual preview arrives in Phase 3."
              : "Binary asset \u2014 no inline preview.";
          bodyHtml = `<div class="preview-binary"><div class="big">${
            kindIcon.binary
          }</div><div>${esc(note)}</div></div>`;
        }
        previewPane.innerHTML = head + bodyHtml;
      })
      .catch((err) => {
        previewPane.innerHTML = `<div class="preview-error"><b>Preview failed.</b><div class="mono">${esc(
          (err as Error).message
        )}</div></div>`;
      });
  }
}
