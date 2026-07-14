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
  saveSummonings,
  saveCoachCards,
  saveFighterCards,
  saveStaticEffects,
  saveEvents,
  type EffectDef,
  type Spell,
  type SpellEdit,
} from "./backend";
import { editFormHTML, wireEditForm, type EditFormSpec } from "./editform";
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
import { mountEffectEditor, type EffectParentKind } from "./effecteditor";
import { newRecordButton, wireNewRecordButton, type CreateKind } from "./recordcreate";
import { jsonButton, wireJsonButton } from "./jsonio";
import { mountScriptEditor, loadScriptIndex, hasScript } from "./scripteditor";
import { mountAnimThumb } from "./animthumb";
import { spellAreaBlock } from "./spellarea";
import { t } from "./i18n";
import { setDirty, markClean } from "./dirty";

// mountEffectEditors finds every fe-mount placeholder inside root and mounts a
// full effect editor for its record (by id), for cards/events drawers. Spells
// use their own mount (they carry a data-fe-spell attribute instead).
function mountEffectEditors(
  root: HTMLElement,
  rows: Array<{ ID: number; Effects: EffectDef[] | null }>,
  kind: EffectParentKind
) {
  root
    .querySelectorAll<HTMLElement>(`.fe-mount[data-fe-kind="${kind}"]:not([data-fe-done])`)
    .forEach((mount) => {
      mount.dataset.feDone = "1";
      const id = Number(mount.dataset.feId);
      const rec = rows.find((r) => r.ID === id);
      if (rec) mountEffectEditor(mount, id, rec.Effects, kind);
    });
}

