// deploy.ts -- the "Push to client" control center. Shows a pre-flight diff of
// every editable data file (local edited copy vs the compiled client's data.jar
// entry), lets you pick which to push, and repacks them into the client jar
// (backup + atomic). This is what turns edits into a runnable, modded client.

import {
  getPushStatus,
  pushDataToClient,
  listBackups,
  restoreBackup,
  deleteBackup,
  type PushStatus,
  type BackupEntry,
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

export function viewDeploy(container: HTMLElement) {
  container.innerHTML = `
    <div class="page-head"><h1>Deploy</h1><span class="sub">push edited data into the compiled client</span></div>
    <div class="loading">Checking client\u2026</div>`;

  const selected = new Set<string>();

  load();

  async function load() {
    let status: PushStatus;
    try {
      status = await getPushStatus();
    } catch (err) {
      container.innerHTML = errBlock((err as Error).message);
      return;
    }
    if (status.error) {
      container.innerHTML = errBlock(status.error);
      return;
    }
    // Pre-select every file that differs and is safe to push.
    selected.clear();
    for (const f of status.files) if (f.localOk && f.differs) selected.add(f.name);
    render(status);
  }

  function render(status: PushStatus) {
    const changed = status.files.filter((f) => f.localOk && f.differs).length;
    const rows = status.files
      .map((f) => {
        const state = !f.localOk
          ? `<span class="dp-state err">${esc(f.error || "unavailable")}</span>`
          : !f.inClient
          ? `<span class="dp-state new">new in client</span>`
          : f.differs
          ? `<span class="dp-state diff">modified</span>`
          : `<span class="dp-state same">up to date</span>`;
        const canPush = f.localOk && f.differs;
        return `
        <tr class="dp-row ${canPush ? "" : "disabled"}">
          <td class="dp-check">
            <input type="checkbox" data-push="${esc(f.name)}" ${
          selected.has(f.name) ? "checked" : ""
        } ${canPush ? "" : "disabled"} />
          </td>
          <td class="dp-name mono">${esc(f.name)}</td>
          <td>${state}</td>
          <td class="dp-hash mono">${f.localOk ? esc(f.localHash) : "\u2014"}</td>
          <td class="dp-arrow">${f.differs && f.localOk ? "\u2192" : ""}</td>
          <td class="dp-hash mono">${f.inClient ? esc(f.clientHash) : "\u2014"}</td>
        </tr>`;
      })
      .join("");

    container.innerHTML = `
      <div class="page-head"><h1>Deploy</h1><span class="sub">push edited data into the compiled client</span></div>

      <div class="card dp-target">
        <div class="dp-target-ico">\u2699</div>
        <div>
          <div class="dp-target-label">Target client jar</div>
          <div class="dp-target-path mono">${esc(status.clientJar || "no client detected")}</div>
        </div>
        <button class="dp-refresh" id="dpRefresh" title="Re-check">\u21BB</button>
      </div>

      <div class="dp-table-wrap">
        <table class="dp-table">
          <thead><tr>
            <th></th><th>File</th><th>Status</th><th>local</th><th></th><th>client</th>
          </tr></thead>
          <tbody>${rows}</tbody>
        </table>
      </div>

      <div class="dp-actions">
        <div class="dp-summary">${
          changed
            ? `<b>${changed}</b> file${changed === 1 ? "" : "s"} differ from the client`
            : "The client is up to date with your edits"
        }</div>
        <button class="primary dp-push" id="dpPush" ${selected.size ? "" : "disabled"}>
          \u2B06 Push ${selected.size || ""} to client
        </button>
        <span class="dp-status" id="dpStatus"></span>
      </div>

      <div class="dp-note">
        Pushing re-encodes each edited <code>data/*.dat</code> and repacks it into the client's
        <code>data.jar</code>, backing up the original jar first (a <code>.bak</code> alongside it).
        Only files whose encoder reproduces the current bytes exactly can be pushed \u2014 anything
        unsafe is disabled. Translations are pushed from the <b>Translations</b> view (into
        <code>i18n.jar</code>).
      </div>

      <div class="ov-section-title" style="margin-top:26px">Backups &amp; restore</div>
      <div id="dpBackups"><div class="loading">Loading backups\u2026</div></div>`;

    wire(status);
    loadBackups();
  }

  // --- backups panel ---

  async function loadBackups() {
    const host = container.querySelector<HTMLElement>("#dpBackups");
    if (!host) return;
    let entries: BackupEntry[] = [];
    try {
      const res = await listBackups();
      entries = res.entries ?? [];
    } catch {
      host.innerHTML = `<div class="dp-note">Could not list backups.</div>`;
      return;
    }
    if (entries.length === 0) {
      host.innerHTML = `<div class="dp-note">No backups yet. Every save/push writes a timestamped <code>.bak</code> next to the original \u2014 they'll show up here so you can roll back any change.</div>`;
      return;
    }
    const rows = entries
      .map(
        (b, i) => `
        <tr class="bk-row">
          <td class="bk-name mono">${esc(b.origName)}</td>
          <td><span class="bk-area ${esc(b.area)}">${esc(b.area)}</span></td>
          <td class="bk-stamp">${esc(b.stamp)}</td>
          <td class="bk-size mono">${fmtBytes(b.bytes)}</td>
          <td class="bk-actions">
            <button data-restore="${i}" ${b.restorable ? "" : "disabled"}>Restore</button>
            <button class="bk-del" data-del="${i}" title="Delete this backup">\u00D7</button>
          </td>
        </tr>`
      )
      .join("");
    host.innerHTML = `
      <div class="dp-table-wrap">
        <table class="dp-table bk-table">
          <thead><tr><th>File</th><th>Area</th><th>When</th><th>Size</th><th></th></tr></thead>
          <tbody>${rows}</tbody>
        </table>
      </div>
      <div class="dp-status" id="bkStatus"></div>`;

    const st = host.querySelector<HTMLElement>("#bkStatus")!;
    host.querySelectorAll<HTMLButtonElement>("[data-restore]").forEach((btn) => {
      btn.addEventListener("click", async () => {
        const b = entries[Number(btn.dataset.restore)];
        if (!confirm(`Restore ${b.origName} from ${b.stamp}?\n\nThe current file is backed up first, so this is undoable.`)) return;
        btn.disabled = true;
        st.textContent = "Restoring\u2026";
        st.className = "dp-status";
        try {
          await restoreBackup(b.path);
          st.innerHTML = `<span class="ok">Restored ${esc(b.origName)} \u00B7 current version backed up</span>`;
          setTimeout(() => {
            loadBackups();
            load();
          }, 600);
        } catch (err) {
          st.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
          btn.disabled = false;
        }
      });
    });
    host.querySelectorAll<HTMLButtonElement>("[data-del]").forEach((btn) => {
      btn.addEventListener("click", async () => {
        const b = entries[Number(btn.dataset.del)];
        if (!confirm(`Delete backup ${b.origName} (${b.stamp})? This cannot be undone.`)) return;
        try {
          await deleteBackup(b.path);
          loadBackups();
        } catch (err) {
          st.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
        }
      });
    });
  }

  function wire(status: PushStatus) {
    container.querySelector<HTMLButtonElement>("#dpRefresh")?.addEventListener("click", () => {
      container.innerHTML = `<div class="page-head"><h1>Deploy</h1></div><div class="loading">Re-checking\u2026</div>`;
      load();
    });
    container.querySelectorAll<HTMLInputElement>("[data-push]").forEach((cb) => {
      cb.addEventListener("change", () => {
        if (cb.checked) selected.add(cb.dataset.push!);
        else selected.delete(cb.dataset.push!);
        const btn = container.querySelector<HTMLButtonElement>("#dpPush")!;
        btn.disabled = selected.size === 0;
        btn.textContent = `\u2B06 Push ${selected.size || ""} to client`;
      });
    });
    container.querySelector<HTMLButtonElement>("#dpPush")?.addEventListener("click", async () => {
      if (!selected.size) return;
      const names = [...selected];
      if (
        !confirm(
          `Push ${names.length} file(s) into the client's data.jar?\n\n${names.join(
            "\n"
          )}\n\nThe original data.jar is backed up first.`
        )
      )
        return;
      const btn = container.querySelector<HTMLButtonElement>("#dpPush")!;
      const st = container.querySelector<HTMLElement>("#dpStatus")!;
      btn.disabled = true;
      st.textContent = "Pushing\u2026";
      st.className = "dp-status";
      try {
        const res = await pushDataToClient(names);
        st.innerHTML = `<span class="ok">Pushed ${res.replaced?.length ?? names.length} file(s) \u00B7 backup ${esc(
          res.backupPath?.split(/[\\/]/).pop() ?? "created"
        )}</span>`;
        setTimeout(load, 700);
      } catch (err) {
        st.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
        btn.disabled = false;
      }
    });
    void status;
  }

  function errBlock(msg: string): string {
    return `<div class="page-head"><h1>Deploy</h1></div>
      <div class="placeholder"><div class="big">\u26A0</div><div>Can't reach the client.</div>
      <div class="mono" style="margin-top:6px;font-size:12px">${esc(msg)}</div>
      <div class="hint" style="margin-top:8px">A valid client directory (with <code>data.jar</code>) is required to deploy.</div></div>`;
  }
}
