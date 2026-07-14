// effecteditor.ts -- a full add/remove/reorder/edit editor for a spell's
// effects. Each row edits the action (with human labels), params (value or
// dice), duration, area shape+size, crit flag and target bits, and shows a LIVE
// human decode of what it does. Saving writes the whole effect list back into
// spells.dat (backup + atomic, byte-exact-gated) via SaveSpellEffects.

import {
  saveSpellEffects,
  saveFighterCardEffects,
  saveEventEffects,
  type EffectDef,
  type EffectEditDTO,
  type ExportResult,
} from "./backend";
import { decodeEffectHTML, actionOptions, gameIcon } from "./effectlore";

// EffectParentKind selects which .dat file the effects are written back to.
export type EffectParentKind = "spell" | "card" | "event";

// parentMeta returns the save fn + human file label for a parent kind.
function parentMeta(kind: EffectParentKind): {
  save: (id: number, effects: EffectEditDTO[]) => Promise<ExportResult>;
  file: string;
} {
  switch (kind) {
    case "card":
      return { save: saveFighterCardEffects, file: "cards.dat" };
    case "event":
      return { save: saveEventEffects, file: "events.dat" };
    default:
      return { save: saveSpellEffects, file: "spells.dat" };
  }
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// numsToStr / strToNums convert a numeric slice to/from a comma string field.
function numsToStr(a: number[] | null | undefined): string {
  return a && a.length ? a.join(", ") : "";
}
function strToNums(s: string): number[] | null {
  const parts = s
    .split(/[,\s]+/)
    .map((x) => x.trim())
    .filter((x) => x !== "")
    .map(Number)
    .filter((n) => !Number.isNaN(n));
  return parts.length ? parts : null;
}

// effectToDTO converts a parsed EffectDef to the editable DTO.
function effectToDTO(e: EffectDef): EffectEditDTO {
  return {
    id: e.ID,
    reserved: 0,
    actionId: e.ActionID,
    isCritical: e.IsCritical,
    duration: e.Duration,
    params: e.Params,
    areaShape: e.AreaShape,
    areaSize: e.AreaSize,
    targets: e.Targets,
    triggersAfter: e.TriggersAfter,
    triggersBefore: e.TriggersBefore,
    affectedByLocalisation: e.AffectedByLocalisation,
  };
}

// dtoToEffectDef adapts a DTO back to an EffectDef so the human decoder can
// preview it live.
function dtoToEffectDef(d: EffectEditDTO): EffectDef {
  return {
    ID: d.id,
    ParentType: "",
    ParentID: 0,
    Duration: d.duration,
    ActionID: d.actionId,
    IsCritical: d.isCritical,
    Params: d.params,
    AreaShape: d.areaShape,
    AreaSize: d.areaSize,
    Targets: d.targets,
    TriggersAfter: d.triggersAfter,
    TriggersBefore: d.triggersBefore,
    AffectedByLocalisation: d.affectedByLocalisation,
  };
}

// mountEffectEditor renders the editor into host for one parent record (spell,
// fighter card or event). Fully self-contained: manages its own draft state and
// Save, writing back to the correct .dat file for the parent kind.
export function mountEffectEditor(
  host: HTMLElement,
  parentId: number,
  effects: EffectDef[] | null,
  kind: EffectParentKind = "spell"
) {
  const meta = parentMeta(kind);
  let draft: EffectEditDTO[] = (effects ?? []).map(effectToDTO);

  function draw() {
    const opts = actionOptions();
    const rows = draft
      .map((d, i) => {
        const optHtml = opts
          .map(
            (o) =>
              `<option value="${o.id}" ${o.id === d.actionId ? "selected" : ""}>${esc(o.label)}</option>`
          )
          .join("");
        return `
        <div class="fe-row" data-i="${i}">
          <div class="fe-row-head">
            <span class="fe-num">#${i + 1}</span>
            <select class="fe-action" data-f="actionId">${optHtml}</select>
            <label class="fe-crit"><input type="checkbox" data-f="isCritical" ${
              d.isCritical ? "checked" : ""
            }/> crit</label>
            <div class="fe-row-btns">
              <button data-move="-1" title="Move up" ${i === 0 ? "disabled" : ""}>\u2191</button>
              <button data-move="1" title="Move down" ${
                i === draft.length - 1 ? "disabled" : ""
              }>\u2193</button>
              <button class="fe-del" data-del title="Remove effect">\u00D7</button>
            </div>
          </div>
          <div class="fe-fields">
            <label class="fe-fld"><span>params</span><input data-f="params" value="${esc(
              numsToStr(d.params)
            )}" placeholder="value  or  dice,faces,mod" /></label>
            <label class="fe-fld"><span>duration</span><input data-f="duration" value="${esc(
              numsToStr(d.duration)
            )}" placeholder="turns" /></label>
            <label class="fe-fld sm"><span>area shape</span><input data-f="areaShape" type="number" value="${
              d.areaShape
            }" /></label>
            <label class="fe-fld"><span>area size</span><input data-f="areaSize" value="${esc(
              numsToStr(d.areaSize)
            )}" /></label>
            <label class="fe-fld"><span>targets</span><input data-f="targets" value="${esc(
              numsToStr(d.targets)
            )}" placeholder="condition bits" /></label>
          </div>
          <div class="fe-preview">${decodeEffectHTML(dtoToEffectDef(d))}</div>
        </div>`;
      })
      .join("");

    host.innerHTML = `
      <div class="fe-editor">
        <div class="fe-head">
          <b>Effects</b>
          <span class="fe-count">${draft.length}</span>
          <button class="fe-add" data-add>+ Add effect</button>
        </div>
        <div class="fe-rows">${rows || `<div class="detail-empty">No effects. Add one to get started.</div>`}</div>
        <div class="fe-actions">
          <button class="primary" data-save>${gameIcon("ap", 14) || ""} Save effects to ${meta.file}</button>
          <span class="fe-status" data-status></span>
        </div>
      </div>`;
    wire();
  }

  function wire() {
    // Prevent the drawer from collapsing while editing.
    host.querySelector(".fe-editor")?.addEventListener("mousedown", (e) => e.stopPropagation());
    host.querySelector(".fe-editor")?.addEventListener("click", (e) => {
      const t = e.target as HTMLElement;
      if (!t.matches("[data-save],[data-add],[data-del],[data-move]")) e.stopPropagation();
    });

    host.querySelector<HTMLButtonElement>("[data-add]")?.addEventListener("click", (e) => {
      e.stopPropagation();
      draft.push({
        id: 0,
        reserved: 0,
        actionId: 1,
        isCritical: false,
        duration: null,
        params: [10],
        areaShape: 1,
        areaSize: null,
        targets: null,
        triggersAfter: null,
        triggersBefore: null,
        affectedByLocalisation: false,
      });
      draw();
    });

    host.querySelectorAll<HTMLElement>(".fe-row").forEach((row) => {
      const i = Number(row.dataset.i);
      row.querySelectorAll<HTMLElement>("[data-f]").forEach((el) => {
        el.addEventListener("input", () => applyField(i, el as HTMLInputElement | HTMLSelectElement));
        el.addEventListener("change", () => applyField(i, el as HTMLInputElement | HTMLSelectElement, true));
      });
      row.querySelector<HTMLButtonElement>("[data-del]")?.addEventListener("click", (e) => {
        e.stopPropagation();
        draft.splice(i, 1);
        draw();
      });
      row.querySelectorAll<HTMLButtonElement>("[data-move]").forEach((btn) => {
        btn.addEventListener("click", (e) => {
          e.stopPropagation();
          const dir = Number(btn.dataset.move);
          const j = i + dir;
          if (j < 0 || j >= draft.length) return;
          [draft[i], draft[j]] = [draft[j], draft[i]];
          draw();
        });
      });
    });

    host.querySelector<HTMLButtonElement>("[data-save]")?.addEventListener("click", async (e) => {
      e.stopPropagation();
      const status = host.querySelector<HTMLElement>("[data-status]")!;
      const btn = e.currentTarget as HTMLButtonElement;
      btn.disabled = true;
      status.textContent = "Saving\u2026";
      status.className = "fe-status";
      try {
        const res = await meta.save(parentId, draft);
        status.innerHTML = `<span class="ok">Saved ${res.bytes} B \u00B7 backup created</span>`;
      } catch (err) {
        status.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
      } finally {
        btn.disabled = false;
      }
    });
  }

  // applyField writes a field edit into the draft; redraw only the preview (on
  // change) so typing in params doesn't lose focus.
  function applyField(i: number, el: HTMLInputElement | HTMLSelectElement, redrawPreview = false) {
    const d = draft[i];
    const f = (el as HTMLElement).dataset.f!;
    switch (f) {
      case "actionId":
        d.actionId = Number((el as HTMLSelectElement).value);
        break;
      case "isCritical":
        d.isCritical = (el as HTMLInputElement).checked;
        break;
      case "areaShape":
        d.areaShape = Number((el as HTMLInputElement).value);
        break;
      case "params":
        d.params = strToNums((el as HTMLInputElement).value);
        break;
      case "duration":
        d.duration = strToNums((el as HTMLInputElement).value);
        break;
      case "areaSize":
        d.areaSize = strToNums((el as HTMLInputElement).value);
        break;
      case "targets":
        d.targets = strToNums((el as HTMLInputElement).value);
        break;
    }
    if (redrawPreview) {
      const row = host.querySelector<HTMLElement>(`.fe-row[data-i="${i}"] .fe-preview`);
      if (row) row.innerHTML = decodeEffectHTML(dtoToEffectDef(d));
    }
  }

  draw();
}
