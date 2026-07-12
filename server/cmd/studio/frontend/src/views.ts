// Phase 1 data views: each takes the main container and renders a loading
// state, fetches from the Go backend, then mounts a configured table.

import {
  getSpells,
  getCoachCards,
  getFighterCards,
  getSummonings,
  getStaticEffects,
  getEvents,
  saveSpells,
  type EffectDef,
  type Spell,
  type SpellEdit,
} from "./backend";
import { mountTable, fmtList, boolBadge, type Column, type Facet } from "./table";
import { loadNames, label, iconCell, wireIconCells, nameOf } from "./names";
import {
  loadLore,
  decodeEffectsHTML,
  effectsSummary,
  decodeEffectText,
  gameIcon,
} from "./effectlore";
import { simulatorHTML, wireSimulator } from "./simulator";
import { wireCrosslinks } from "./crosslink";

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

function pageHead(title: string, sub: string): string {
  return `<div class="page-head"><h1>${esc(title)}</h1><span class="sub">${esc(sub)}</span></div>`;
}

// Renders an effects list in human, game-tooltip form (shared by
// spells/cards/events/static). Falls back gracefully when the lore table isn't
// loaded (decodeEffectsHTML then shows raw action ids).
function effectsDetail(effects: EffectDef[] | null): string {
  if (!effects || effects.length === 0) {
    return `<div class="detail-empty">No attached effects.</div>`;
  }
  return `
    <div class="detail-block">
      <div class="detail-title">Effects (${effects.length})</div>
      ${decodeEffectsHTML(effects)}
    </div>`;
}

