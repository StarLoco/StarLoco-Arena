// editform.ts -- a small reusable inline editor for a record's scalar fields.
// A view declares the fields (label + type + current value), and this renders a
// grid form with a Save button that calls the provided save function with the
// edited values. It handles dirty tracking, in-flight state, and status/backup
// messaging, so every data type gets a consistent admin editor with almost no
// boilerplate.

import type { ExportResult } from "./backend";
import { setDirty, markClean } from "./dirty";
import { t } from "./i18n";

export type FieldType = "number" | "text" | "bool";

export interface EditField {
  key: string;
  label: string;
  type: FieldType;
  value: number | string | boolean;
  min?: number;
  max?: number;
  hint?: string;
}

export interface EditFormSpec {
  id: number;
  title: string;
  fields: EditField[];
  // save receives the edited values keyed by field.key and returns the export
  // result (target + backup path).
  save: (values: Record<string, number | string | boolean>) => Promise<ExportResult>;
  // dirtyKey uniquely identifies this form in the global unsaved-changes
  // registry; defaults to the title if omitted.
  dirtyKey?: string;
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// editFormHTML renders the form markup. Wire it afterwards with wireEditForm.
export function editFormHTML(spec: EditFormSpec): string {
  const field = (f: EditField) => {
    if (f.type === "bool") {
      return `<label class="ef-chk"><input type="checkbox" data-ef="${esc(f.key)}" ${
        f.value ? "checked" : ""
      }/> ${esc(f.label)}</label>`;
    }
    const t = f.type === "number" ? "number" : "text";
    const attrs =
      f.type === "number"
        ? `min="${f.min ?? -2147483648}" max="${f.max ?? 2147483647}"`
        : "";
    return `<label class="ef-field">
        <span>${esc(f.label)}</span>
        <input class="ef-in ${f.type === "text" ? "mono" : ""}" type="${t}" ${attrs} data-ef="${esc(
      f.key
    )}" value="${esc(f.value)}" />
        ${f.hint ? `<small>${esc(f.hint)}</small>` : ""}
      </label>`;
  };
  const bools = spec.fields.filter((f) => f.type === "bool");
  const rest = spec.fields.filter((f) => f.type !== "bool");
  return `
    <div class="edit-form" data-ef-id="${spec.id}">
      <div class="edit-title">${esc(spec.title)}</div>
      <div class="edit-grid">${rest.map(field).join("")}</div>
      ${bools.length ? `<div class="edit-checks">${bools.map(field).join("")}</div>` : ""}
      <div class="edit-actions">
        <button class="primary" data-ef-save>\u2B07 ${esc(t("ef.save"))}</button>
        <span class="edit-status" data-ef-status></span>
      </div>
    </div>`;
}

// wireEditForm attaches the save handler to a rendered edit form inside root.
// Call after every (re)render (the table drawer re-renders on interaction).
export function wireEditForm(root: HTMLElement, specs: EditFormSpec[]): void {
  const byId = new Map(specs.map((s) => [s.id, s]));
  root.querySelectorAll<HTMLElement>(".edit-form").forEach((form) => {
    // Stop drawer collapse when interacting with the form.
    form.addEventListener("mousedown", (e) => e.stopPropagation());
    form.addEventListener("click", (e) => {
      const tgt = e.target as HTMLElement;
      if (!tgt.matches("[data-ef-save]")) e.stopPropagation();
    });
    const id = Number(form.dataset.efId);
    const spec = byId.get(id);
    const dkey = spec ? `form:${spec.dirtyKey ?? spec.title}` : "";

    // Track unsaved changes: compare each field to its original spec value and
    // register/clear this form's dirty key in the global registry.
    if (spec) {
      const original: Record<string, string> = {};
      for (const f of spec.fields) original[f.key] = String(f.value);
      const recompute = () => {
        let dirty = false;
        form.querySelectorAll<HTMLInputElement>("[data-ef]").forEach((inp) => {
          const f = spec.fields.find((x) => x.key === inp.dataset.ef);
          if (!f) return;
          const cur = f.type === "bool" ? String(inp.checked) : inp.value;
          if (cur !== original[f.key]) dirty = true;
        });
        setDirty(dkey, dirty);
      };
      form.querySelectorAll<HTMLInputElement>("[data-ef]").forEach((inp) => {
        inp.addEventListener("input", recompute);
        inp.addEventListener("change", recompute);
      });
    }

    const saveBtn = form.querySelector<HTMLButtonElement>("[data-ef-save]");
    saveBtn?.addEventListener("click", async (e) => {
      e.stopPropagation();
      if (!spec) return;
      const status = form.querySelector<HTMLElement>("[data-ef-status]")!;
      const values: Record<string, number | string | boolean> = {};
      form.querySelectorAll<HTMLInputElement>("[data-ef]").forEach((inp) => {
        const f = spec.fields.find((x) => x.key === inp.dataset.ef);
        if (!f) return;
        if (f.type === "bool") values[f.key] = inp.checked;
        else if (f.type === "number") values[f.key] = Number(inp.value);
        else values[f.key] = inp.value;
      });
      saveBtn.disabled = true;
      status.textContent = t("ef.saving");
      status.className = "edit-status";
      try {
        const res = await spec.save(values);
        const backup = res.backupPath?.split(/[\\/]/).pop();
        status.textContent = backup ? t("ef.saved", { name: backup }) : t("ef.savedNoBackup");
        status.className = "edit-status ok";
        markClean(dkey); // saved -> no longer dirty
      } catch (err) {
        status.textContent = t("ef.failed", { msg: (err as Error).message });
        status.className = "edit-status err";
      } finally {
        saveBtn.disabled = false;
      }
    });
  });
}