// eventForm declares the editable scalar fields for an event (auto-description
// toggle; effects are edited separately and preserved on save).
function eventForm(r: { ID: number; UseAutoDescription: boolean }): EditFormSpec {
  return {
    id: r.ID,
    title: t("form.event", { id: r.ID }),
    fields: [
      {
        key: "useAutoDescription",
        label: t("field.useAutoDesc"),
        type: "bool",
        value: r.UseAutoDescription,
      },
    ],
    save: (v) =>
      saveEvents([{ id: r.ID, useAutoDescription: v.useAutoDescription as boolean }]),
  };
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

function pageHead(title: string, sub: string, actions = ""): string {
  return `<div class="page-head"><h1>${esc(title)}</h1><span class="sub">${esc(
    sub
  )}</span>${actions ? `<div class="page-actions">${actions}</div>` : ""}</div>`;
}

// installNewButton injects a "+ New" pill into the page head of container and
// wires it to open the create modal for kind; on success it re-runs reload so
// the fresh record shows up immediately.
function installNewButton(
  container: HTMLElement,
  kind: CreateKind,
  label: string,
  reload: () => void
) {
  const head = container.querySelector<HTMLElement>(".page-head");
  if (!head) return;
  let actions = head.querySelector<HTMLElement>(".page-actions");
  if (!actions) {
    actions = document.createElement("div");
    actions.className = "page-actions";
    head.appendChild(actions);
  }
  actions.insertAdjacentHTML("beforeend", newRecordButton(kind, label));
  wireNewRecordButton(actions, () => reload());
}

// installJsonButton injects the bulk JSON import/export pill into the page head
// for a record kind; import reloads the view via `reload`.
function installJsonButton(container: HTMLElement, kind: string, reload: () => void) {
  const head = container.querySelector<HTMLElement>(".page-head");
  if (!head) return;
  let actions = head.querySelector<HTMLElement>(".page-actions");
  if (!actions) {
    actions = document.createElement("div");
    actions.className = "page-actions";
    head.appendChild(actions);
  }
  actions.insertAdjacentHTML("beforeend", jsonButton());
  wireJsonButton(actions, kind, () => reload());
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
  container.innerHTML = `${pageHead(title, sub)}<div class="loading">${esc(t("common.loading"))}</div>`;
  try {
    // Ensure name maps + effect semantics are ready so tables render
    // "Name (id)" and effects decode to human sentences.
    await Promise.all([loadNames(), loadLore()]);
    const rows = await fetcher();
    container.innerHTML = pageHead(title, `${rows.length} ${t("common.records")} \u00B7 ${sub}`);
    const host = document.createElement("div");
    host.className = "table-host";
    container.appendChild(host);
    build(host, rows);
  } catch (err) {
    container.innerHTML =
      pageHead(title, sub) +
      `<div class="placeholder"><div class="big">\u26A0</div><div>${esc(
        t("common.couldNotLoad", { what: title.toLowerCase() })
      )}</div><div style="margin-top:6px;font-size:12.5px" class="mono">${esc(
        (err as Error).message
      )}</div></div>`;
  }
}

export function viewSpells(c: HTMLElement) {
  withLoad(c, t("nav.spells"), "spells.dat", getSpells, (host, rows) => {
    const cols: Column<(typeof rows)[number]>[] = [
      { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "70px" },
      { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("spell", r.ID, 44), width: "56px", align: "center" },
      {
        key: "name",
        label: "Name",
        value: (r) => nameOf("spells", r.ID) || String(r.ID),
        render: (r) => `<span class="row-name">${esc(nameOf("spells", r.ID) || "\u2014")}</span>`,
      },
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
      {
        key: "script",
        label: "Script",
        value: (r) => (r.ScriptID > 0 && hasScript(r.ScriptID) ? 1 : 0),
        render: (r) =>
          r.ScriptID > 0 && hasScript(r.ScriptID)
            ? `<span class="tb-badge script">Lua ${r.ScriptID}</span>`
            : "\u2014",
        align: "center",
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
      { key: "hasScript", label: "Has Lua script", kind: "toggle", value: (r) => r.ScriptID > 0 && hasScript(r.ScriptID) },
    ];
    const tableCfg = {
      columns: cols,
      rows,
      facets,
      exportName: "spells",
      searchText: (r: Spell) =>
        `${r.ID} ${nameOf("spells", r.ID)} ${r.Criterion} ${nameOf("breeds", r.BreedID)} ${r.ScriptID} ${(r.Effects ?? [])
          .map((e) => decodeEffectText(e))
          .join(" ")}`,
      detail: (r: Spell) => spellEditor(r),
      drawerTitle: (r: Spell) => `${nameOf("spells", r.ID) || "Spell"} (${r.ID})`,
      onDraw: (c: HTMLElement) => {
        wireIconCells(c);
        wireSimulator(c);
        wireCrosslinks(c);
        // Mount the full effect editor into any expanded spell drawer.
        c.querySelectorAll<HTMLElement>(".fe-mount:not([data-fe-done])").forEach((mount) => {
          mount.dataset.feDone = "1";
          const sid = Number(mount.dataset.feSpell);
          const spell = rows.find((r) => r.ID === sid);
          if (spell) mountEffectEditor(mount, sid, spell.Effects);
        });
        // Mount the Lua spell-script editor into any expanded spell drawer.
        c.querySelectorAll<HTMLElement>(".sc-mount").forEach((mount) => {
          mountScriptEditor(mount, Number(mount.dataset.scScript));
        });
        // Track unsaved changes on any expanded spell form.
        c.querySelectorAll<HTMLElement>(".spell-edit").forEach((form) => {
          const sid = Number(form.dataset.spell);
          const spell = rows.find((r) => r.ID === sid);
          if (spell) trackSpellDirty(form, spell);
        });
      },
    };
    mountTable(host, tableCfg);
    // Load the script index in the background, then re-render so the "script"
    // column/badges reflect which spells actually have a Lua file.
    loadScriptIndex().then(() => mountTable(host, tableCfg));
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
    if (host.parentElement) {
      installNewButton(host.parentElement, "spell", t("common.newSpell"), () => viewSpells(c));
      installJsonButton(host.parentElement, "spells", () => viewSpells(c));
    }
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
      : t("hero.noEffects");
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
      : t("hero.noEffects");
  const stat = (ico: string, label: string, val: string) =>
    `<div class="hero-stat"><span class="hs-ico">${ico}</span><div><b>${esc(val)}</b><span>${esc(label)}</span></div></div>`;
  const apIco = gameIcon("ap", 18) || "\u25C8";
  const tags: string[] = [];
  if (s.CastTestLineOfSight) tags.push(`<span class="hero-tag">${esc(t("hero.tagLos"))}</span>`);
  if (s.CastOnlyLine) tags.push(`<span class="hero-tag">${esc(t("hero.tagLine"))}</span>`);
  if (s.NeedFreeCell) tags.push(`<span class="hero-tag">${esc(t("hero.tagFree"))}</span>`);
  return `
    <div class="entity-hero">
      <div class="hero-icon">${iconCell("spell", s.ID, 72)}</div>
      <div class="hero-main">
        <div class="hero-title">${esc(name)} ${breed ? `<span class="hero-breed">${esc(breed)}</span>` : ""}</div>
        <div class="hero-summary">${esc(summary)}</div>
        <div class="hero-stats">
          ${stat(apIco, t("hero.apCost"), String(s.ActionPointsCost))}
          ${stat("\u2316", t("hero.range"), `${s.RangeMin}\u2013${s.RangeMax}`)}
          ${stat("\u2726", t("hero.effects"), String(s.Effects?.length ?? 0))}
          ${s.Price ? stat("\u25C9", t("hero.price"), s.Price.toLocaleString()) : ""}
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
  // Pull the AoE from the first effect that defines a non-point area, so the
  // footprint shows range + area of effect together when the spell has one.
  const areaEff = (s.Effects ?? []).find((e) => (e.AreaSize?.[0] ?? 0) > 0);
  return `
    ${spellHero(s)}
    ${spellAreaBlock({
      rangeMin: s.RangeMin,
      rangeMax: s.RangeMax,
      onlyLine: s.CastOnlyLine,
      aoeShape: areaEff ? areaEff.AreaShape : undefined,
      aoeSize: areaEff ? areaEff.AreaSize : null,
    })}
    <div class="spell-edit" data-spell="${s.ID}">
      <div class="edit-title">${esc(t("spell.editTitle", { id: s.ID }))}</div>
      <div class="edit-grid">
        ${num("actionPointsCost", t("spell.apCost"), s.ActionPointsCost)}
        ${num("rangeMin", t("spell.rangeMin"), s.RangeMin)}
        ${num("rangeMax", t("spell.rangeMax"), s.RangeMax)}
        ${num("castFrequencyMaxPerTurn", t("spell.freqTurn"), s.CastFrequencyMaxPerTurn)}
        ${num("castFrequencyMaxPerPlayer", t("spell.freqTarget"), s.CastFrequencyMaxPerPlayer)}
        ${num("castFrequencyMinInterval", t("spell.minInterval"), s.CastFrequencyMinInterval)}
        ${num("price", t("spell.price"), s.Price, -2147483648, 2147483647)}
      </div>
      <div class="edit-checks">
        ${chk("castTestLineOfSight", t("spell.testLos"), s.CastTestLineOfSight)}
        ${chk("castOnlyLine", t("spell.onlyLine"), s.CastOnlyLine)}
        ${chk("needFreeCell", t("spell.needFreeCell"), s.NeedFreeCell)}
      </div>
      <div class="edit-full">
        <span>${esc(t("spell.criterion"))}</span>
        <input class="edit-in mono" type="text" data-f="criterion" placeholder="${esc(
          t("spell.criterionPlaceholder")
        )}" value="${esc(s.Criterion)}" />
        <div class="edit-hint">${esc(t("spell.criterionHint"))}</div>
      </div>
      <div class="edit-actions">
        <button class="primary" data-save-spell="${s.ID}">${esc(t("spell.save"))}</button>
        <span class="edit-status" data-status="${s.ID}"></span>
      </div>
    </div>
    ${simulatorHTML(s.Effects)}
    <div class="fe-mount" data-fe-spell="${s.ID}"></div>
    <div class="sc-mount" data-sc-script="${s.ScriptID}"></div>`;
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

  if (statusEl) statusEl.textContent = t("spell.saving");
  saveSpells([edit])
    .then((res) => {
      if (statusEl)
        statusEl.innerHTML = `<span class="ok">${esc(t("spell.saved", { bytes: res.bytes }))}</span>`;
      markClean(`spell:${id}`);
    })
    .catch((err) => {
      if (statusEl) statusEl.innerHTML = `<span class="err">${esc((err as Error).message)}</span>`;
    });
}

// trackSpellDirty registers "spell:<id>" as dirty whenever the form diverges
// from the saved spell record, and clears it when they match again.
function trackSpellDirty(form: HTMLElement, s: Spell) {
  if (form.dataset.dirtyWired) return;
  form.dataset.dirtyWired = "1";
  const original: Record<string, string> = {
    actionPointsCost: String(s.ActionPointsCost),
    rangeMin: String(s.RangeMin),
    rangeMax: String(s.RangeMax),
    castFrequencyMaxPerTurn: String(s.CastFrequencyMaxPerTurn),
    castFrequencyMaxPerPlayer: String(s.CastFrequencyMaxPerPlayer),
    castFrequencyMinInterval: String(s.CastFrequencyMinInterval),
    price: String(s.Price),
    castTestLineOfSight: String(s.CastTestLineOfSight),
    castOnlyLine: String(s.CastOnlyLine),
    needFreeCell: String(s.NeedFreeCell),
    criterion: s.Criterion,
  };
  const recompute = () => {
    let dirty = false;
    form.querySelectorAll<HTMLInputElement>("[data-f]").forEach((inp) => {
      const key = inp.dataset.f!;
      if (!(key in original)) return;
      const cur = inp.type === "checkbox" ? String(inp.checked) : inp.value;
      if (cur !== original[key]) dirty = true;
    });
    setDirty(`spell:${s.ID}`, dirty);
  };
  form.querySelectorAll<HTMLInputElement>("[data-f]").forEach((inp) => {
    inp.addEventListener("input", recompute);
    inp.addEventListener("change", recompute);
  });
}

// openSpellDrawer renders the full editable spell detail (hero + footprint +
// edit form + simulator + effect/script editors) in a standalone right-side
// overlay drawer. Reused by the Grimoire so clicking a spell opens the same
// editor as the Spells table instead of redirecting. Ensures the Lua script
// index is loaded so the script editor resolves.
export async function openSpellDrawer(s: Spell) {
  await loadScriptIndex().catch(() => {});
  // Tear down any previously-open standalone spell drawer.
  document.querySelectorAll(".sd-standalone").forEach((el) => el.remove());

  const backdrop = document.createElement("div");
  backdrop.className = "tb-drawer-backdrop sd-standalone";
  const drawer = document.createElement("aside");
  drawer.className = "tb-drawer sd-standalone";
  drawer.innerHTML = `
    <div class="tb-drawer-head">
      <span class="tb-drawer-title">${esc(nameOf("spells", s.ID) || "Spell")} (${s.ID})</span>
      <button class="tb-drawer-close" title="Close (Esc)" aria-label="Close">\u00D7</button>
    </div>
    <div class="tb-drawer-body">${spellEditor(s)}</div>`;
  document.body.appendChild(backdrop);
  document.body.appendChild(drawer);

  const body = drawer.querySelector<HTMLElement>(".tb-drawer-body")!;
  wireIconCells(body);
  wireSimulator(body);
  wireCrosslinks(body);
  body.querySelectorAll<HTMLElement>(".fe-mount:not([data-fe-done])").forEach((mount) => {
    mount.dataset.feDone = "1";
    if (Number(mount.dataset.feSpell) === s.ID) mountEffectEditor(mount, s.ID, s.Effects);
  });
  body.querySelectorAll<HTMLElement>(".sc-mount").forEach((mount) => {
    mountScriptEditor(mount, Number(mount.dataset.scScript));
  });
  body.querySelectorAll<HTMLElement>(".spell-edit").forEach((form) => trackSpellDirty(form, s));

  const close = () => {
    backdrop.remove();
    drawer.remove();
    document.removeEventListener("keydown", onKey);
  };
  const onKey = (ev: KeyboardEvent) => {
    if (ev.key === "Escape") close();
  };
  backdrop.addEventListener("click", close);
  drawer.querySelector<HTMLButtonElement>(".tb-drawer-close")!.addEventListener("click", close);
  document.addEventListener("keydown", onKey);
  // Save is delegated on the drawer body.
  body.addEventListener("click", (ev) => {
    const el = ev.target as HTMLElement;
    if (el.dataset.saveSpell != null) onSaveSpell(body, Number(el.dataset.saveSpell));
  });
}

export function viewCards(c: HTMLElement) {
  c.innerHTML = pageHead(t("nav.cards"), t("view.cards.sub")) + `<div class="loading">${esc(t("common.loading"))}</div>`;
  Promise.all([getCoachCards(), getFighterCards(), loadNames()])
    .then(([coach, fighter]) => {
      c.innerHTML = pageHead(t("nav.cards"),
        t("view.cards.count", { coach: coach.length, fighter: fighter.length }),
        newRecordButton("fighterCard", t("cards.newFighter")) + newRecordButton("coachCard", t("cards.newCoach"))
      );
      wireNewRecordButton(c, () => viewCards(c));
      installJsonButton(c, "cards", () => viewCards(c));
      const tabs = document.createElement("div");
      tabs.className = "subtabs";
      tabs.innerHTML = `<button class="subtab active" data-t="coach">${esc(
        t("cards.tabCoach", { n: coach.length })
      )}</button><button class="subtab" data-t="fighter">${esc(
        t("cards.tabFighter", { n: fighter.length })
      )}</button>`;
      c.appendChild(tabs);
      const host = document.createElement("div");
      host.className = "table-host";
      c.appendChild(host);

      const drawCoach = () =>
        mountTable(host, {
          rows: coach,
          onDraw: (h) => {
            wireIconCells(h);
            wireEditForm(
              h,
              coach.map((r) => coachCardForm(r))
            );
          },
          columns: [
            { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
            { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("coachCard", r.ID, 44), width: "56px", align: "center" },
            {
              key: "name",
              label: "Name",
              value: (r) => nameOf("coachCards", r.ID) || String(r.ID),
              render: (r) => `<span class="row-name">${esc(nameOf("coachCards", r.ID) || "\u2014")}</span>`,
            },
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
          detail: (r) => editFormHTML(coachCardForm(r)),
          drawerTitle: (r) => `${nameOf("coachCards", r.ID) || "Coach card"} (${r.ID})`,
        });
      const drawFighter = () =>
        mountTable(host, {
          rows: fighter,
          onDraw: (h) => {
            wireIconCells(h);
            wireCrosslinks(h);
            wireEditForm(
              h,
              fighter.map((r) => fighterCardForm(r))
            );
            mountEffectEditors(h, fighter, "card");
            h.querySelectorAll<HTMLElement>(".sc-mount").forEach((m) =>
              mountScriptEditor(m, Number(m.dataset.scScript))
            );
          },
          columns: [
            { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
            { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("fighterCard", r.ID, 44), width: "56px", align: "center" },
            {
              key: "name",
              label: "Name",
              value: (r) => nameOf("fighterCards", r.ID) || String(r.ID),
              render: (r) => `<span class="row-name">${esc(nameOf("fighterCards", r.ID) || "\u2014")}</span>`,
            },
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
                ["\u2726", t("hero.effects"), String(r.Effects?.length ?? 0)],
                ["\u25C9", t("hero.value"), String(r.Value)],
              ],
            }) +
            editFormHTML(fighterCardForm(r)) +
            `<div class="fe-mount" data-fe-kind="card" data-fe-id="${r.ID}"></div>` +
            `<div class="sc-mount" data-sc-script="${r.ScriptID}"></div>`,
          drawerTitle: (r) => `${nameOf("fighterCards", r.ID) || "Fighter card"} (${r.ID})`,
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
        pageHead(t("nav.cards"), "cards.dat") +
        `<div class="placeholder"><div class="big">\u26A0</div><div>Could not load cards.</div><div class="mono" style="margin-top:6px;font-size:12.5px">${esc(
          (err as Error).message
        )}</div></div>`;
    });
}

function fighterCardType(t: number): string {
  const names: Record<number, string> = { 1: "Weapon", 2: "Pet", 3: "Cloak", 4: "Hat", 5: "Dofus" };
  return names[t] ? `<span class="tb-badge">${names[t]}</span>` : String(t);
}

// Active summon-thumbnail disposers, keyed by their host container, so a table
// re-draw (which replaces the DOM) can stop the old RAF loops before mounting
// fresh ones -- no leaked animation loops.
const summonThumbDisposers = new WeakMap<HTMLElement, (() => void)[]>();

// mountSummonThumbs finds each expanded summon's .summon-thumb placeholder and
// plays that creature's animation (animations/<gfx>.sba) in it.
function mountSummonThumbs(host: HTMLElement, _rows: { ID: number; Gfx: number }[]) {
  // Stop any thumbs from the previous render of this host.
  const prev = summonThumbDisposers.get(host);
  if (prev) prev.forEach((d) => d());
  const disposers: (() => void)[] = [];
  host.querySelectorAll<HTMLElement>(".summon-thumb:not([data-thumb-done])").forEach((mount) => {
    mount.dataset.thumbDone = "1";
    const gfx = Number(mount.dataset.summonGfx);
    if (!gfx || gfx <= 0) {
      mount.innerHTML = `<div class="anim-thumb-empty">no gfx</div>`;
      return;
    }
    // Size the canvas to match the box (row .sm = 46, hero = 96).
    const size = mount.classList.contains("sm") ? 46 : 96;
    disposers.push(mountAnimThumb(mount, "animations.jar", `animations/${gfx}.sba`, size));
  });
  summonThumbDisposers.set(host, disposers);
}

// summonHero renders a summon's detail banner: the live creature animation as
// the icon, the name, inlined HP/AP/MP stat chips, and the cast-spell link.
function summonHero(r: {
  ID: number;
  HP: number;
  AP: number;
  MP: number;
  Gfx: number;
  SpellID: number;
}): string {
  const name = nameOf("summons", r.ID) || `Summon ${r.ID}`;
  const stat = (ico: string, label: string, v: number) =>
    `<span class="hero-stat-inline"><span class="hs-ico">${ico}</span><b>${v}</b><span class="hs-l">${label}</span></span>`;
  const thumb =
    r.Gfx > 0
      ? `<div class="summon-thumb hero" data-summon-gfx="${r.Gfx}"></div>`
      : `<div class="summon-thumb hero"><div class="anim-thumb-empty">no gfx</div></div>`;
  return `
    <div class="entity-hero summon-hero">
      ${thumb}
      <div class="hero-main">
        <div class="hero-title">${esc(name)} <span class="id-dim">(${r.ID})</span></div>
        <div class="hero-stats-inline">
          ${stat(gameIcon("hp", 15) || "\u2665", "HP", r.HP)}
          ${stat(gameIcon("ap", 15) || "\u25C8", "AP", r.AP)}
          ${stat(gameIcon("mp", 15) || "\u2316", "MP", r.MP)}
        </div>
        <div class="hero-sub-line">${t("hero.summonLine", {
          gfx: r.Gfx,
          spell: crosslinkSpell(r.SpellID),
        })}</div>
      </div>
    </div>`;
}

export function viewSummonings(c: HTMLElement) {
  withLoad(c, t("nav.summonings"), "summoning.dat", getSummonings, (host, rows) => {
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
        { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
        {
          key: "icon",
          label: "",
          value: (r) => r.Gfx,
          render: (r) =>
            r.Gfx > 0
              ? `<div class="summon-thumb sm" data-summon-gfx="${r.Gfx}"></div>`
              : "\u2014",
          width: "56px",
          align: "center",
        },
        {
          key: "name",
          label: "Name",
          value: (r) => nameOf("summons", r.ID) || String(r.ID),
          render: (r) => `<span class="row-name">${esc(nameOf("summons", r.ID) || "\u2014")}</span>`,
        },
        { key: "HP", label: "HP", value: (r) => r.HP, align: "right" },
        { key: "AP", label: "AP", value: (r) => r.AP, align: "right" },
        { key: "MP", label: "MP", value: (r) => r.MP, align: "right" },
        { key: "SpellID", label: "Spell", value: (r) => nameOf("spells", r.SpellID) || String(r.SpellID), render: (r) => esc(label("spells", r.SpellID)) },
      ],
      onDraw: (h) => {
        wireIconCells(h);
        wireCrosslinks(h);
        wireEditForm(
          h,
          rows.map((r) => summoningForm(r))
        );
        mountSummonThumbs(h, rows);
      },
      detail: (r) => summonHero(r) + editFormHTML(summoningForm(r)),
      drawerTitle: (r) => `${nameOf("summons", r.ID) || "Summon"} (${r.ID})`,
    });
    if (host.parentElement) {
      installNewButton(host.parentElement, "summoning", t("common.newSummon"), () => viewSummonings(c));
      installJsonButton(host.parentElement, "summonings", () => viewSummonings(c));
    }
  });
}

// summoningForm declares the editable-field spec for one summoning.
function summoningForm(r: {
  ID: number;
  HP: number;
  AP: number;
  MP: number;
  Gfx: number;
  SpellID: number;
}): EditFormSpec {
  return {
    id: r.ID,
    title: t("form.summoning", { id: r.ID }),
    fields: [
      { key: "hp", label: t("field.hp"), type: "number", value: r.HP, min: 0 },
      { key: "ap", label: t("field.ap"), type: "number", value: r.AP, min: 0 },
      { key: "mp", label: t("field.mp"), type: "number", value: r.MP, min: 0 },
      { key: "gfx", label: t("field.gfx"), type: "number", value: r.Gfx },
      { key: "spellId", label: t("field.spellId"), type: "number", value: r.SpellID },
    ],
    save: (v) =>
      saveSummonings([
        {
          id: r.ID,
          hp: v.hp as number,
          ap: v.ap as number,
          mp: v.mp as number,
          gfx: v.gfx as number,
          spellId: v.spellId as number,
        },
      ]),
  };
}

// fighterCardForm declares editable scalar fields for a fighter card (effects
// are shown/edited separately and preserved on save).
function fighterCardForm(r: {
  ID: number;
  Type: number;
  Value: number;
  ScriptID: number;
  SubType: number;
}): EditFormSpec {
  return {
    id: r.ID,
    title: t("form.fighterCard", { id: r.ID }),
    fields: [
      { key: "type", label: t("field.type"), type: "number", value: r.Type, min: 0, max: 255, hint: t("hint.fighterType") },
      { key: "value", label: t("field.value"), type: "number", value: r.Value },
      { key: "scriptId", label: t("field.scriptId"), type: "number", value: r.ScriptID },
      { key: "subType", label: t("field.subType"), type: "number", value: r.SubType },
    ],
    save: (v) =>
      saveFighterCards([
        {
          id: r.ID,
          type: v.type as number,
          value: v.value as number,
          scriptId: v.scriptId as number,
          subType: v.subType as number,
        },
      ]),
  };
}

// coachCardForm declares editable scalar fields for a coach card.
function coachCardForm(r: { ID: number; Type: number; Value: number; Set: number }): EditFormSpec {
  return {
    id: r.ID,
    title: t("form.coachCard", { id: r.ID }),
    fields: [
      { key: "type", label: t("field.type"), type: "number", value: r.Type },
      { key: "value", label: t("field.value"), type: "number", value: r.Value },
      { key: "set", label: t("field.set"), type: "number", value: r.Set },
    ],
    save: (v) =>
      saveCoachCards([
        { id: r.ID, type: v.type as number, value: v.value as number, set: v.set as number },
      ]),
  };
}

// crosslinkSpell renders a clickable chip to a spell by id (or a dash).
function crosslinkSpell(spellId: number): string {
  if (!spellId) return `<span class="detail-empty">\u2014</span>`;
  const name = nameOf("spells", spellId) || `Spell ${spellId}`;
  return `<button class="xlink" data-xview="spells" data-xquery="${esc(name)}">${esc(name)} <span class="xlink-ico">\u2197</span></button>`;
}

export function viewStaticEffects(c: HTMLElement) {
  withLoad(c, t("nav.staticEffects"), "staticEffects.dat", getStaticEffects, (host, rows) => {
    mountTable(host, {
      rows,
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
      onDraw: (h) => {
        wireCrosslinks(h);
        wireEditForm(
          h,
          rows.map((r) => staticEffectForm(r))
        );
        h.querySelectorAll<HTMLElement>(".sc-mount").forEach((m) =>
          mountScriptEditor(m, Number(m.dataset.scScript))
        );
      },
      detail: (r) =>
        `<div class="detail-grid">
          <div><span>Area params</span><b class="mono">${fmtList(r.AreaParams)}</b></div>
          <div><span>App. triggers</span><b class="mono">${fmtList(r.ApplicationTriggers)}</b></div>
          <div><span>Unapp. triggers</span><b class="mono">${fmtList(r.UnapplicationTriggers)}</b></div>
          <div><span>App. targets</span><b class="mono">${fmtList(r.ApplicationTargets)}</b></div>
          <div><span>Deactivation delay</span><b class="mono">${fmtList(r.DeactivationDelay)}</b></div>
          <div><span>App. condition</span><b>${r.ApplicationCondition}</b></div>
        </div>
        ${editFormHTML(staticEffectForm(r))}
        ${effectsDetail(r.Effects)}
        <div class="sc-mount" data-sc-script="${r.ScriptID}"></div>`,
      drawerTitle: (r) => `Static effect #${r.ID}`,
    });
    if (host.parentElement)
      installJsonButton(host.parentElement, "staticEffects", () => viewStaticEffects(c));
  });
}

