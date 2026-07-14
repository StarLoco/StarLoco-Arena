// scriptsview.ts -- a dedicated Lua Scripts browser. Lists every script shipped
// in the client data.jar (scripts/<id>.lua), shows which game records reference
// each one (spells, fighter cards, static-effect areas), and opens the same
// in-place Lua editor used in the drawers. This makes spell scripting a
// first-class workflow instead of something buried per-record.

import {
  listScriptIDs,
  getSpells,
  getFighterCards,
  getStaticEffects,
} from "./backend";
import { loadNames, nameOf } from "./names";
import { mountScriptEditor } from "./scripteditor";
import { link, wireCrosslinks } from "./crosslink";
import { t } from "./i18n";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// A reference from a game record to a script id.
interface ScriptRef {
  view: string; // target nav view for the crosslink
  query: string; // search query to focus the record
  label: string; // human chip label
}

// buildRefIndex maps scriptId -> the records that use it, across spells,
// fighter cards and static-effect areas.
function buildRefIndex(
  spells: Array<{ ID: number; ScriptID: number }>,
  cards: Array<{ ID: number; ScriptID: number }>,
  statics: Array<{ ID: number; ScriptID: number }>
): Map<number, ScriptRef[]> {
  const idx = new Map<number, ScriptRef[]>();
  const add = (scriptId: number, ref: ScriptRef) => {
    if (scriptId <= 0) return;
    const list = idx.get(scriptId) ?? [];
    list.push(ref);
    idx.set(scriptId, list);
  };
  for (const s of spells) {
    const nm = nameOf("spells", s.ID) || `Spell ${s.ID}`;
    add(s.ScriptID, { view: "spells", query: nm, label: `\u2726 ${nm}` });
  }
  for (const c of cards) {
    const nm = nameOf("fighterCards", c.ID) || `Card ${c.ID}`;
    add(c.ScriptID, { view: "cards", query: nm, label: `\u2617 ${nm}` });
  }
  for (const a of statics) {
    add(a.ScriptID, { view: "staticEffects", query: String(a.ID), label: `\u2622 Static #${a.ID}` });
  }
  return idx;
}

export function viewScripts(c: HTMLElement) {
  c.innerHTML = `<div class="page-head"><h1>${esc(t("nav.scripts"))}</h1><span class="sub">${esc(t("view.scripts.sub"))}</span></div><div class="loading">${esc(t("view.scripts.loading"))}</div>`;

  Promise.all([
    listScriptIDs(),
    getSpells().catch(() => []),
    getFighterCards().catch(() => []),
    getStaticEffects().catch(() => []),
    loadNames(),
  ])
    .then(([ids, spells, cards, statics]) => {
      const refIndex = buildRefIndex(spells, cards, statics);
      renderList(c, ids, refIndex);
    })
    .catch((err) => {
      c.innerHTML =
        `<div class="page-head"><h1>${esc(t("nav.scripts"))}</h1><span class="sub">data.jar</span></div>` +
        `<div class="placeholder"><div class="big">\u26A0</div><div>${esc(t("common.couldNotLoad", { what: t("nav.scripts").toLowerCase() }))}</div><div class="mono" style="margin-top:6px;font-size:12.5px">${esc(
          (err as Error).message
        )}</div></div>`;
    });
}

function renderList(
  c: HTMLElement,
  ids: number[],
  refIndex: Map<number, ScriptRef[]>
) {
  const used = ids.filter((id) => (refIndex.get(id)?.length ?? 0) > 0).length;
  c.innerHTML = `
    <div class="page-head">
      <h1>${esc(t("nav.scripts"))}</h1>
      <span class="sub">${esc(t("view.scripts.count", { n: ids.length, used }))}</span>
    </div>
    <div class="sv-wrap">
      <div class="sv-list">
        <input class="sv-search" type="search" placeholder="Search by id or referencing record\u2026" />
        <div class="sv-items"></div>
      </div>
      <div class="sv-detail"><div class="detail-empty">Select a script to view and edit its Lua source.</div></div>
    </div>`;

  const itemsBox = c.querySelector<HTMLElement>(".sv-items")!;
  const detail = c.querySelector<HTMLElement>(".sv-detail")!;
  const search = c.querySelector<HTMLInputElement>(".sv-search")!;

  const refText = (id: number) =>
    (refIndex.get(id) ?? []).map((r) => r.query).join(" ").toLowerCase();

  let selected = -1;
  const drawItems = () => {
    const q = search.value.trim().toLowerCase();
    const filtered = ids.filter(
      (id) => !q || String(id).includes(q) || refText(id).includes(q)
    );
    if (filtered.length === 0) {
      itemsBox.innerHTML = `<div class="detail-empty">No scripts match.</div>`;
      return;
    }
    itemsBox.innerHTML = filtered
      .map((id) => {
        const refs = refIndex.get(id) ?? [];
        const sub =
          refs.length > 0
            ? esc(refs[0].label.replace(/^\S+\s/, "")) + (refs.length > 1 ? ` +${refs.length - 1}` : "")
            : `<span class="sv-orphan">unreferenced</span>`;
        return `<button class="sv-item ${id === selected ? "active" : ""}" data-id="${id}">
            <span class="sv-item-id mono">${id}</span>
            <span class="sv-item-sub">${sub}</span>
          </button>`;
      })
      .join("");
    itemsBox.querySelectorAll<HTMLButtonElement>(".sv-item").forEach((btn) => {
      btn.addEventListener("click", () => select(Number(btn.dataset.id)));
    });
  };

  const select = (id: number) => {
    selected = id;
    drawItems();
    const refs = refIndex.get(id) ?? [];
    const chips =
      refs.length > 0
        ? refs.map((r) => link(r.view, r.query, r.label)).join(" ")
        : `<span class="detail-empty">No record references this script.</span>`;
    detail.innerHTML = `
      <div class="sv-refs">
        <div class="detail-title">Referenced by</div>
        <div class="sv-chips">${chips}</div>
      </div>
      <div class="sc-host"></div>`;
    wireCrosslinks(detail);
    const host = detail.querySelector<HTMLElement>(".sc-host")!;
    mountScriptEditor(host, id);
  };

  search.addEventListener("input", drawItems);
  drawItems();
}
