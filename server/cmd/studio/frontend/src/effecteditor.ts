// effecteditor.ts -- a full add/remove/reorder/edit editor for a spell's
// effects. Each row edits the action (human labels), the value (a mode switch:
// none / flat amount / dice roll, or a summon/area reference), the duration (a
// turns field + permanent toggle), the area of effect (shape dropdown + radius)
// and the target conditions (one checkbox per validator bit) -- so nobody has to
// guess what raw numbers mean. A LIVE human decode of the effect is shown under
// each row. Saving writes the whole effect list back into the parent .dat file
// (backup + atomic, byte-exact-gated).

import {
  saveSpellEffects,
  saveFighterCardEffects,
  saveEventEffects,
  type EffectDef,
  type EffectEditDTO,
  type ExportResult,
} from "./backend";
import {
  decodeEffectHTML,
  actionOptions,
  gameIcon,
  areaShapeOptions,
  areaShapeName,
  targetConditionBits,
  effectKind,
} from "./effectlore";
import { setDirty, markClean } from "./dirty";
import { wireCrosslinks } from "./crosslink";
import { t } from "./i18n";

// shapeLabel localizes an area-shape name (backend returns English keys).
function shapeLabel(name: string): string {
  const key = `fx.shape.${name}`;
  const s = t(key);
  return s === key ? name : s;
}

// targetLabel localizes a FightTargetValidator condition label (backend English).
const TARGET_KEY: Record<string, string> = {
  "in area": "fx.tgt.inArea",
  "self (caster)": "fx.tgt.self",
  ally: "fx.tgt.ally",
  enemy: "fx.tgt.enemy",
  "not caster": "fx.tgt.notCaster",
  summon: "fx.tgt.summon",
  "not summon": "fx.tgt.notSummon",
};
function targetLabel(label: string): string {
  const key = TARGET_KEY[label];
  return key ? t(key) : label;
}

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

// ---- guided field controls --------------------------------------------------
// The raw EffectDef fields (params/duration/areaShape/areaSize/targets) are
// opaque numbers. These builders turn them into human controls that spell out
// what each value means and constrain input to what's valid.

const PERMANENT = 63; // duration >= 63 reads as "permanent" (see effectlore)

// paramsMode classifies how a params slice should be edited, based on the
// effect's semantic kind and the current shape of the data.
type ParamsMode = "none" | "flat" | "dice" | "ref";
function paramsModeFor(d: EffectEditDTO): ParamsMode {
  const kind = effectKind(d.actionId);
  if (kind === "summon" || kind === "summon_double" || kind === "summon_mirror" || kind === "set_area") {
    return "ref";
  }
  const p = d.params;
  if (!p || p.length === 0) return "none";
  if (p.length >= 3) return "dice";
  return "flat";
}

// valueControl renders the params editor appropriate to the effect kind, with a
// mode switch (none / flat / dice) for regular effects, or a single reference
// field for summon / set-area effects.
// fldRow lays out one labelled control as a clean definition row: a fixed-width
// left label, the control(s) in the middle, and a muted plain-language note on
// the right that explains the current value.
function fldRow(label: string, control: string, note: string): string {
  return `
    <div class="fe-fld3">
      <div class="fe-fld3-lbl">${esc(label)}</div>
      <div class="fe-fld3-ctl">${control}</div>
      <div class="fe-fld3-note">${note}</div>
    </div>`;
}

