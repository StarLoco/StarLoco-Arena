// translations.ts -- the admin translation editor. Lists every editable
// content.* string (spell/card/breed/event/summon names + descriptions +
// effect labels) for the active language, with the other language shown
// alongside for reference. Edits are inline; a sticky save bar writes them back
// into i18n.jar (backup + atomic repack).

import {
  getTranslations,
  saveTranslations,
  getLanguage,
  setLanguage,
  type TransRow,
  type TransEdit,
} from "./backend";
import { setDirty } from "./dirty";
import { iconCell, wireIconCells } from "./names";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

interface State {
  rows: TransRow[];
  lang: string;
  altLang: string;
  edits: Map<string, string>; // "cat.id" -> new value
  filter: string;
  kind: string; // "" = all
  onlyEmpty: boolean;
  onlyChanged: boolean;
}

const KIND_LABELS: Record<string, string> = {
  spell: "Spells",
  fighterCard: "Fighter cards",
  coachCard: "Coach cards",
  breed: "Classes",
  summon: "Summons",
  event: "Events",
  effect: "Effect labels",
};

const KIND_ICON: Record<string, string> = {
  spell: "spell",
  fighterCard: "fighterCard",
  coachCard: "coachCard",
  event: "event",
  breed: "breed",
};

export function viewTranslations(container: HTMLElement) {
  const state: State = {
    rows: [],
    lang: "en",
    altLang: "fr",
    edits: new Map(),
    filter: "",
    kind: "",
    onlyEmpty: false,
    onlyChanged: false,
  };

  container.innerHTML = `
    <div class="page-head"><h1>Translations</h1><span class="sub">edit the client's i18n strings</span></div>
    <div class="loading">Loading\u2026</div>`;

  load();

  async function load() {
    try {
      const ts = await getTranslations();
      if (ts.error) throw new Error(ts.error);
      state.rows = ts.rows;
      state.lang = ts.lang;
      state.altLang = ts.altLang;
      state.edits.clear();
      draw();
    } catch (err) {
      container.innerHTML = `<div class="page-head"><h1>Translations</h1></div>
        <div class="placeholder"><div class="big">\u26A0</div><div>Could not load translations.</div>
        <div class="mono" style="margin-top:6px;font-size:12px">${esc((err as Error).message)}</div>
        <div class="hint" style="margin-top:8px">A valid client directory with <code>i18n.jar</code> is required.</div></div>`;
    }
  }

  const key = (r: TransRow) => `${r.cat}.${r.id}`;

  function currentValue(r: TransRow): string {
    const k = key(r);
    return state.edits.has(k) ? state.edits.get(k)! : r.value;
  }

  function filtered(): TransRow[] {
    const q = state.filter.trim().toLowerCase();
    return state.rows.filter((r) => {
      if (state.kind && r.kind !== state.kind) return false;
      if (state.onlyChanged && !state.edits.has(key(r))) return false;
      if (state.onlyEmpty && currentValue(r).trim() !== "") return false;
      if (q) {
        const hay = `${r.id} ${r.label} ${currentValue(r)} ${r.alt}`.toLowerCase();
        if (!hay.includes(q)) return false;
      }
      return true;
    });
  }

  function draw() {
    // Keep the global unsaved-changes guard in sync on every (re)render.
    setDirty(`trans:${state.lang}`, state.edits.size > 0);
    const kinds = [...new Set(state.rows.map((r) => r.kind))];
    const chips = [`<button class="tr-chip ${state.kind === "" ? "on" : ""}" data-kind="">all</button>`]
      .concat(
        kinds.map(
          (k) =>
            `<button class="tr-chip ${state.kind === k ? "on" : ""}" data-kind="${esc(k)}">${esc(
              KIND_LABELS[k] ?? k
            )}</button>`
        )
      )
      .join("");

    const list = filtered();
    const rowsHtml = list
      .map((r) => {
        const k = key(r);
        const changed = state.edits.has(k);
        const val = currentValue(r);
        const icon = KIND_ICON[r.kind] ? iconCell(KIND_ICON[r.kind], r.id, 30) : `<span class="tr-noicon">${r.isDesc ? "\u00B6" : "T"}</span>`;
        return `
        <tr class="tr-row ${changed ? "changed" : ""} ${val.trim() === "" ? "empty" : ""}" data-key="${k}">
          <td class="tr-icon">${icon}</td>
          <td class="tr-meta">
            <span class="tr-label">${esc(r.label)}</span>
            <span class="tr-id mono">#${r.id}</span>
          </td>
          <td class="tr-val">
            <textarea class="tr-input" data-key="${k}" rows="1" spellcheck="false">${esc(val)}</textarea>
          </td>
          <td class="tr-alt" title="${esc(r.altLang)} reference">${esc(r.alt) || "<span class='tr-dash'>\u2014</span>"}</td>
        </tr>`;
      })
      .join("");

    container.innerHTML = `
      <div class="page-head">
        <h1>Translations</h1>
        <span class="sub">${state.rows.length} strings \u00B7 editing <b>${esc(state.lang.toUpperCase())}</b></span>
      </div>
      <div class="tr-toolbar">
        <div class="tb-search-wrap">
          <span class="tb-search-ico">\u2315</span>
          <input class="tb-search" id="trSearch" placeholder="Search names, ids, values\u2026" value="${esc(state.filter)}" />
        </div>
        <label class="tr-toggle"><input type="checkbox" id="trEmpty" ${state.onlyEmpty ? "checked" : ""}/> missing only</label>
        <label class="tr-toggle"><input type="checkbox" id="trChanged" ${state.onlyChanged ? "checked" : ""}/> changed only</label>
        <select id="trLang" class="pref-select tr-lang">
          <option value="en" ${state.lang === "en" ? "selected" : ""}>English</option>
          <option value="fr" ${state.lang === "fr" ? "selected" : ""}>Français</option>
        </select>
      </div>
      <div class="tr-chips">${chips}</div>
      <div class="tr-scroll">
        <table class="tr-table">
          <thead><tr>
            <th></th><th>String</th><th>${esc(state.lang.toUpperCase())} (editing)</th><th>${esc(state.altLang.toUpperCase())} (ref)</th>
          </tr></thead>
          <tbody>${rowsHtml || `<tr><td colspan="4" class="tb-empty">No matching strings.</td></tr>`}</tbody>
        </table>
      </div>
      ${saveBar()}`;

    wire();
    wireIconCells(container);
    autosize(container);
  }

  function saveBar(): string {
    const n = state.edits.size;
    return `
      <div class="tr-savebar ${n ? "active" : ""}">
        <span class="tr-savecount">${n ? `<b>${n}</b> unsaved change${n === 1 ? "" : "s"}` : "No unsaved changes"}</span>
        <div class="tr-savebar-actions">
          <button id="trRevert" ${n ? "" : "disabled"}>Revert</button>
          <button id="trSave" class="primary" ${n ? "" : "disabled"}>Save to i18n.jar</button>
        </div>
        <span class="tr-savestatus" id="trStatus"></span>
      </div>`;
  }

  function refreshSaveBar() {
    const bar = container.querySelector<HTMLElement>(".tr-savebar");
    if (bar) bar.outerHTML = saveBar();
    wireSaveBar();
    // Reflect the pending translation edits in the global unsaved-changes guard.
    setDirty(`trans:${state.lang}`, state.edits.size > 0);
  }

  function wire() {
    const search = container.querySelector<HTMLInputElement>("#trSearch")!;
    search.addEventListener("input", () => {
      state.filter = search.value;
      const pos = search.selectionStart;
      draw();
      const ni = container.querySelector<HTMLInputElement>("#trSearch")!;
      ni.focus();
      if (pos != null) ni.setSelectionRange(pos, pos);
    });
    container.querySelector<HTMLInputElement>("#trEmpty")!.addEventListener("change", (e) => {
      state.onlyEmpty = (e.target as HTMLInputElement).checked;
      draw();
    });
    container.querySelector<HTMLInputElement>("#trChanged")!.addEventListener("change", (e) => {
      state.onlyChanged = (e.target as HTMLInputElement).checked;
      draw();
    });
    (container.querySelector("#trLang") as HTMLSelectElement).addEventListener("change", async (e) => {
      const code = (e.target as HTMLSelectElement).value;
      if (state.edits.size && !confirm("Switch language and discard unsaved changes?")) {
        (e.target as HTMLSelectElement).value = state.lang;
        return;
      }
      await setLanguage(code);
      await load();
    });
    container.querySelectorAll<HTMLElement>("[data-kind]").forEach((el) => {
      el.addEventListener("click", () => {
        state.kind = el.dataset.kind!;
        draw();
      });
    });
    container.querySelectorAll<HTMLTextAreaElement>(".tr-input").forEach((ta) => {
      ta.addEventListener("input", () => {
        const k = ta.dataset.key!;
        const row = state.rows.find((r) => key(r) === k)!;
        if (ta.value === row.value) state.edits.delete(k);
        else state.edits.set(k, ta.value);
        ta.closest("tr")?.classList.toggle("changed", state.edits.has(k));
        autosizeOne(ta);
        refreshSaveBar();
      });
    });
    wireSaveBar();
  }

  function wireSaveBar() {
    container.querySelector<HTMLButtonElement>("#trSave")?.addEventListener("click", onSave);
    container.querySelector<HTMLButtonElement>("#trRevert")?.addEventListener("click", () => {
      if (!state.edits.size) return;
      if (!confirm("Discard all unsaved changes?")) return;
      state.edits.clear();
      draw();
    });
  }

  async function onSave() {
    if (!state.edits.size) return;
    const status = container.querySelector<HTMLElement>("#trStatus")!;
    const btn = container.querySelector<HTMLButtonElement>("#trSave")!;
    btn.disabled = true;
    status.textContent = "Saving\u2026";
    status.className = "tr-savestatus";
    const edits: TransEdit[] = [...state.edits.entries()].map(([k, value]) => {
      const [cat, id] = k.split(".").map(Number);
      return { cat, id, value };
    });
    try {
      const res = await saveTranslations(state.lang, edits);
      // Reflect saved values as the new baseline, clear edits.
      for (const [k, v] of state.edits) {
        const row = state.rows.find((r) => key(r) === k);
        if (row) row.value = v;
      }
      state.edits.clear();
      draw();
      const st = container.querySelector<HTMLElement>("#trStatus");
      if (st) {
        st.textContent = `Saved \u00B7 backup: ${res.backupPath?.split(/[\\/]/).pop() ?? "created"}`;
        st.className = "tr-savestatus ok";
      }
    } catch (err) {
      status.textContent = `Failed: ${(err as Error).message}`;
      status.className = "tr-savestatus err";
      btn.disabled = false;
    }
  }

  // Sync the language dropdown initial state from the app.
  getLanguage().then((l) => {
    if (l.current !== state.lang && state.rows.length === 0) {
      state.lang = l.current;
    }
  });
}

// autosize grows every textarea to fit its content (called after a full draw).
function autosize(root: HTMLElement) {
  root.querySelectorAll<HTMLTextAreaElement>(".tr-input").forEach(autosizeOne);
}
function autosizeOne(ta: HTMLTextAreaElement) {
  ta.style.height = "auto";
  ta.style.height = Math.min(160, ta.scrollHeight) + "px";
}