function staticEffectForm(r: {
  ID: number;
  ScriptID: number;
  AreaShapeID: number;
  MaxExecutionCount: number;
  TargetsToShow: number;
}): EditFormSpec {
  return {
    id: r.ID,
    title: t("form.staticEffect", { id: r.ID }),
    fields: [
      { key: "scriptId", label: t("field.scriptId"), type: "number", value: r.ScriptID },
      { key: "areaShapeId", label: t("field.areaShape"), type: "number", value: r.AreaShapeID, hint: t("hint.areaShape") },
      { key: "maxExecutionCount", label: t("field.maxExec"), type: "number", value: r.MaxExecutionCount },
      { key: "targetsToShow", label: t("field.targetsToShow"), type: "number", value: r.TargetsToShow },
    ],
    save: (v) =>
      saveStaticEffects([
        {
          id: r.ID,
          scriptId: v.scriptId as number,
          areaShapeId: v.areaShapeId as number,
          maxExecutionCount: v.maxExecutionCount as number,
          targetsToShow: v.targetsToShow as number,
        },
      ]),
  };
}

export function viewEvents(c: HTMLElement) {
  withLoad(c, t("nav.events"), "events.dat", getEvents, (host, rows) => {
    mountTable(host, {
      rows,
      onDraw: (h) => {
        wireIconCells(h);
        wireCrosslinks(h);
        wireEditForm(
          h,
          rows.map((r) => eventForm(r))
        );
        mountEffectEditors(h, rows, "event");
      },
      searchText: (r) =>
        `${r.ID} ${nameOf("events", r.ID)} ${(r.Effects ?? []).map((e) => decodeEffectText(e)).join(" ")}`,
      exportName: "events",
      facets: [
        { key: "auto", label: "Auto desc", kind: "toggle", value: (r) => r.UseAutoDescription },
        { key: "hasfx", label: "Has effects", kind: "toggle", value: (r) => (r.Effects?.length ?? 0) > 0 },
      ],
      columns: [
        { key: "ID", label: "ID", value: (r) => r.ID, align: "right", width: "80px" },
        { key: "icon", label: "", value: (r) => r.ID, render: (r) => iconCell("event", r.ID, 44), width: "56px", align: "center" },
        {
          key: "name",
          label: "Name",
          value: (r) => nameOf("events", r.ID) || String(r.ID),
          render: (r) => `<span class="row-name">${esc(nameOf("events", r.ID) || "\u2014")}</span>`,
        },
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
          stats: [["\u2726", t("hero.effects"), String(r.Effects?.length ?? 0)]],
        }) +
        editFormHTML(eventForm(r)) +
        `<div class="fe-mount" data-fe-kind="event" data-fe-id="${r.ID}"></div>`,
      drawerTitle: (r) => `${nameOf("events", r.ID) || "Event"} (${r.ID})`,
    });
    if (host.parentElement) installJsonButton(host.parentElement, "events", () => viewEvents(c));
  });
}