function valueControl(d: EffectEditDTO, i: number): string {
  const kind = effectKind(d.actionId);
  const mode = paramsModeFor(d);

  if (mode === "ref") {
    const isArea = kind === "set_area";
    const refLabel = isArea ? t("fx.areaEffectId") : t("fx.summonId");
    const hint = isArea ? t("fx.areaEffectHint") : t("fx.summonHint");
    const val = d.params && d.params.length ? d.params[0] : 0;
    return fldRow(
      t("fx.value"),
      `<span class="fe-unit">${esc(refLabel)}</span>
       <input type="number" class="fe-in" data-p="ref" data-i="${i}" value="${val}" />`,
      esc(hint)
    );
  }

  const p = d.params ?? [];
  const flat = mode === "flat" ? p[0] ?? 0 : 0;
  const dice = mode === "dice" ? p[0] ?? 0 : 0;
  const faces = mode === "dice" ? p[1] ?? 0 : 0;
  const mod = mode === "dice" ? p[2] ?? 0 : 0;

  const opt = (m: ParamsMode, label: string) =>
    `<option value="${m}" ${mode === m ? "selected" : ""}>${esc(label)}</option>`;
  const modeSel = `<select class="fe-in fe-mode" data-p="mode" data-i="${i}">${opt(
    "none",
    t("fx.mode.none")
  )}${opt("flat", t("fx.mode.flat"))}${opt("dice", t("fx.mode.dice"))}</select>`;

  if (mode === "none") {
    return fldRow(t("fx.value"), modeSel, t("fx.noteNone"));
  }
  if (mode === "flat") {
    return fldRow(
      t("fx.value"),
      `${modeSel}<input type="number" class="fe-in fe-in-sm" data-p="flat" data-i="${i}" value="${flat}" />`,
      t("fx.noteFlat", { n: `<b>${flat}</b>` })
    );
  }
  const modTxt = mod ? (mod > 0 ? `+${mod}` : `${mod}`) : "";
  const formula = `<b>${dice || "N"}d${faces || "N"}${modTxt}</b>${diceRange(dice, faces, mod)}`;
  return fldRow(
    t("fx.value"),
    `${modeSel}
     <input type="number" min="0" class="fe-in fe-in-xs" data-p="dice" data-i="${i}" value="${dice}" />
     <span class="fe-x">d</span>
     <input type="number" min="0" class="fe-in fe-in-xs" data-p="faces" data-i="${i}" value="${faces}" />
     <span class="fe-x">+</span>
     <input type="number" class="fe-in fe-in-xs" data-p="mod" data-i="${i}" value="${mod}" />`,
    t("fx.noteDice", { formula })
  );
}

function diceRange(dice: number, faces: number, mod: number): string {
  if (dice <= 0 || faces <= 0) return "";
  const lo = dice * 1 + mod;
  const hi = dice * faces + mod;
  return ` &rarr; ${lo === hi ? lo : `${lo}\u2013${hi}`}`;
}

// durationControl renders a turns field plus a "permanent" toggle. Only the
// first duration element is meaningful to the engine.
function durationControl(d: EffectEditDTO, i: number): string {
  const v = d.duration && d.duration.length ? d.duration[0] : 0;
  const perm = v >= PERMANENT;
  const turns = perm ? "" : v > 0 ? String(v) : "0";
  const note = perm
    ? t("fx.durPerm")
    : v <= 0
    ? t("fx.durInstant")
    : v === 1
    ? t("fx.durOne")
    : t("fx.durMany", { n: v });
  return fldRow(
    t("fx.duration"),
    `<input type="number" min="0" class="fe-in fe-in-sm" data-p="durTurns" data-i="${i}" value="${turns}" ${
      perm ? "disabled" : ""
    } /><span class="fe-unit">${esc(t("fx.turns"))}</span>
     <label class="fe-chk"><input type="checkbox" data-p="durPerm" data-i="${i}" ${
      perm ? "checked" : ""
    } /> ${esc(t("fx.permanent"))}</label>`,
    note
  );
}

