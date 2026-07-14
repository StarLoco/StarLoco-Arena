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
  validateData,
  diffBackup,
  diffPushFile,
  diffAllPushFiles,
  type PushStatus,
  type BackupEntry,
  type ValidationReport,
  type BackupDiff,
  type PushDiff,
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

// cssEsc escapes a value for use in an attribute selector (file names have dots).
function cssEsc(v: string): string {
  return (window.CSS && CSS.escape ? CSS.escape(v) : v.replace(/["\\]/g, "\\$&"));
}

// buildChangelog produces a shareable Markdown changelog of every pending
// change: per-file record-count deltas plus a data-integrity summary. This lets
// a modder document/share exactly what their build changes vs the vanilla
// client. Pure string building -- no side effects.
function buildChangelog(
  clientJar: string,
  diffs: PushDiff[],
  rep: ValidationReport | null
): string {
  const lines: string[] = [];
  lines.push("# DofusArena data changelog");
  lines.push("");
  lines.push(`_Generated ${new Date().toISOString()}_`);
  if (clientJar) lines.push(`_Target client: \`${clientJar}\`_`);
  lines.push("");

  if (diffs.length === 0) {
    lines.push("No pending data changes — local data matches the client.");
  } else {
    lines.push(`## Changed files (${diffs.length})`);
    lines.push("");
    for (const d of diffs) {
      lines.push(`### \`${d.name}\``);
      lines.push("");
      lines.push(d.note || "(changed)");
      if (d.parsed && d.deltas.length) {
        const changedRows = d.deltas.filter((r) => r.current !== r.backup);
        if (changedRows.length) {
          lines.push("");
          lines.push("| Kind | Client | New | Δ |");
          lines.push("| --- | ---: | ---: | ---: |");
          for (const r of changedRows) {
            const delta = r.backup - r.current;
            lines.push(`| ${r.kind} | ${r.current} | ${r.backup} | ${delta > 0 ? "+" : ""}${delta} |`);
          }
        }
      }
      const sizeDelta = d.localBytes - (d.clientBytes < 0 ? 0 : d.clientBytes);
      lines.push("");
      lines.push(`_Size: ${d.clientBytes < 0 ? "new" : d.clientBytes + " B"} → ${d.localBytes} B (${
        sizeDelta > 0 ? "+" : ""
      }${sizeDelta})_`);
      lines.push("");
    }
  }

  lines.push("## Data integrity");
  lines.push("");
  if (!rep) {
    lines.push("_Validation unavailable._");
  } else if (rep.errors === 0 && rep.warnings === 0) {
    lines.push(`All clear — ${rep.checked.toLocaleString()} records validated, no issues.`);
  } else {
    lines.push(
      `${rep.errors} error(s), ${rep.warnings} warning(s) across ${rep.checked.toLocaleString()} records.`
    );
    const shown = rep.issues.slice(0, 50);
    if (shown.length) {
      lines.push("");
      for (const is of shown) {
        lines.push(`- **[${is.severity}]** ${is.category}: ${is.message}`);
      }
      if (rep.issues.length > shown.length) {
        lines.push(`- _…and ${rep.issues.length - shown.length} more._`);
      }
    }
  }
  lines.push("");
  return lines.join("\n");
}

// renderPushDiff renders a PushDiff (client -> local) as a note + delta table.
// RecordDelta.current = the client's value, .backup = the local (new) value.
function renderPushDiff(d: PushDiff): string {
  const cls = d.identical ? "same" : !d.inClient ? "new" : "diff";
  const rows =
    d.parsed && d.deltas.length
      ? d.deltas
          .map((r) => {
            const delta = r.backup - r.current;
            const dcls = delta === 0 ? "" : delta > 0 ? "up" : "down";
            return `<tr><td>${esc(r.kind)}</td><td class="mono">${r.current}</td>
              <td class="dp-diff-arrow">\u2192</td><td class="mono">${r.backup}</td>
              <td class="dp-delta ${dcls}">${delta === 0 ? "" : (delta > 0 ? "+" : "") + delta}</td></tr>`;
          })
          .join("")
      : "";
  return `
    <div class="dp-diff-note ${cls}">${esc(d.note)}</div>
    ${
      rows
        ? `<table class="dp-diff-table"><thead><tr><th>Kind</th><th>client</th><th></th><th>new</th><th>\u0394</th></tr></thead><tbody>${rows}</tbody></table>`
        : ""
    }`;
}

export function viewDeploy(container: HTMLElement) {
  container.innerHTML = `
    <div class="page-head"><h1>Deploy</h1><span class="sub">push edited data into the compiled client</span></div>
    <div class="loading">Checking client\u2026</div>`;

  const selected = new Set<string>();
  // The latest integrity report, gating the push button (errors block).
  let report: ValidationReport | null = null;

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
    // Run the data-integrity validator so deploy can warn/block on broken data.
    try {
      report = await validateData();
    } catch {
      report = null; // don't block deploy if validation itself fails to run
    }
    // Pre-select every file that differs and is safe to push.
    selected.clear();
    for (const f of status.files) if (f.localOk && f.differs) selected.add(f.name);
    render(status);
  }

  // pushBlocked reports whether unresolved errors should prevent pushing.
  function pushBlocked(): boolean {
    return !!report && report.errors > 0;
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
        // Only offer a record-level diff when the file exists in the client and
        // differs (a "new" file has nothing to diff against).
        const canDiff = f.localOk && f.differs && f.inClient;
        return `
        <tr class="dp-row ${canPush ? "" : "disabled"}">
          <td class="dp-check">
            <input type="checkbox" data-push="${esc(f.name)}" ${
          selected.has(f.name) ? "checked" : ""
        } ${canPush ? "" : "disabled"} />
          </td>
          <td class="dp-name mono">${esc(f.name)}${
          canDiff ? ` <button class="dp-diff-btn" data-diff-file="${esc(f.name)}" title="Show what changes">diff</button>` : ""
        }</td>
          <td>${state}</td>
          <td class="dp-hash mono">${f.localOk ? esc(f.localHash) : "\u2014"}</td>
          <td class="dp-arrow">${f.differs && f.localOk ? "\u2192" : ""}</td>
          <td class="dp-hash mono">${f.inClient ? esc(f.clientHash) : "\u2014"}</td>
        </tr>
        <tr class="dp-diff-row" data-diff-row="${esc(f.name)}" hidden><td colspan="6"><div class="dp-diff" data-diff="${esc(f.name)}"></div></td></tr>`;
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

      ${
        changed
          ? `<div class="dp-review">
               <button class="dp-review-btn" id="dpReviewBtn">\u25B8 Review all ${changed} pending change${
              changed === 1 ? "" : "s"
            }</button>
               <button class="dp-review-btn" id="dpChangelogBtn" title="Download a Markdown changelog of every pending change">\u2B07 Changelog</button>
               <div class="dp-review-body" id="dpReviewBody" hidden></div>
             </div>`
          : ""
      }

      ${integrityBanner()}

      <div class="dp-actions">
        <div class="dp-summary">${
          changed
            ? `<b>${changed}</b> file${changed === 1 ? "" : "s"} differ from the client`
            : "The client is up to date with your edits"
        }</div>
        <button class="primary dp-push" id="dpPush" ${
          selected.size && !pushBlocked() ? "" : "disabled"
        }>
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
            <button data-preview="${i}" title="Preview what restoring changes">Preview</button>
            <button data-restore="${i}" ${b.restorable ? "" : "disabled"}>Restore</button>
            <button class="bk-del" data-del="${i}" title="Delete this backup">\u00D7</button>
          </td>
        </tr>
        <tr class="bk-diff-row" data-diff-row="${i}" hidden><td colspan="5"><div class="bk-diff" data-diff="${i}"></div></td></tr>`
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

    // Cache of fetched diffs so Restore can reuse the preview without refetch.
    const diffCache = new Map<number, BackupDiff>();

    const renderDiff = (d: BackupDiff): string => {
      const cls = d.identical ? "same" : d.currentBytes < 0 ? "new" : "diff";
      const rows = d.parsed
        ? d.deltas
            .map((r) => {
              const delta = r.backup - r.current;
              const dcls = delta === 0 ? "" : delta > 0 ? "up" : "down";
              return `<tr><td>${esc(r.kind)}</td><td class="mono">${r.current}</td>
                <td class="bk-arrow">\u2192</td><td class="mono">${r.backup}</td>
                <td class="bk-delta ${dcls}">${delta === 0 ? "" : (delta > 0 ? "+" : "") + delta}</td></tr>`;
            })
            .join("")
        : "";
      return `
        <div class="bk-diff-note ${cls}">${esc(d.note)}</div>
        ${rows ? `<table class="bk-diff-table"><thead><tr><th>Kind</th><th>current</th><th></th><th>backup</th><th>\u0394</th></tr></thead><tbody>${rows}</tbody></table>` : ""}`;
    };

    const showPreview = async (i: number): Promise<BackupDiff | null> => {
      const b = entries[i];
      const row = host.querySelector<HTMLElement>(`[data-diff-row="${i}"]`);
      const box = host.querySelector<HTMLElement>(`[data-diff="${i}"]`);
      if (!row || !box) return null;
      row.hidden = false;
      box.innerHTML = `<div class="bk-diff-loading">Comparing\u2026</div>`;
      try {
        const d = await diffBackup(b.path);
        diffCache.set(i, d);
        box.innerHTML = renderDiff(d);
        return d;
      } catch (err) {
        box.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
        return null;
      }
    };

    host.querySelectorAll<HTMLButtonElement>("[data-preview]").forEach((btn) => {
      btn.addEventListener("click", () => {
        const i = Number(btn.dataset.preview);
        const row = host.querySelector<HTMLElement>(`[data-diff-row="${i}"]`);
        if (row && !row.hidden) {
          row.hidden = true; // toggle off
          return;
        }
        void showPreview(i);
      });
    });

    host.querySelectorAll<HTMLButtonElement>("[data-restore]").forEach((btn) => {
      btn.addEventListener("click", async () => {
        const i = Number(btn.dataset.restore);
        const b = entries[i];
        // Compute (or reuse) the diff so the confirm shows what will change.
        let d = diffCache.get(i) ?? null;
        if (!d) {
          try {
            d = await diffBackup(b.path);
            diffCache.set(i, d);
          } catch {
            d = null;
          }
        }
        const summary = d
          ? d.identical
            ? "\n\nThis backup is identical to the current file \u2014 nothing changes."
            : `\n\n${d.note}`
          : "";
        if (
          !confirm(
            `Restore ${b.origName} from ${b.stamp}?${summary}\n\nThe current file is backed up first, so this is undoable.`
          )
        )
          return;
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
    container.querySelector<HTMLButtonElement>("[data-goto-diag]")?.addEventListener("click", () => {
      window.dispatchEvent(
        new CustomEvent("studio:navigate", { detail: { view: "diagnostics" } })
      );
    });

    // Consolidated "review all pending changes" block.
    const reviewBtn = container.querySelector<HTMLButtonElement>("#dpReviewBtn");
    const reviewBody = container.querySelector<HTMLElement>("#dpReviewBody");
    if (reviewBtn && reviewBody) {
      let loaded = false;
      reviewBtn.addEventListener("click", async () => {
        const open = reviewBody.hidden;
        reviewBody.hidden = !open;
        reviewBtn.innerHTML = reviewBtn.innerHTML.replace(open ? "\u25B8" : "\u25BE", open ? "\u25BE" : "\u25B8");
        if (open && !loaded) {
          reviewBody.innerHTML = `<div class="dp-diff-loading">Comparing every changed file with the client\u2026</div>`;
          try {
            const diffs = await diffAllPushFiles();
            loaded = true;
            reviewBody.innerHTML = diffs.length
              ? diffs
                  .map(
                    (d) => `<div class="dp-review-file">
                        <div class="dp-review-name mono">${esc(d.name)}</div>
                        ${renderPushDiff(d)}
                      </div>`
                  )
                  .join("")
              : `<div class="detail-empty">No differing files.</div>`;
          } catch (err) {
            reviewBody.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
          }
        }
      });
    }

    // Download a Markdown changelog of every pending change (data + integrity).
    container.querySelector<HTMLButtonElement>("#dpChangelogBtn")?.addEventListener("click", async (e) => {
      const btn = e.currentTarget as HTMLButtonElement;
      const orig = btn.textContent;
      btn.disabled = true;
      btn.textContent = "\u2026 building";
      try {
        const [diffs, rep] = await Promise.all([diffAllPushFiles(), validateData().catch(() => null)]);
        const md = buildChangelog(status.clientJar, diffs, rep);
        const blob = new Blob([md], { type: "text/markdown" });
        const url = URL.createObjectURL(blob);
        const a = document.createElement("a");
        const stamp = new Date().toISOString().slice(0, 19).replace(/[:T]/g, "-");
        a.href = url;
        a.download = `dofusarena-changelog-${stamp}.md`;
        a.click();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
      } catch (err) {
        const st = container.querySelector<HTMLElement>("#dpStatus");
        if (st) st.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
      } finally {
        btn.disabled = false;
        btn.textContent = orig;
      }
    });

    // Per-file record-level diff (expand/collapse) between local and client.
    container.querySelectorAll<HTMLButtonElement>("[data-diff-file]").forEach((btn) => {
      btn.addEventListener("click", async () => {
        const name = btn.dataset.diffFile!;
        const row = container.querySelector<HTMLElement>(`[data-diff-row="${cssEsc(name)}"]`);
        const box = container.querySelector<HTMLElement>(`[data-diff="${cssEsc(name)}"]`);
        if (!row || !box) return;
        if (!row.hidden) {
          row.hidden = true; // toggle off
          return;
        }
        row.hidden = false;
        box.innerHTML = `<div class="dp-diff-loading">Comparing with client\u2026</div>`;
        try {
          const d = await diffPushFile(name);
          box.innerHTML = renderPushDiff(d);
        } catch (err) {
          box.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
        }
      });
    });
    container.querySelectorAll<HTMLInputElement>("[data-push]").forEach((cb) => {
      cb.addEventListener("change", () => {
        if (cb.checked) selected.add(cb.dataset.push!);
        else selected.delete(cb.dataset.push!);
        const btn = container.querySelector<HTMLButtonElement>("#dpPush")!;
        btn.disabled = selected.size === 0 || pushBlocked();
        btn.textContent = `\u2B06 Push ${selected.size || ""} to client`;
      });
    });
    container.querySelector<HTMLButtonElement>("#dpPush")?.addEventListener("click", async () => {
      if (!selected.size) return;
      if (pushBlocked()) {
        const st = container.querySelector<HTMLElement>("#dpStatus")!;
        st.innerHTML = `<span class="err">Resolve integrity errors before deploying.</span>`;
        return;
      }
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

  // integrityBanner renders the pre-flight validation summary. Errors block the
  // push (deploying broken references would ship a broken client); warnings are
  // advisory. Clean data gets a compact confirmation. A link opens Diagnostics.
  function integrityBanner(): string {
    if (!report) return "";
    const diag = `<button class="xlink" data-goto-diag>View Diagnostics <span class="xlink-ico">\u2197</span></button>`;
    if (report.errors > 0) {
      return `<div class="dp-integrity err">
        <span class="dpi-ico">\u2716</span>
        <div class="dpi-body">
          <b>Deploy blocked \u2014 ${report.errors} integrity error${report.errors === 1 ? "" : "s"}</b>
          <div>Fix the broken references before pushing, or you'll ship a client that misbehaves.${
            report.warnings ? ` (${report.warnings} warning${report.warnings === 1 ? "" : "s"} too.)` : ""
          }</div>
        </div>
        ${diag}
      </div>`;
    }
    if (report.warnings > 0) {
      return `<div class="dp-integrity warn">
        <span class="dpi-ico">\u26A0</span>
        <div class="dpi-body">
          <b>${report.warnings} integrity warning${report.warnings === 1 ? "" : "s"}</b>
          <div>Deploy is allowed, but review these first \u2014 they may indicate incomplete edits.</div>
        </div>
        ${diag}
      </div>`;
    }
    return `<div class="dp-integrity ok">
      <span class="dpi-ico">\u2714</span>
      <div class="dpi-body"><b>Data integrity: clean</b><div>All ${report.checked.toLocaleString()} records validated with no issues.</div></div>
      ${diag}
    </div>`;
  }

  function errBlock(msg: string): string {
    return `<div class="page-head"><h1>Deploy</h1></div>
      <div class="placeholder"><div class="big">\u26A0</div><div>Can't reach the client.</div>
      <div class="mono" style="margin-top:6px;font-size:12px">${esc(msg)}</div>
      <div class="hint" style="margin-top:8px">A valid client directory (with <code>data.jar</code>) is required to deploy.</div></div>`;
  }
}