async function withLoad<T>(
  container: HTMLElement,
  title: string,
  sub: string,
  fetcher: () => Promise<T[]>,
  build: (c: HTMLElement, rows: T[]) => void
) {
  container.innerHTML = `${pageHead(title, sub)}<div class="loading">Loading\u2026</div>`;
  try {
    // Ensure name maps + effect semantics are ready so tables render
    // "Name (id)" and effects decode to human sentences.
    await Promise.all([loadNames(), loadLore()]);
    const rows = await fetcher();
    container.innerHTML = pageHead(title, `${rows.length} records \u00B7 ${sub}`);
    const host = document.createElement("div");
    host.className = "table-host";
    container.appendChild(host);
    build(host, rows);
  } catch (err) {
    container.innerHTML =
      pageHead(title, sub) +
      `<div class="placeholder"><div class="big">\u26A0</div><div>Could not load ${esc(
        title
      )}.</div><div style="margin-top:6px;font-size:12.5px" class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
  }
}

export function viewSpells(c: HTMLElement) {
  withLoad(c, "Spells", "spells.dat", getSpells, (host, rows) => {
    const cols: Column<(typeof rows)[number]>[] = [
      { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("spell", r.ID, 44), width: "56px", align: "center" },
      {
        key: "name",
        label: "Name",
        value: (r) => nameOf("spells", r.ID) || String(r.ID),
        render: (r) => `<span class="row-name">${esc(nameOf("spells", r.ID) || "\u2014")}</span>`,
      },
      { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "70px" },
      { key: "AP", label: "AP", value: (r) => r.ActionPointsCost, align: "right", width: "56px" },
      {
        key: "range",
        label: "Range",
        value: (r) => r.RangeMin * 100 + r.RangeMax,
        render: (r) => `${r.RangeMin}\u2013${r.RangeMax}`,
        align: "center",
        width: "84px",
      },
      { key: "los", label: "LOS", value: (r) => (r.CastTestLineOfSight ? 1 : 0), render: (r) => boolBadge(r.CastTestLineOfSight), align: "center" },
      { key: "line", label: "Line", value: (r) => (r.CastOnlyLine ? 1 : 0), render: (r) => boolBadge(r.CastOnlyLine), align: "center" },
      { key: "free", label: "Free cell", value: (r) => (r.NeedFreeCell ? 1 : 0), render: (r) => boolBadge(r.NeedFreeCell), align: "center" },
      {
        key: "breed",
        label: "Breed",
        value: (r) => nameOf("breeds", r.BreedID) || String(r.BreedID),
        render: (r) => (r.BreedID > 0 ? esc(label("breeds", r.BreedID)) : "\u2014"),
      },
      {
        key: "fx",
        label: "Effect",
        value: (r) => (r.Effects?.length ?? 0),
        render: (r) => effectsSummary(r.Effects),
      },
    ];
    const facets: Facet<(typeof rows)[number]>[] = [
      {
        key: "breed",
        label: "Breed",
        kind: "select",
        value: (r) => (r.BreedID > 0 ? label("breeds", r.BreedID) : "\u2014"),
      },
      { key: "ap", label: "AP", kind: "range", value: (r) => r.ActionPointsCost },
      { key: "los", label: "LOS", kind: "toggle", value: (r) => r.CastTestLineOfSight },
      { key: "line", label: "Line only", kind: "toggle", value: (r) => r.CastOnlyLine },
      { key: "free", label: "Free cell", kind: "toggle", value: (r) => r.NeedFreeCell },
    ];
    mountTable(host, {
      columns: cols,
      rows,
      facets,
      exportName: "spells",
      searchText: (r) =>
        `${r.ID} ${nameOf("spells", r.ID)} ${r.Criterion} ${nameOf("breeds", r.BreedID)} ${r.ScriptID} ${(r.Effects ?? [])
          .map((e) => decodeEffectText(e))
          .join(" ")}`,
      detail: (r) => spellEditor(r),
      onDraw: (c) => {
        wireIconCells(c);
        wireSimulator(c);
        wireCrosslinks(c);
      },
    });
    // Editable spell form wiring (delegated, since the table re-renders).
    host.addEventListener("click", (ev) => {
      const t = ev.target as HTMLElement;
      if (t.dataset.saveSpell != null) {
        ev.stopPropagation();
        onSaveSpell(host, Number(t.dataset.saveSpell));
      }
    });
    // Prevent row-collapse when interacting with form fields.
    host.addEventListener("mousedown", (ev) => {
      const t = ev.target as HTMLElement;
      if (t.closest(".spell-edit")) ev.stopPropagation();
    });
    host.addEventListener("click", (ev) => {
      const t = ev.target as HTMLElement;
      if (t.closest(".spell-edit") && t.dataset.saveSpell == null) ev.stopPropagation();
    });
  });
}

// entityHero renders a generic game-card banner (icon + name + human summary +
// stat chips) for any effect-bearing record (cards/events/static effects).
function entityHero(opts: {
  kind: string;
  id: number;
  name: string;
  badge?: string;
  effects: EffectDef[] | null;
  stats: Array<[string, string, string]>;
}): string {
  const summary =
    opts.effects && opts.effects.length
      ? opts.effects.map((e) => decodeEffectText(e)).join(" \u00B7 ")
      : "No attached effects.";
  const statHtml = opts.stats
    .map(
      ([ico, l, v]) =>
        `<div class="hero-stat"><span class="hs-ico">${ico}</span><div><b>${esc(v)}</b><span>${esc(l)}</span></div></div>`
    )
    .join("");
  return `
    <div class="entity-hero">
      <div class="hero-icon">${iconCell(opts.kind, opts.id, 72)}</div>
      <div class="hero-main">
        <div class="hero-title">${esc(opts.name)} ${
    opts.badge ? `<span class="hero-breed">${esc(opts.badge)}</span>` : ""
  }</div>
        <div class="hero-summary">${esc(summary)}</div>
        <div class="hero-stats">${statHtml}</div>
      </div>
    </div>`;
}

// spellHero renders a game-card-style banner: big icon, name, a one-line
// human summary of what the spell does, and quick-read stat chips.
function spellHero(s: Spell): string {
  const name = nameOf("spells", s.ID) || `Spell ${s.ID}`;
  const breed = s.BreedID > 0 ? label("breeds", s.BreedID) : "";
  const summary =
    s.Effects && s.Effects.length
      ? s.Effects.map((e) => decodeEffectText(e)).join(" \u00B7 ")
      : "No attached effects.";
  const stat = (ico: string, label: string, val: string) =>
    `<div class="hero-stat"><span class="hs-ico">${ico}</span><div><b>${esc(val)}</b><span>${esc(label)}</span></div></div>`;
  const apIco = gameIcon("ap", 18) || "\u25C8";
  const tags: string[] = [];
  if (s.CastTestLineOfSight) tags.push(`<span class="hero-tag">line of sight</span>`);
  if (s.CastOnlyLine) tags.push(`<span class="hero-tag">line only</span>`);
  if (s.NeedFreeCell) tags.push(`<span class="hero-tag">free cell</span>`);
  return `
    <div class="entity-hero">
      <div class="hero-icon">${iconCell("spell", s.ID, 72)}</div>
      <div class="hero-main">
        <div class="hero-title">${esc(name)} ${breed ? `<span class="hero-breed">${esc(breed)}</span>` : ""}</div>
        <div class="hero-summary">${esc(summary)}</div>
        <div class="hero-stats">
          ${stat(apIco, "AP cost", String(s.ActionPointsCost))}
          ${stat("\u2316", "Range", `${s.RangeMin}\u2013${s.RangeMax}`)}
          ${stat("\u2726", "Effects", String(s.Effects?.length ?? 0))}
          ${s.Price ? stat("\u25C9", "Price", s.Price.toLocaleString()) : ""}
        </div>
        ${tags.length ? `<div class="hero-tags">${tags.join("")}</div>` : ""}
      </div>
    </div>`;
}

// spellEditor renders the hero banner + editable form + human effects list.
function spellEditor(s: Spell): string {
  const num = (id: string, label: string, val: number, min = 0, max = 255) =>
    `<label><span>${esc(label)}</span><input class="edit-in" type="number" min="${min}" max="${max}" data-f="${id}" value="${val}" /></label>`;
  const chk = (id: string, label: string, val: boolean) =>
    `<label class="edit-chk"><input type="checkbox" data-f="${id}" ${val ? "checked" : ""}/> ${esc(label)}</label>`;
  return `
    ${spellHero(s)}
    <div class="spell-edit" data-spell="${s.ID}">
      <div class="edit-title">Edit spell #${s.ID}</div>
      <div class="edit-grid">
        ${num("actionPointsCost", "AP cost", s.ActionPointsCost)}
        ${num("rangeMin", "Range min", s.RangeMin)}
        ${num("rangeMax", "Range max", s.RangeMax)}
        ${num("castFrequencyMaxPerTurn", "Freq/turn", s.CastFrequencyMaxPerTurn)}
        ${num("castFrequencyMaxPerPlayer", "Freq/target", s.CastFrequencyMaxPerPlayer)}
        ${num("castFrequencyMinInterval", "Min interval", s.CastFrequencyMinInterval)}
        ${num("price", "Price", s.Price, -2147483648, 2147483647)}
      </div>
      <div class="edit-checks">
        ${chk("castTestLineOfSight", "Test LOS", s.CastTestLineOfSight)}
        ${chk("castOnlyLine", "Only line", s.CastOnlyLine)}
        ${chk("needFreeCell", "Needs free cell", s.NeedFreeCell)}
      </div>
      <label class="edit-full"><span>Criterion</span><input class="edit-in mono" type="text" data-f="criterion" value="${esc(
        s.Criterion
      )}" /></label>
      <div class="edit-actions">
        <button class="primary" data-save-spell="${s.ID}">Save to spells.dat</button>
        <span class="edit-status" data-status="${s.ID}"></span>
      </div>
    </div>
    ${simulatorHTML(s.Effects)}
    ${effectsDetail(s.Effects)}`;
}

function onSaveSpell(host: HTMLElement, id: number) {
  const form = host.querySelector<HTMLElement>(`.spell-edit[data-spell="${id}"]`);
  if (!form) return;
  const statusEl = host.querySelector<HTMLElement>(`[data-status="${id}"]`);
  const getNum = (f: string) =>
    Number((form.querySelector(`[data-f="${f}"]`) as HTMLInputElement).value);
  const getChk = (f: string) =>
    (form.querySelector(`[data-f="${f}"]`) as HTMLInputElement).checked;
  const getStr = (f: string) =>
    (form.querySelector(`[data-f="${f}"]`) as HTMLInputElement).value;

  const edit: SpellEdit = {
    id,
    actionPointsCost: getNum("actionPointsCost"),
    rangeMin: getNum("rangeMin"),
    rangeMax: getNum("rangeMax"),
    castFrequencyMaxPerTurn: getNum("castFrequencyMaxPerTurn"),
    castFrequencyMaxPerPlayer: getNum("castFrequencyMaxPerPlayer"),
    castFrequencyMinInterval: getNum("castFrequencyMinInterval"),
    price: getNum("price"),
    castTestLineOfSight: getChk("castTestLineOfSight"),
    castOnlyLine: getChk("castOnlyLine"),
    needFreeCell: getChk("needFreeCell"),
    criterion: getStr("criterion"),
  };

  if (statusEl) statusEl.textContent = "Saving\u2026";
  saveSpells([edit])
    .then((res) => {
      if (statusEl)
        statusEl.innerHTML = `<span class="ok">Saved ${res.bytes} B \u00B7 backup created</span>`;
    })
    .catch((err) => {
      if (statusEl) statusEl.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
    });
}

export function viewCards(c: HTMLElement) {
  c.innerHTML = pageHead("Cards", "cards.dat \u00B7 coach + fighter cards") + `<div class="loading">Loading\u2026</div>`;
  Promise.all([getCoachCards(), getFighterCards(), loadNames()])
    .then(([coach, fighter]) => {
      c.innerHTML = pageHead("Cards", `${coach.length} coach \u00B7 ${fighter.length} fighter \u00B7 cards.dat`);
      const tabs = document.createElement("div");
      tabs.className = "subtabs";
      tabs.innerHTML = `<button class="subtab active" data-t="coach">Coach cards (${coach.length})</button><button class="subtab" data-t="fighter">Fighter cards (${fighter.length})</button>`;
      c.appendChild(tabs);
      const host = document.createElement("div");
      host.className = "table-host";
      c.appendChild(host);

      const drawCoach = () =>
        mountTable(host, {
          rows: coach,
          onDraw: (h) => wireIconCells(h),
          columns: [
            { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("coachCard", r.ID, 44), width: "56px", align: "center" },
            {
              key: "name",
              label: "Name",
              value: (r) => nameOf("coachCards", r.ID) || String(r.ID),
              render: (r) => `<span class="row-name">${esc(nameOf("coachCards", r.ID) || "\u2014")}</span>`,
            },
            { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
            { key: "Type", label: "Type", value: (r) => r.Type, align: "right" },
            { key: "Value", label: "Value", value: (r) => r.Value, align: "right" },
            { key: "Set", label: "Set", value: (r) => r.Set, align: "right" },
          ],
          searchText: (r) => `${r.ID} ${nameOf("coachCards", r.ID)}`,
          exportName: "coach-cards",
          facets: [
            { key: "type", label: "Type", kind: "select", value: (r) => String(r.Type) },
            { key: "set", label: "Set", kind: "select", value: (r) => String(r.Set) },
          ],
        });
      const drawFighter = () =>
        mountTable(host, {
          rows: fighter,
          onDraw: (h) => {
            wireIconCells(h);
            wireCrosslinks(h);
          },
          columns: [
            { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("fighterCard", r.ID, 44), width: "56px", align: "center" },
            {
              key: "name",
              label: "Name",
              value: (r) => nameOf("fighterCards", r.ID) || String(r.ID),
              render: (r) => `<span class="row-name">${esc(nameOf("fighterCards", r.ID) || "\u2014")}</span>`,
            },
            { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
            { key: "Type", label: "Type", value: (r) => r.Type, render: (r) => fighterCardType(r.Type), align: "center" },
            { key: "Value", label: "Value", value: (r) => r.Value, align: "right" },
            { key: "SubType", label: "SubType", value: (r) => r.SubType, align: "right" },
            { key: "fx", label: "Effect", value: (r) => (r.Effects?.length ?? 0), render: (r) => effectsSummary(r.Effects) },
          ],
          searchText: (r) =>
            `${r.ID} ${nameOf("fighterCards", r.ID)} ${(r.Effects ?? []).map((e) => decodeEffectText(e)).join(" ")}`,
          exportName: "fighter-cards",
          facets: [
            {
              key: "type",
              label: "Type",
              kind: "select",
              value: (r) =>
                ({ 1: "Weapon", 2: "Pet", 3: "Cloak", 4: "Hat", 5: "Dofus" } as Record<number, string>)[
                  r.Type
                ] ?? String(r.Type),
            },
            { key: "hasfx", label: "Has effects", kind: "toggle", value: (r) => (r.Effects?.length ?? 0) > 0 },
          ],
          detail: (r) =>
            entityHero({
              kind: "fighterCard",
              id: r.ID,
              name: nameOf("fighterCards", r.ID) || `Card ${r.ID}`,
              badge: ({ 1: "Weapon", 2: "Pet", 3: "Cloak", 4: "Hat", 5: "Dofus" } as Record<number, string>)[r.Type],
              effects: r.Effects,
              stats: [
                ["\u2726", "Effects", String(r.Effects?.length ?? 0)],
                ["\u25C9", "Value", String(r.Value)],
              ],
            }) + effectsDetail(r.Effects),
        });

      drawCoach();
      tabs.querySelectorAll<HTMLButtonElement>(".subtab").forEach((btn) => {
        btn.addEventListener("click", () => {
          tabs.querySelectorAll(".subtab").forEach((b) => b.classList.remove("active"));
          btn.classList.add("active");
          if (btn.dataset.t === "coach") drawCoach();
          else drawFighter();
        });
      });
    })
    .catch((err) => {
      c.innerHTML =
        pageHead("Cards", "cards.dat") +
        `<div class="placeholder"><div class="big">\u26A0</div><div>Could not load cards.</div><div class="mono" style="margin-top:6px;font-size:12.5px">${esc(
          (err as Error).message
        )}</div></div>`;
    });
}

function fighterCardType(t: number): string {
  const names: Record<number, string> = { 1: "Weapon", 2: "Pet", 3: "Cloak", 4: "Hat", 5: "Dofus" };
  return names[t] ? `<span class="tb-badge">${names[t]}</span>` : String(t);
}

export function viewSummonings(c: HTMLElement) {
  withLoad(c, "Summonings", "summoning.dat", getSummonings, (host, rows) => {
    mountTable(host, {
      rows,
      searchText: (r) => `${r.ID} ${nameOf("summons", r.ID)}`,
      exportName: "summonings",
      facets: [
        { key: "hp", label: "HP", kind: "range", value: (r) => r.HP },
        { key: "ap", label: "AP", kind: "range", value: (r) => r.AP },
        { key: "mp", label: "MP", kind: "range", value: (r) => r.MP },
      ],
      columns: [
        {
          key: "name",
          label: "Name",
          value: (r) => nameOf("summons", r.ID) || String(r.ID),
          render: (r) => `<span class="row-name">${esc(nameOf("summons", r.ID) || "\u2014")}</span>`,
        },
        { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
        { key: "HP", label: "HP", value: (r) => r.HP, align: "right" },
        { key: "AP", label: "AP", value: (r) => r.AP, align: "right" },
        { key: "MP", label: "MP", value: (r) => r.MP, align: "right" },
        { key: "SpellID", label: "Spell", value: (r) => nameOf("spells", r.SpellID) || String(r.SpellID), render: (r) => esc(label("spells", r.SpellID)) },
      ],
    });
  });
}

export function viewStaticEffects(c: HTMLElement) {
  withLoad(c, "Static Effects", "staticEffects.dat", getStaticEffects, (host, rows) => {
    mountTable(host, {
      rows,
      onDraw: (h) => wireCrosslinks(h),
      searchText: (r) =>
        `${r.ID} ${r.EffectAreaType} ${r.ScriptID} ${(r.Effects ?? []).map((e) => decodeEffectText(e)).join(" ")}`,
      exportName: "static-effects",
      facets: [
        { key: "area", label: "Area type", kind: "select", value: (r) => r.EffectAreaType.trim() },
        { key: "hasfx", label: "Has effects", kind: "toggle", value: (r) => (r.Effects?.length ?? 0) > 0 },
      ],
      columns: [
        { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "70px" },
        { key: "Type", label: "Area type", value: (r) => r.EffectAreaType, render: (r) => `<span class="tb-badge ${r.EffectAreaType === "TRAP" ? "warn" : ""}">${esc(r.EffectAreaType.trim())}</span>` },
        { key: "Shape", label: "Shape", value: (r) => r.AreaShapeID, align: "right" },
        { key: "Script", label: "Script", value: (r) => r.ScriptID, align: "right" },
        { key: "Max", label: "Max exec", value: (r) => r.MaxExecutionCount, align: "right" },
        { key: "fx", label: "Effect", value: (r) => (r.Effects?.length ?? 0), render: (r) => effectsSummary(r.Effects) },
      ],
      detail: (r) => `
        <div class="detail-grid">
          <div><span>Area params</span><b class="mono">${fmtList(r.AreaParams)}</b></div>
          <div><span>App. triggers</span><b class="mono">${fmtList(r.ApplicationTriggers)}</b></div>
          <div><span>Unapp. triggers</span><b class="mono">${fmtList(r.UnapplicationTriggers)}</b></div>
          <div><span>App. targets</span><b class="mono">${fmtList(r.ApplicationTargets)}</b></div>
          <div><span>Deactivation delay</span><b class="mono">${fmtList(r.DeactivationDelay)}</b></div>
          <div><span>App. condition</span><b>${r.ApplicationCondition}</b></div>
        </div>
        ${effectsDetail(r.Effects)}`,
    });
  });
}

export function viewEvents(c: HTMLElement) {
  withLoad(c, "Events", "events.dat", getEvents, (host, rows) => {
    mountTable(host, {
      rows,
      onDraw: (h) => {
        wireIconCells(h);
        wireCrosslinks(h);
      },
      searchText: (r) =>
        `${r.ID} ${nameOf("events", r.ID)} ${(r.Effects ?? []).map((e) => decodeEffectText(e)).join(" ")}`,
      exportName: "events",
      facets: [
        { key: "auto", label: "Auto desc", kind: "toggle", value: (r) => r.UseAutoDescription },
        { key: "hasfx", label: "Has effects", kind: "toggle", value: (r) => (r.Effects?.length ?? 0) > 0 },
      ],
      columns: [
        { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("event", r.ID, 44), width: "56px", align: "center" },
        {
          key: "name",
          label: "Name",
          value: (r) => nameOf("events", r.ID) || String(r.ID),
          render: (r) => `<span class="row-name">${esc(nameOf("events", r.ID) || "\u2014")}</span>`,
        },
        { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
        { key: "Auto", label: "Auto description", value: (r) => (r.UseAutoDescription ? 1 : 0), render: (r) => boolBadge(r.UseAutoDescription), align: "center" },
        { key: "fx", label: "Effect", value: (r) => (r.Effects?.length ?? 0), render: (r) => effectsSummary(r.Effects) },
      ],
      detail: (r) =>
        entityHero({
          kind: "event",
          id: r.ID,
          name: nameOf("events", r.ID) || `Event ${r.ID}`,
          badge: r.UseAutoDescription ? "auto" : undefined,
          effects: r.Effects,
          stats: [["\u2726", "Effects", String(r.Effects?.length ?? 0)]],
        }) + effectsDetail(r.Effects),
    });
  });
}
