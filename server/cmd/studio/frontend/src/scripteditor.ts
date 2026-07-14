// scripteditor.ts -- a spell-script IDE panel. Given a record's ScriptID it
// lazily loads scripts/<id>.lua from the client's data.jar, shows the Lua source
// in an editable code area with line numbers, and saves edits back to data.jar
// (backup + atomic, via the universal jar recompiler). Missing scripts render a
// graceful "no script file" state rather than an error, since some spells point
// at script ids that ship no file.

import {
  getSpellScript,
  saveSpellScript,
  listScriptIDs,
  type SpellScript,
} from "./backend";
import { highlightLua } from "./luahl";
import { setDirty, markClean } from "./dirty";
import { t } from "./i18n";

// scriptIdSet caches which script ids actually have a Lua file, so views can
// badge "has script" at a glance without a fetch per row. Loaded once.
let scriptIdSet: Set<number> | null = null;

export async function loadScriptIndex(): Promise<Set<number>> {
  if (scriptIdSet) return scriptIdSet;
  try {
    scriptIdSet = new Set(await listScriptIDs());
  } catch {
    scriptIdSet = new Set();
  }
  return scriptIdSet;
}

// hasScript reports whether scriptId has a Lua file (per the cached index).
// Returns false until loadScriptIndex has populated the cache.
export function hasScript(scriptId: number): boolean {
  return !!scriptIdSet && scriptIdSet.has(scriptId);
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// mountScriptEditor loads + renders the Lua editor for scriptId into host.
// scriptId <= 0 means the record has no script link; we say so and stop.
export function mountScriptEditor(host: HTMLElement, scriptId: number) {
  if (host.dataset.scDone === String(scriptId)) return; // already mounted for this id
  host.dataset.scDone = String(scriptId);

  if (!scriptId || scriptId <= 0) {
    host.innerHTML = `
      <div class="sc-editor">
        <div class="sc-head"><b>${esc(t("script.title"))}</b></div>
        <div class="detail-empty">${esc(t("script.noLink"))}</div>
      </div>`;
    return;
  }

  host.innerHTML = `
    <div class="sc-editor">
      <div class="sc-head"><b>${esc(t("script.title"))}</b> <span class="sc-id">scripts/${scriptId}.lua</span></div>
      <div class="sc-loading">${esc(t("script.loading"))}</div>
    </div>`;

  getSpellScript(scriptId)
    .then((sc) => render(host, sc))
    .catch((err) => {
      const box = host.querySelector(".sc-loading");
      if (box) box.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
    });
}

function render(host: HTMLElement, sc: SpellScript) {
  const editor = host.querySelector<HTMLElement>(".sc-editor")!;
  if (!sc.exists) {
    editor.innerHTML = `
      <div class="sc-head"><b>${esc(t("script.title"))}</b> <span class="sc-id">${esc(sc.entry)}</span></div>
      <div class="detail-empty">${t("script.noFile", {
        entry: `<span class="mono">${esc(sc.entry)}</span>`,
      })}</div>`;
    return;
  }

  const lineCount = sc.source.split("\n").length;
  editor.innerHTML = `
    <div class="sc-head">
      <b>${esc(t("script.title"))}</b>
      <span class="sc-id">${esc(sc.entry)}</span>
      <span class="sc-meta">${esc(t("script.lines", { n: lineCount, bytes: sc.bytes }))}</span>
    </div>
    <div class="sc-codewrap">
      <pre class="sc-hl mono" aria-hidden="true"></pre>
      <textarea class="sc-code mono" spellcheck="false" wrap="off">${esc(sc.source)}</textarea>
    </div>
    <div class="sc-actions">
      <button class="primary" data-save>\u2B07 ${esc(t("script.save"))}</button>
      <button data-revert>${esc(t("script.revert"))}</button>
      <span class="sc-status" data-status></span>
    </div>`;

  const ta = editor.querySelector<HTMLTextAreaElement>(".sc-code")!;
  const hl = editor.querySelector<HTMLElement>(".sc-hl")!;
  let baseline = sc.source; // the last-saved value; edits are dirty vs this
  const dkey = `script:${sc.scriptId}`;

  // syncHighlight repaints the highlight layer + keeps it scroll-aligned with
  // the textarea. Called on every input and scroll.
  const syncHighlight = () => {
    hl.innerHTML = highlightLua(ta.value);
    hl.scrollTop = ta.scrollTop;
    hl.scrollLeft = ta.scrollLeft;
  };
  syncHighlight();
  ta.addEventListener("scroll", () => {
    hl.scrollTop = ta.scrollTop;
    hl.scrollLeft = ta.scrollLeft;
  });

  // Keep the drawer from collapsing while interacting with the editor.
  editor.addEventListener("mousedown", (e) => e.stopPropagation());
  editor.addEventListener("click", (e) => {
    const t = e.target as HTMLElement;
    if (!t.matches("[data-save],[data-revert]")) e.stopPropagation();
  });

  // Tab inserts two spaces instead of moving focus (basic code-editor feel).
  ta.addEventListener("keydown", (e) => {
    if (e.key === "Tab") {
      e.preventDefault();
      const start = ta.selectionStart;
      const end = ta.selectionEnd;
      ta.value = ta.value.slice(0, start) + "  " + ta.value.slice(end);
      ta.selectionStart = ta.selectionEnd = start + 2;
      markDirty();
      syncHighlight();
    }
  });

  const status = editor.querySelector<HTMLElement>("[data-status]")!;
  const markDirty = () => {
    const dirty = ta.value !== baseline;
    setDirty(dkey, dirty);
    if (dirty) {
      status.textContent = t("script.unsaved");
      status.className = "sc-status dirty";
    } else {
      status.textContent = "";
      status.className = "sc-status";
    }
  };
  ta.addEventListener("input", () => {
    markDirty();
    syncHighlight();
  });

  editor.querySelector<HTMLButtonElement>("[data-revert]")?.addEventListener("click", (e) => {
    e.stopPropagation();
    ta.value = baseline;
    markDirty();
    syncHighlight();
  });

  editor.querySelector<HTMLButtonElement>("[data-save]")?.addEventListener("click", async (e) => {
    e.stopPropagation();
    const btn = e.currentTarget as HTMLButtonElement;
    btn.disabled = true;
    status.textContent = t("script.saving");
    status.className = "sc-status";
    try {
      const res = await saveSpellScript(sc.scriptId, ta.value);
      const backup = res.backupPath?.split(/[\\/]/).pop();
      status.textContent =
        t("script.saved", { bytes: res.bytes ?? 0 }) + (backup ? ` \u00B7 ${backup}` : "");
      status.className = "sc-status ok";
      // The saved value becomes the new baseline; the editor is now clean.
      baseline = ta.value;
      markClean(dkey);
    } catch (err) {
      status.textContent = (err as Error).message;
      status.className = "sc-status err";
    } finally {
      btn.disabled = false;
    }
  });
}
