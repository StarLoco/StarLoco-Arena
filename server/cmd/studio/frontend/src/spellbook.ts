// spellbook.ts -- a beautiful class-organized view of every spell, grouped by
// breed with the client's own class background art. Clicking a spell jumps to
// the Spells view filtered on it. This turns a flat 138-row table into a
// browsable, game-like grimoire.

import { getSpells, type Spell } from "./backend";
import { loadNames, nameOf, label, iconCell, wireIconCells } from "./names";
import { loadLore, decodeEffectText, gameIcon } from "./effectlore";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

export function viewSpellbook(container: HTMLElement) {
  container.innerHTML = `
    <div class="page-head"><h1>Spellbook</h1><span class="sub">spells by class</span></div>
    <div class="loading">Loading\u2026</div>`;

  Promise.all([loadNames(), loadLore(), getSpells()])
    .then(([, , spells]) => render(container, spells))
    .catch((err) => {
      container.innerHTML = `<div class="page-head"><h1>Spellbook</h1></div>
        <div class="placeholder"><div class="big">\u26A0</div><div>Could not load spells.</div>
        <div class="mono" style="margin-top:6px;font-size:12px">${esc((err as Error).message)}</div></div>`;
    });
}

function render(container: HTMLElement, spells: Spell[]) {
  // Group by breed; breed 0 == generic/no-class.
  const byBreed = new Map<number, Spell[]>();
  for (const s of spells) {
    const b = s.BreedID || 0;
    if (!byBreed.has(b)) byBreed.set(b, []);
    byBreed.get(b)!.push(s);
  }
  const breeds = [...byBreed.keys()].sort((a, b) => {
    if (a === 0) return 1;
    if (b === 0) return -1;
    return a - b;
  });

  const sections = breeds
    .map((breedId) => {
      const list = byBreed.get(breedId)!.sort((a, b) => a.ActionPointsCost - b.ActionPointsCost || a.ID - b.ID);
      const breedName = breedId ? label("breeds", breedId) : "Generic / no class";
      const bg = breedId ? iconCell("breedBg", breedId, 340) : "";
      const cards = list
        .map((s) => {
          const name = nameOf("spells", s.ID) || `Spell ${s.ID}`;
          const summary = s.Effects && s.Effects.length ? decodeEffectText(s.Effects[0]) : "";
          return `
          <button class="sb-spell" data-spell="${s.ID}" title="${esc(name)}">
            <div class="sb-spell-ico">${iconCell("spell", s.ID, 48)}</div>
            <div class="sb-spell-info">
              <div class="sb-spell-name">${esc(name)}</div>
              <div class="sb-spell-sum">${esc(summary)}</div>
            </div>
            <div class="sb-spell-ap" title="${s.ActionPointsCost} AP">
              <b>${s.ActionPointsCost}</b>${gameIcon("ap", 15) || "<span>AP</span>"}
            </div>
          </button>`;
        })
        .join("");
      return `
        <section class="sb-section" data-breed="${breedId}">
          <div class="sb-band">
            <div class="sb-band-bg">${bg}</div>
            <div class="sb-band-overlay"></div>
            <div class="sb-band-title">
              <b>${esc(breedName)}</b>
              <span>${list.length} spell${list.length === 1 ? "" : "s"}</span>
            </div>
          </div>
          <div class="sb-grid">${cards}</div>
        </section>`;
    })
    .join("");

  container.innerHTML = `
    <div class="page-head"><h1>Spellbook</h1><span class="sub">${spells.length} spells \u00B7 ${breeds.length} classes</span></div>
    <div class="sb-wrap">${sections}</div>`;

  wireIconCells(container);

  container.querySelectorAll<HTMLElement>(".sb-spell").forEach((el) => {
    el.addEventListener("click", () => {
      const name = nameOf("spells", Number(el.dataset.spell)) || el.dataset.spell!;
      window.dispatchEvent(
        new CustomEvent("studio:navigate", { detail: { view: "spells", query: name } })
      );
    });
  });
}
