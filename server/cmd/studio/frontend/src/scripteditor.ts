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
        <div class="sc-head"><b>Lua script</b></div>
        <div class="detail-empty">This record has no script link (ScriptID 0).</div>
      </div>`;
    return;
  }

  host.innerHTML = `
    <div class="sc-editor">
      <div class="sc-head"><b>Lua script</b> <span class="sc-id">scripts/${scriptId}.lua</span></div>
      <div class="sc-loading">Loading script\u2026</div>
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
      <div class="sc-head"><b>Lua script</b> <span class="sc-id">${esc(sc.entry)}</span></div>
      <div class="detail-empty">No script file <span class="mono">${esc(
        sc.entry
      )}</span> in data.jar. This spell references a script id that ships no file.</div>`;
    return;
  }

  const lineCount = sc.source.split("\n").length;
  editor.innerHTML = `
    <div class="sc-head">
      <b>Lua script</b>
      <span class="sc-id">${esc(sc.entry)}</span>
      <span class="sc-meta">${lineCount} lines \u00B7 ${sc.bytes} B</span>
    </div>
    <div class="sc-codewrap">
      <pre class="sc-hl mono" aria-hidden="true"></pre>
      <textarea class="sc-code mono" spellcheck="false" wrap="off">${esc(sc.source)}</textarea>
    </div>
    <div class="sc-actions">
      <button class="primary" data-save>\u2B07 Save to data.jar</button>
      <button data-revert>Revert</button>
      <span class="sc-status" data-status></span>
    </div>`;

  const ta = editor.querySelector<HTMLTextAreaElement>(".sc-code")!;
  const hl = editor.querySelector<HTMLElement>(".sc-hl")!;
  const original = sc.source;

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
    if (ta.value !== original) {
      status.textContent = "Unsaved changes";
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
    ta.value = original;
    markDirty();
    syncHighlight();
  });

  editor.querySelector<HTMLButtonElement>("[data-save]")?.addEventListener("click", async (e) => {
    e.stopPropagation();
    const btn = e.currentTarget as HTMLButtonElement;
    btn.disabled = true;
    status.textContent = "Saving\u2026";
    status.className = "sc-status";
    try {
      const res = await saveSpellScript(sc.scriptId, ta.value);
      status.textContent = `Saved \u00B7 backup ${
        res.backupPath?.split(/[\\/]/).pop() ?? "created"
      }`;
      status.className = "sc-status ok";
      // The saved value becomes the new baseline (mark clean on next input).
      (sc as { source: string }).source = ta.value;
    } catch (err) {
      status.textContent = (err as Error).message;
      status.className = "sc-status err";
    } finally {
      btn.disabled = false;
    }
  });
}