// areaControl renders a shape dropdown + a size stepper (only meaningful when the
// shape is not point/none).
function areaControl(d: EffectEditDTO, i: number): string {
  const shapes = areaShapeOptions();
  const name = areaShapeName(d.areaShape);
  const hasSize = name !== "point" && name !== "none" && name !== "";
  const size = d.areaSize && d.areaSize.length ? d.areaSize[0] : 0;
  const shapeOpts = shapes
    .map(
      (s) =>
        `<option value="${s.id}" ${s.id === d.areaShape ? "selected" : ""}>${esc(shapeLabel(s.label))}</option>`
    )
    .join("");
  const note = !hasSize
    ? t("fx.areaPoint")
    : t("fx.areaShaped", { shape: esc(shapeLabel(name)), n: size });
  return fldRow(
    t("fx.area"),
    `<select class="fe-in" data-p="shape" data-i="${i}">${shapeOpts}</select>
     ${
       hasSize
         ? `<span class="fe-unit">${esc(t("fx.radius"))}</span><input type="number" min="0" class="fe-in fe-in-xs" data-p="size" data-i="${i}" value="${size}" />`
         : ""
     }`,
    note
  );
}

// targetsControl renders one checkbox per known FightTargetValidator condition
// bit; the resulting value is their OR. Unknown extra bits are preserved.
function targetsControl(d: EffectEditDTO, i: number): string {
  const bits = targetConditionBits();
  const cur = d.targets && d.targets.length ? d.targets[0] : 0;
  const boxes = bits
    .map(
      (b) =>
        `<label class="fe-tag ${cur & b.bit ? "on" : ""}"><input type="checkbox" data-p="target" data-bit="${b.bit}" data-i="${i}" ${
          cur & b.bit ? "checked" : ""
        } /> ${esc(targetLabel(b.label))}</label>`
    )
    .join("");
  return fldRow(
    t("fx.targets"),
    `<div class="fe-tags">${boxes}</div>`,
    cur ? "" : t("fx.targetsNone")
  );
}

