// diagnostics.ts -- a data-integrity lint view. Runs the backend validator over
// every record repository and lists broken cross-references and suspicious
// values (missing scripts/summons/areas, unknown actions, bad ranges, duplicate
// ids). Each finding deep-links to the offending record via the navigate bus,
// so fixing an issue is one click away. A clean run is celebrated explicitly.

import { validateData, type ValidationIssue, type ValidationReport } from "./backend";
import { link, wireCrosslinks } from "./crosslink";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

const SEV_META: Record<string, { glyph: string; label: string }> = {
  error: { glyph: "\u2716", label: "error" },
  warning: { glyph: "\u26A0", label: "warning" },
  info: { glyph: "\u2139", label: "info" },
};

export function viewDiagnostics(c: HTMLElement) {
  c.innerHTML = `
    <div class="page-head"><h1>Diagnostics</h1><span class="sub">data-integrity validation</span></div>
    <div class="dg-loading">Validating game data\u2026</div>`;

  validateData()
    .then((rep) => render(c, rep))
    .catch((err) => {
      c.innerHTML =
        `<div class="page-head"><h1>Diagnostics</h1><span class="sub">data-integrity validation</span></div>` +
        `<div class="placeholder"><div class="big">\u26A0</div><div>Could not validate.</div>` +
        `<div class="mono" style="margin-top:6px;font-size:12.5px">${esc((err as Error).message)}</div></div>`;
    });
}

function render(c: HTMLElement, rep: ValidationReport) {
  const total = rep.issues.length;
  const stat = (n: number, label: string, cls: string) =>
    `<div class="dg-stat ${cls}"><div class="dg-n">${n.toLocaleString()}</div><div class="dg-l">${label}</div></div>`;

  c.innerHTML = `
    <div class="page-head">
      <h1>Diagnostics</h1>
      <span class="sub">${rep.checked.toLocaleString()} records checked</span>
      <div class="page-actions"><button class="rc-new" data-revalidate>Re-run</button></div>
    </div>
    <div class="dg-statband">
      ${stat(rep.errors, "errors", "err")}
      ${stat(rep.warnings, "warnings", "warn")}
      ${stat(rep.infos, "infos", "info")}
      ${stat(rep.checked, "checked", "ok")}
    </div>
    ${
      total === 0
        ? `<div class="dg-clean"><div class="dg-clean-ico">\u2714</div>
             <div><b>All clear.</b><div>No broken references or suspicious values found across ${rep.checked.toLocaleString()} records.</div></div>
           </div>`
        : `<div class="dg-toolbar">
             <div class="dg-filters">
               <button class="dg-chip on" data-sev="">all (${total})</button>
               ${rep.errors ? `<button class="dg-chip" data-sev="error">errors (${rep.errors})</button>` : ""}
               ${rep.warnings ? `<button class="dg-chip" data-sev="warning">warnings (${rep.warnings})</button>` : ""}
               ${rep.infos ? `<button class="dg-chip" data-sev="info">infos (${rep.infos})</button>` : ""}
             </div>
             <input class="dg-search" type="search" placeholder="Filter by message or category\u2026" />
           </div>
           <div class="dg-list"></div>`
    }`;

  c.querySelector<HTMLButtonElement>("[data-revalidate]")?.addEventListener("click", () =>
    viewDiagnostics(c)
  );
  if (total === 0) return;

  const listEl = c.querySelector<HTMLElement>(".dg-list")!;
  const search = c.querySelector<HTMLInputElement>(".dg-search")!;
  let sevFilter = "";

  const rowHTML = (is: ValidationIssue) => {
    const m = SEV_META[is.severity] ?? SEV_META.info;
    const jump = link(is.view, is.query, `#${is.recordId}`, "\u2197");
    return `
      <div class="dg-row sev-${esc(is.severity)}">
        <span class="dg-sev" title="${esc(m.label)}">${m.glyph}</span>
        <span class="dg-cat">${esc(is.category)}</span>
        <span class="dg-msg">${esc(is.message)}</span>
        <span class="dg-jump">${jump}</span>
      </div>`;
  };

  const draw = () => {
    const q = search.value.trim().toLowerCase();
    const rows = rep.issues.filter((is) => {
      if (sevFilter && is.severity !== sevFilter) return false;
      if (q && !(`${is.message} ${is.category}`.toLowerCase().includes(q))) return false;
      return true;
    });
    listEl.innerHTML = rows.length
      ? rows.map(rowHTML).join("")
      : `<div class="detail-empty">No issues match.</div>`;
    wireCrosslinks(listEl);
  };

  c.querySelectorAll<HTMLButtonElement>(".dg-chip").forEach((chip) => {
    chip.addEventListener("click", () => {
      sevFilter = chip.dataset.sev ?? "";
      c.querySelectorAll(".dg-chip").forEach((x) => x.classList.toggle("on", x === chip));
      draw();
    });
  });
  search.addEventListener("input", draw);
  draw();
}
