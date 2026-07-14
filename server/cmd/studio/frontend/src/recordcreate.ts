// recordcreate.ts -- "+ New record" creation flow. Opens a modal with a small
// scalar form for a brand-new spell / fighter card / coach card / summoning,
// pre-filling a free id suggested by the backend, then calls the matching
// Create* IPC (append + byte-exact-gated + backup). On success it fires a
// callback so the host view can reload. Effects are attached afterwards via the
// per-record effect editor (new records start with none).

import {
  createSpell,
  createFighterCard,
  createCoachCard,
  createSummoning,
  suggestSpellID,
  suggestCardIDs,
  suggestSummoningID,
  type NewRecordResult,
} from "./backend";

export type CreateKind = "spell" | "fighterCard" | "coachCard" | "summoning";

interface FieldSpec {
  key: string;
  label: string;
  type: "number" | "text" | "bool";
  value: number | string | boolean;
  hint?: string;
}

interface CreateSpec {
  title: string;
  suggestId: () => Promise<number>;
  fields: (id: number) => FieldSpec[];
  submit: (v: Record<string, number | string | boolean>) => Promise<NewRecordResult>;
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

const n = (v: Record<string, number | string | boolean>, k: string) => Number(v[k]);
const b = (v: Record<string, number | string | boolean>, k: string) => Boolean(v[k]);
const s = (v: Record<string, number | string | boolean>, k: string) => String(v[k] ?? "");

// specFor builds the modal spec for a given record kind.
function specFor(kind: CreateKind): CreateSpec {
  switch (kind) {
    case "spell":
      return {
        title: "New spell",
        suggestId: suggestSpellID,
        fields: (id) => [
          { key: "id", label: "Spell ID", type: "number", value: id, hint: "must be unique" },
          { key: "actionPointsCost", label: "AP cost", type: "number", value: 3 },
          { key: "rangeMin", label: "Range min", type: "number", value: 1 },
          { key: "rangeMax", label: "Range max", type: "number", value: 6 },
          { key: "castFrequencyMaxPerTurn", label: "Freq / turn", type: "number", value: 0, hint: "0 = unlimited" },
          { key: "castFrequencyMaxPerPlayer", label: "Freq / target", type: "number", value: 0 },
          { key: "castFrequencyMinInterval", label: "Min interval", type: "number", value: 0 },
          { key: "price", label: "Price", type: "number", value: 0 },
          { key: "aiTargetId", label: "AI target id", type: "number", value: 0 },
          { key: "scriptId", label: "Script ID", type: "number", value: 0 },
          { key: "breedId", label: "Breed ID", type: "number", value: 0 },
          { key: "criterion", label: "Criterion", type: "text", value: "" },
          { key: "castTestLineOfSight", label: "Test line of sight", type: "bool", value: true },
          { key: "castOnlyLine", label: "Only in a line", type: "bool", value: false },
          { key: "needFreeCell", label: "Needs free cell", type: "bool", value: false },
          { key: "useAutoDescription", label: "Auto description", type: "bool", value: true },
        ],
        submit: (v) =>
          createSpell({
            id: n(v, "id"),
            actionPointsCost: n(v, "actionPointsCost"),
            castFrequencyMaxPerPlayer: n(v, "castFrequencyMaxPerPlayer"),
            castFrequencyMaxPerTurn: n(v, "castFrequencyMaxPerTurn"),
            castFrequencyMinInterval: n(v, "castFrequencyMinInterval"),
            castTestLineOfSight: b(v, "castTestLineOfSight"),
            castOnlyLine: b(v, "castOnlyLine"),
            rangeMin: n(v, "rangeMin"),
            rangeMax: n(v, "rangeMax"),
            price: n(v, "price"),
            aiTargetId: n(v, "aiTargetId"),
            needFreeCell: b(v, "needFreeCell"),
            scriptId: n(v, "scriptId"),
            breedId: n(v, "breedId"),
            criterion: s(v, "criterion"),
            useAutoDescription: b(v, "useAutoDescription"),
          }),
      };
    case "fighterCard":
      return {
        title: "New fighter card",
        suggestId: async () => (await suggestCardIDs()).fighter ?? 1,
        fields: (id) => [
          { key: "id", label: "Card ID", type: "number", value: id, hint: "must be unique" },
          { key: "type", label: "Type", type: "number", value: 1, hint: "1 weapon / 2 pet / 3 cloak / 4 hat / 5 dofus" },
          { key: "value", label: "Value", type: "number", value: 0 },
          { key: "scriptId", label: "Script ID", type: "number", value: 0 },
          { key: "subType", label: "SubType", type: "number", value: 0 },
        ],
        submit: (v) =>
          createFighterCard({
            id: n(v, "id"),
            type: n(v, "type"),
            value: n(v, "value"),
            scriptId: n(v, "scriptId"),
            subType: n(v, "subType"),
          }),
      };
    case "coachCard":
      return {
        title: "New coach card",
        suggestId: async () => (await suggestCardIDs()).coach ?? 1,
        fields: (id) => [
          { key: "id", label: "Card ID", type: "number", value: id, hint: "must be unique" },
          { key: "type", label: "Type", type: "number", value: 1 },
          { key: "value", label: "Value", type: "number", value: 0 },
          { key: "set", label: "Set", type: "number", value: 0 },
        ],
        submit: (v) =>
          createCoachCard({
            id: n(v, "id"),
            type: n(v, "type"),
            value: n(v, "value"),
            set: n(v, "set"),
          }),
      };
    case "summoning":
      return {
        title: "New summoning",
        suggestId: suggestSummoningID,
        fields: (id) => [
          { key: "id", label: "Summoning ID", type: "number", value: id, hint: "must be unique" },
          { key: "hp", label: "HP", type: "number", value: 50 },
          { key: "ap", label: "AP", type: "number", value: 6 },
          { key: "mp", label: "MP", type: "number", value: 3 },
          { key: "gfx", label: "Gfx", type: "number", value: 0 },
          { key: "spellId", label: "Spell ID", type: "number", value: 0 },
        ],
        submit: (v) =>
          createSummoning({
            id: n(v, "id"),
            hp: n(v, "hp"),
            ap: n(v, "ap"),
            mp: n(v, "mp"),
            gfx: n(v, "gfx"),
            spellId: n(v, "spellId"),
          }),
      };
  }
}

// openCreateModal shows the creation dialog for kind. onCreated is called with
// the new record's id after a successful save (host view should reload).
export async function openCreateModal(kind: CreateKind, onCreated: (id: number) => void) {
  const spec = specFor(kind);
  let id = 1;
  try {
    id = await spec.suggestId();
  } catch {
    /* keep default id=1; the backend still validates uniqueness on submit */
  }
  const fields = spec.fields(id);

  const overlay = document.createElement("div");
  overlay.className = "rc-overlay";
  const bools = fields.filter((f) => f.type === "bool");
  const rest = fields.filter((f) => f.type !== "bool");
  const fieldHTML = (f: FieldSpec) => {
    if (f.type === "bool") {
      return `<label class="rc-chk"><input type="checkbox" data-k="${esc(f.key)}" ${
        f.value ? "checked" : ""
      }/> ${esc(f.label)}</label>`;
    }
    const t = f.type === "number" ? "number" : "text";
    return `<label class="rc-field">
        <span>${esc(f.label)}</span>
        <input class="rc-in ${f.type === "text" ? "mono" : ""}" type="${t}" data-k="${esc(
      f.key
    )}" value="${esc(f.value)}" />
        ${f.hint ? `<small>${esc(f.hint)}</small>` : ""}
      </label>`;
  };
  overlay.innerHTML = `
    <div class="rc-modal" role="dialog" aria-modal="true">
      <div class="rc-head">
        <b>${esc(spec.title)}</b>
        <button class="rc-close" data-close title="Close">\u00D7</button>
      </div>
      <div class="rc-body">
        <div class="rc-grid">${rest.map(fieldHTML).join("")}</div>
        ${bools.length ? `<div class="rc-checks">${bools.map(fieldHTML).join("")}</div>` : ""}
        <p class="rc-note">A new record is appended with no effects. Attach effects afterwards from its row's effect editor.</p>
      </div>
      <div class="rc-actions">
        <span class="rc-status" data-status></span>
        <button data-cancel>Cancel</button>
        <button class="primary" data-create>Create</button>
      </div>
    </div>`;
  document.body.appendChild(overlay);

  const close = () => overlay.remove();
  overlay.addEventListener("mousedown", (e) => {
    if (e.target === overlay) close();
  });
  overlay.querySelector<HTMLButtonElement>("[data-close]")?.addEventListener("click", close);
  overlay.querySelector<HTMLButtonElement>("[data-cancel]")?.addEventListener("click", close);
  const escHandler = (e: KeyboardEvent) => {
    if (e.key === "Escape") {
      close();
      document.removeEventListener("keydown", escHandler);
    }
  };
  document.addEventListener("keydown", escHandler);

  overlay.querySelector<HTMLButtonElement>("[data-create]")?.addEventListener("click", async () => {
    const status = overlay.querySelector<HTMLElement>("[data-status]")!;
    const btn = overlay.querySelector<HTMLButtonElement>("[data-create]")!;
    const values: Record<string, number | string | boolean> = {};
    overlay.querySelectorAll<HTMLInputElement>("[data-k]").forEach((inp) => {
      const f = fields.find((x) => x.key === inp.dataset.k);
      if (!f) return;
      if (f.type === "bool") values[f.key] = inp.checked;
      else if (f.type === "number") values[f.key] = Number(inp.value);
      else values[f.key] = inp.value;
    });
    btn.disabled = true;
    status.textContent = "Creating\u2026";
    status.className = "rc-status";
    try {
      const res = await spec.submit(values);
      status.textContent = `Created #${res.newId} \u00B7 ${res.bytes} B written`;
      status.className = "rc-status ok";
      document.removeEventListener("keydown", escHandler);
      setTimeout(() => {
        close();
        onCreated(res.newId);
      }, 550);
    } catch (err) {
      status.textContent = (err as Error).message;
      status.className = "rc-status err";
      btn.disabled = false;
    }
  });
}

// newRecordButton returns markup for a "+ New" pill button that, when wired via
// wireNewRecordButton, opens the create modal for kind.
export function newRecordButton(kind: CreateKind, label: string): string {
  return `<button class="rc-new" data-rc-new="${kind}">+ ${esc(label)}</button>`;
}

// wireNewRecordButton attaches the click handler to any rc-new button in root.
export function wireNewRecordButton(root: HTMLElement, onCreated: (id: number) => void) {
  root.querySelectorAll<HTMLButtonElement>("[data-rc-new]:not([data-rc-wired])").forEach((btn) => {
    btn.dataset.rcWired = "1";
    btn.addEventListener("click", (e) => {
      e.stopPropagation();
      openCreateModal(btn.dataset.rcNew as CreateKind, onCreated);
    });
  });
}