// fieldsHTML lays out the four guided controls for one effect row.
function fieldsHTML(d: EffectEditDTO, i: number): string {
  return `
    <div class="fe-fields2">
      ${valueControl(d, i)}
      ${durationControl(d, i)}
      ${areaControl(d, i)}
      ${targetsControl(d, i)}
    </div>`;
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
  const dkey = `effects:${kind}:${parentId}`;
  let baseline = JSON.stringify(draft);
  // Which effect rows have their editor expanded (index-based). Default: all
  // collapsed, so the list reads as clean human sentences until you edit one.
  const openRows = new Set<number>();

  // recomputeDirty flags this editor dirty whenever the draft diverges from the
  // effects it was mounted with.
  function recomputeDirty() {
    setDirty(dkey, JSON.stringify(draft) !== baseline);
  }

  // remapOpen rewrites the open-editor index set through `fn` after a reorder or
  // delete so the right rows stay expanded (fn returns -1 to drop an index).
  function remapOpen(fn: (idx: number) => number) {
    const next = new Set<number>();
    for (const idx of openRows) {
      const m = fn(idx);
      if (m >= 0) next.add(m);
    }
    openRows.clear();
    for (const idx of next) openRows.add(idx);
  }

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
        const open = openRows.has(i);
        return `
        <div class="fe-row ${open ? "open" : ""}" data-i="${i}">
          <div class="fe-row-top">
            <span class="fe-num">${i + 1}</span>
            <div class="fe-preview">${decodeEffectHTML(dtoToEffectDef(d))}</div>
            <div class="fe-row-btns">
              <button class="fe-edit-toggle" data-toggle title="${esc(open ? t("fx.done") : t("fx.edit"))}">${esc(
                open ? t("fx.done") : t("fx.edit")
              )}</button>
              <button data-move="-1" title="${esc(t("fx.moveUp"))}" ${i === 0 ? "disabled" : ""}>\u2191</button>
              <button data-move="1" title="${esc(t("fx.moveDown"))}" ${
                i === draft.length - 1 ? "disabled" : ""
              }>\u2193</button>
              <button class="fe-del" data-del title="${esc(t("fx.remove"))}">\u00D7</button>
            </div>
          </div>
          ${
            open
              ? `<div class="fe-editpane">
                   <div class="fe-editrow">
                     <label class="fe-editlbl">${esc(t("fx.effectLabel"))}</label>
                     <select class="fe-action" data-f="actionId">${optHtml}</select>
                     <label class="fe-crit"><input type="checkbox" data-f="isCritical" ${
                       d.isCritical ? "checked" : ""
                     }/> ${esc(t("fx.critical"))}</label>
                   </div>
                   ${fieldsHTML(d, i)}
                 </div>`
              : ""
          }
        </div>`;
      })
      .join("");

    host.innerHTML = `
      <div class="fe-editor">
        <div class="fe-head">
          <b>${esc(t("fx.effects"))}</b>
          <span class="fe-count">${draft.length}</span>
          <button class="fe-add" data-add>${esc(t("fx.add"))}</button>
        </div>
        <div class="fe-rows">${rows || `<div class="detail-empty">${esc(t("fx.empty"))}</div>`}</div>
        <div class="fe-actions">
          <button class="primary" data-save>${gameIcon("ap", 14) || ""} ${esc(
      t("fx.saveTo", { file: meta.file })
    )}</button>
          <span class="fe-status" data-status></span>
        </div>
      </div>`;
    wire();
    recomputeDirty();
  }

  function wire() {
    // Prevent the drawer from collapsing while editing.
    host.querySelector(".fe-editor")?.addEventListener("mousedown", (e) => e.stopPropagation());
    host.querySelector(".fe-editor")?.addEventListener("click", (e) => {
      const tgt = e.target as HTMLElement;
      if (!tgt.matches("[data-save],[data-add],[data-del],[data-move],[data-toggle]"))
        e.stopPropagation();
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
      openRows.add(draft.length - 1); // reveal the editor for the new effect
      draw();
    });

    host.querySelectorAll<HTMLElement>(".fe-row").forEach((row) => {
      const i = Number(row.dataset.i);
      // Head controls (action / crit) use data-f.
      row.querySelectorAll<HTMLElement>("[data-f]").forEach((el) => {
        el.addEventListener("input", () => applyField(i, el as HTMLInputElement | HTMLSelectElement));
        el.addEventListener("change", () => applyField(i, el as HTMLInputElement | HTMLSelectElement, true));
      });
      // Guided value/duration/area/target controls use data-p.
      row.querySelectorAll<HTMLElement>("[data-p]").forEach((el) => {
        const ctl = el as HTMLInputElement | HTMLSelectElement;
        // Mode/shape/permanent switches restructure the row -> full redraw of it.
        const p = ctl.dataset.p!;
        const structural = p === "mode" || p === "shape" || p === "durPerm";
        ctl.addEventListener("input", () => applyParam(i, ctl, false));
        ctl.addEventListener("change", () => applyParam(i, ctl, structural));
      });
      row.querySelector<HTMLButtonElement>("[data-toggle]")?.addEventListener("click", (e) => {
        e.stopPropagation();
        if (openRows.has(i)) openRows.delete(i);
        else openRows.add(i);
        draw();
      });
      row.querySelector<HTMLButtonElement>("[data-del]")?.addEventListener("click", (e) => {
        e.stopPropagation();
        draft.splice(i, 1);
        remapOpen((idx) => (idx === i ? -1 : idx > i ? idx - 1 : idx));
        draw();
      });
      row.querySelectorAll<HTMLButtonElement>("[data-move]").forEach((btn) => {
        btn.addEventListener("click", (e) => {
          e.stopPropagation();
          const dir = Number(btn.dataset.move);
          const j = i + dir;
          if (j < 0 || j >= draft.length) return;
          [draft[i], draft[j]] = [draft[j], draft[i]];
          remapOpen((idx) => (idx === i ? j : idx === j ? i : idx));
          draw();
        });
      });
    });

    host.querySelector<HTMLButtonElement>("[data-save]")?.addEventListener("click", async (e) => {
      e.stopPropagation();
      const status = host.querySelector<HTMLElement>("[data-status]")!;
      const btn = e.currentTarget as HTMLButtonElement;
      btn.disabled = true;
      status.textContent = t("fx.saving");
      status.className = "fe-status";
      try {
        const res = await meta.save(parentId, draft);
        status.innerHTML = `<span class="ok">${esc(t("fx.saved", { bytes: res.bytes }))}</span>`;
        markClean(dkey); // saved; the current draft is the new baseline
        baseline = JSON.stringify(draft);
      } catch (err) {
        status.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
      } finally {
        btn.disabled = false;
      }
    });

    // Make any cross-link chips in the per-row previews (summon/area) clickable.
    wireCrosslinks(host);
  }

  // applyField handles the row-head controls (action / crit). Changing the
  // action can change what "value" means (summon ref vs damage roll), so it
  // fully redraws the row; crit only refreshes the preview.
  function applyField(i: number, el: HTMLInputElement | HTMLSelectElement, redrawPreview = false) {
    const d = draft[i];
    const f = (el as HTMLElement).dataset.f!;
    if (f === "actionId") {
      d.actionId = Number((el as HTMLSelectElement).value);
      draw(); // action change may restructure the value control
      return;
    }
    if (f === "isCritical") d.isCritical = (el as HTMLInputElement).checked;
    if (redrawPreview) refreshPreview(i);
    recomputeDirty();
  }

  // applyParam writes one guided-control edit back into the draft. `structural`
  // controls (mode/shape/permanent) change which inputs are shown, so they
  // redraw the row; scalar edits only refresh the live preview.
  function applyParam(i: number, el: HTMLInputElement | HTMLSelectElement, structural: boolean) {
    const d = draft[i];
    const p = el.dataset.p!;
    const numVal = () => Number((el as HTMLInputElement).value || 0);
    switch (p) {
      case "mode": {
        const mode = (el as HTMLSelectElement).value;
        if (mode === "none") d.params = null;
        else if (mode === "flat") d.params = [d.params?.[0] ?? 0];
        else if (mode === "dice") {
          const cur = d.params ?? [];
          d.params = [cur[0] ?? 1, cur[1] ?? 6, cur[2] ?? 0];
        }
        break;
      }
      case "ref":
        d.params = [numVal()];
        break;
      case "flat":
        d.params = [numVal()];
        break;
      case "dice":
      case "faces":
      case "mod": {
        const cur = d.params ?? [0, 0, 0];
        const arr = [cur[0] ?? 0, cur[1] ?? 0, cur[2] ?? 0];
        arr[p === "dice" ? 0 : p === "faces" ? 1 : 2] = numVal();
        d.params = arr;
        break;
      }
      case "durTurns":
        d.duration = numVal() > 0 ? [numVal()] : null;
        break;
      case "durPerm":
        d.duration = (el as HTMLInputElement).checked ? [PERMANENT] : null;
        break;
      case "shape":
        d.areaShape = numVal();
        // Reset size to a sensible default when moving to/from a sized shape.
        if (areaShapeName(d.areaShape) === "point" || areaShapeName(d.areaShape) === "none") {
          d.areaSize = null;
        } else if (!d.areaSize || !d.areaSize.length) {
          d.areaSize = [1];
        }
        break;
      case "size":
        d.areaSize = numVal() > 0 ? [numVal()] : null;
        break;
      case "target": {
        const bit = Number(el.dataset.bit);
        let cur = d.targets && d.targets.length ? d.targets[0] : 0;
        if ((el as HTMLInputElement).checked) cur |= bit;
        else cur &= ~bit;
        d.targets = cur ? [cur] : null;
        break;
      }
    }
    if (structural) draw();
    else refreshPreview(i);
    recomputeDirty();
  }

  // refreshPreview re-renders only the live human decode for row i so typing in
  // a field never steals focus.
  function refreshPreview(i: number) {
    const row = host.querySelector<HTMLElement>(`.fe-row[data-i="${i}"] .fe-preview`);
    if (row) {
      row.innerHTML = decodeEffectHTML(dtoToEffectDef(draft[i]));
      wireCrosslinks(row);
    }
  }

  draw();
}
