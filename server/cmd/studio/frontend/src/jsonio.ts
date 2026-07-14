// jsonio.ts -- the bulk JSON import/export UI. A view opens this modal for its
// record kind; the user can export the whole .dat as a full-fidelity JSON
// document (download or copy) and re-import an edited document. Import runs the
// backend's byte-exact-gated pipeline, so a no-op export/import reproduces the
// file exactly and any structural mistake is refused rather than corrupting
// data. Great for scripted bulk edits (search/replace, generation) outside the
// per-record editors.

import { exportRecordsJSON, importRecordsJSON } from "./backend";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// KIND_FILE maps a record kind to its .dat filename (for labels/downloads).
const KIND_FILE: Record<string, string> = {
  spells: "spells.dat",
  cards: "cards.dat",
  events: "events.dat",
  summonings: "summoning.dat",
  staticEffects: "staticEffects.dat",
};

// openJsonModal shows the import/export dialog for kind. onImported is called
// after a successful import so the host view can reload.
export function openJsonModal(kind: string, onImported: () => void) {
  const file = KIND_FILE[kind] ?? `${kind}.dat`;
  const overlay = document.createElement("div");
  overlay.className = "rc-overlay";
  overlay.innerHTML = `
    <div class="rc-modal jio-modal" role="dialog" aria-modal="true">
      <div class="rc-head">
        <b>JSON \u00B7 ${esc(file)}</b>
        <button class="rc-close" data-close title="Close">\u00D7</button>
      </div>
      <div class="rc-body">
        <p class="jio-note">
          Export the full parsed contents of <span class="mono">${esc(file)}</span> as JSON,
          edit it anywhere, then import it back. Import is byte-exact-gated and backed up;
          a no-op round-trips the file unchanged. Every field (including reserved bytes) is
          preserved, so keep the structure intact.
        </p>
        <div class="jio-actions">
          <button class="primary" data-export>\u2B07 Export JSON</button>
          <button data-copy disabled>Copy</button>
          <button data-download disabled>Download</button>
          <label class="jio-file">Load file\u2026<input type="file" accept=".json,application/json" data-file hidden /></label>
        </div>
        <textarea class="jio-text mono" spellcheck="false" placeholder="Exported JSON appears here \u2014 or paste a document to import\u2026"></textarea>
        <div class="jio-import-row">
          <button class="danger" data-import disabled>\u2B06 Import &amp; overwrite ${esc(file)}</button>
          <span class="jio-status" data-status></span>
        </div>
      </div>
    </div>`;
  document.body.appendChild(overlay);

  const ta = overlay.querySelector<HTMLTextAreaElement>(".jio-text")!;
  const status = overlay.querySelector<HTMLElement>("[data-status]")!;
  const copyBtn = overlay.querySelector<HTMLButtonElement>("[data-copy]")!;
  const dlBtn = overlay.querySelector<HTMLButtonElement>("[data-download]")!;
  const importBtn = overlay.querySelector<HTMLButtonElement>("[data-import]")!;

  const close = () => overlay.remove();
  overlay.addEventListener("mousedown", (e) => {
    if (e.target === overlay) close();
  });
  overlay.querySelector<HTMLButtonElement>("[data-close]")?.addEventListener("click", close);

  const setStatus = (msg: string, cls = "") => {
    status.textContent = msg;
    status.className = `jio-status ${cls}`;
  };

  // Enable import/copy/download when there's text.
  const refreshButtons = () => {
    const has = ta.value.trim().length > 0;
    copyBtn.disabled = !has;
    dlBtn.disabled = !has;
    importBtn.disabled = !has;
  };
  ta.addEventListener("input", refreshButtons);

  overlay.querySelector<HTMLButtonElement>("[data-export]")?.addEventListener("click", async (e) => {
    const btn = e.currentTarget as HTMLButtonElement;
    btn.disabled = true;
    setStatus("Exporting\u2026");
    try {
      ta.value = await exportRecordsJSON(kind);
      setStatus(`Exported ${ta.value.length.toLocaleString()} chars`, "ok");
      refreshButtons();
    } catch (err) {
      setStatus((err as Error).message, "err");
    } finally {
      btn.disabled = false;
    }
  });

  copyBtn.addEventListener("click", async () => {
    try {
      await navigator.clipboard.writeText(ta.value);
      setStatus("Copied to clipboard", "ok");
    } catch {
      ta.select();
      document.execCommand("copy");
      setStatus("Copied", "ok");
    }
  });

  dlBtn.addEventListener("click", () => {
    const blob = new Blob([ta.value], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `${file}.json`;
    a.click();
    setTimeout(() => URL.revokeObjectURL(url), 1000);
  });

  overlay.querySelector<HTMLInputElement>("[data-file]")?.addEventListener("change", (e) => {
    const f = (e.target as HTMLInputElement).files?.[0];
    if (!f) return;
    const reader = new FileReader();
    reader.onload = () => {
      ta.value = String(reader.result ?? "");
      refreshButtons();
      setStatus(`Loaded ${esc(f.name)}`, "ok");
    };
    reader.readAsText(f);
  });

  importBtn.addEventListener("click", async () => {
    const doc = ta.value.trim();
    if (!doc) return;
    // Validate JSON client-side first for a friendly error.
    try {
      JSON.parse(doc);
    } catch (err) {
      setStatus(`Invalid JSON: ${(err as Error).message}`, "err");
      return;
    }
    if (
      !window.confirm(
        `Overwrite ${file} with the JSON above? The current file is backed up first, but this replaces ALL records.`
      )
    ) {
      return;
    }
    importBtn.disabled = true;
    setStatus("Importing\u2026");
    try {
      const res = await importRecordsJSON(kind, doc);
      setStatus(
        `Imported \u00B7 ${res.bytes.toLocaleString()} B written \u00B7 backup ${
          res.backupPath?.split(/[\\/]/).pop() ?? "created"
        }`,
        "ok"
      );
      setTimeout(() => {
        close();
        onImported();
      }, 900);
    } catch (err) {
      setStatus((err as Error).message, "err");
      importBtn.disabled = false;
    }
  });
}

// jsonButton returns markup for a "JSON" pill button that opens the modal.
export function jsonButton(): string {
  return `<button class="jio-btn" data-jio-open>{ } JSON</button>`;
}

// wireJsonButton attaches the open handler to any jio-open button in root.
export function wireJsonButton(root: HTMLElement, kind: string, onImported: () => void) {
  root.querySelectorAll<HTMLButtonElement>("[data-jio-open]:not([data-jio-wired])").forEach((btn) => {
    btn.dataset.jioWired = "1";
    btn.addEventListener("click", (e) => {
      e.stopPropagation();
      openJsonModal(kind, onImported);
    });
  });
}
