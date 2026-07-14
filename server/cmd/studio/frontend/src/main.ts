import "./style.css";
import {
  getEnv,
  getPaths,
  setDataDir,
  setClientDir,
  getDataCounts,
  getLanguage,
  setLanguage,
  type Paths,
  type DataCounts,
  type LanguageState,
} from "./backend";
import { loadNames } from "./names";
import {
  viewSpells,
  viewCards,
  viewSummonings,
  viewStaticEffects,
  viewEvents,
} from "./views";
import { viewAssets } from "./assets";
import { viewSprites } from "./sprites";
import { viewMaps } from "./maps";
import { viewBuilder } from "./builder";
import { viewAnimations } from "./animations";
import { viewSpellbook } from "./spellbook";
import { viewTranslations } from "./translations";
import { viewDeploy } from "./deploy";
import { viewScripts } from "./scriptsview";
import { listScriptIDs } from "./backend";

// -------- App shell / navigation --------

interface NavEntry {
  id: string;
  label: string;
  icon: string;
  section: string;
  phase: number; // which roadmap phase implements it (for "coming soon" tags)
}

const NAV: NavEntry[] = [
  { id: "overview", label: "Overview", icon: "\u25C8", section: "Studio", phase: 0 },
  { id: "deploy", label: "Deploy", icon: "\u2B06", section: "Studio", phase: 0 },
  { id: "spellbook", label: "Spellbook", icon: "\u2727", section: "Game Data", phase: 1 },
  { id: "spells", label: "Spells", icon: "\u2726", section: "Game Data", phase: 1 },
  { id: "cards", label: "Cards", icon: "\u2617", section: "Game Data", phase: 1 },
  { id: "summonings", label: "Summonings", icon: "\u269C", section: "Game Data", phase: 1 },
  { id: "staticEffects", label: "Static Effects", icon: "\u2622", section: "Game Data", phase: 1 },
  { id: "events", label: "Events", icon: "\u26A1", section: "Game Data", phase: 1 },
  { id: "scripts", label: "Lua Scripts", icon: "\u2328", section: "Game Data", phase: 1 },
  { id: "translations", label: "Translations", icon: "\u2709", section: "Game Data", phase: 1 },
  { id: "assets", label: "Asset Browser", icon: "\u25A6", section: "Assets", phase: 2 },
  { id: "sprites", label: "Sprites (TGA)", icon: "\u25A3", section: "Assets", phase: 3 },
  { id: "animations", label: "Animations", icon: "\u2637", section: "Assets", phase: 6 },
  { id: "maps", label: "Maps", icon: "\u25C6", section: "World", phase: 4 },
  { id: "builder", label: "Map Builder", icon: "\u2692", section: "World", phase: 7 },
];

let current = "overview";
let paths: Paths = {
  dataDir: "",
  clientDir: "",
  dataDirValid: false,
  clientDirValid: false,
};
let counts: DataCounts | null = null;
let scriptCount: number | null = null;
let langState: LanguageState = { current: "en", options: [] };

const app = document.querySelector<HTMLDivElement>("#app")!;

function h(html: string): string {
  return html;
}

function esc(s: string): string {
  return s.replace(/[&<>"]/g, (c) =>
    ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

function renderShell() {
  const sections = new Map<string, NavEntry[]>();
  for (const e of NAV) {
    if (!sections.has(e.section)) sections.set(e.section, []);
    sections.get(e.section)!.push(e);
  }

  let navHtml = `
    <div class="brand">
      <div class="logo">DA</div>
      <div class="title"><b>DofusArena Studio</b><span>Data &amp; Asset Explorer</span></div>
    </div>`;
  for (const [section, entries] of sections) {
    navHtml += `<div class="nav-section">${esc(section)}</div>`;
    for (const e of entries) {
      const badge = navBadge(e);
      navHtml += `
        <div class="nav-item ${e.id === current ? "active" : ""}" data-nav="${e.id}">
          <span class="ico">${e.icon}</span>
          <span>${esc(e.label)}</span>
          ${badge}
        </div>`;
    }
  }
  navHtml += `<div class="nav-spacer"></div>`;

  app.innerHTML = h(`
    <div class="nav">${navHtml}</div>
    <div class="main" id="main"></div>
    <div class="status" id="status"></div>
  `);

  app.querySelectorAll<HTMLElement>("[data-nav]").forEach((el) => {
    el.addEventListener("click", () => navigate(el.dataset.nav!));
  });

  renderStatus();
  renderPage();
}

// navBadge shows the live record count for data views (when loaded). Views
// without a count (assets/sprites/animations/maps/builder) show nothing, for a
// cleaner sidebar.
function navBadge(e: NavEntry): string {
  if (counts && !counts.error) {
    const map: Record<string, number> = {
      spells: counts.spells,
      cards: counts.coachCards + counts.fighterCards,
      summonings: counts.summonings,
      staticEffects: counts.staticEffects,
      events: counts.events,
    };
    if (e.id in map) return `<span class="count">${map[e.id].toLocaleString()}</span>`;
  }
  return "";
}

function renderStatus() {
  const status = document.querySelector<HTMLDivElement>("#status");
  if (!status) return;
  const dataOk = paths.dataDirValid;
  const clientOk = paths.clientDirValid;
  status.innerHTML = h(`
    <span><span class="dot ${dataOk ? "ok" : "err"}"></span>data ${dataOk ? "loaded" : "not found"}</span>
    <span><span class="dot ${clientOk ? "ok" : "err"}"></span>client ${clientOk ? "detected" : "not found"}</span>
    <span class="spacer"></span>
    <span class="mono" id="envline"></span>
  `);
  getEnv().then((env) => {
    const el = document.querySelector("#envline");
    if (el) el.textContent = `${env.os} \u00B7 studio ${env.version}`;
  });
}

// pendingFocus carries a deep-link target (search query) into the next page
// render so a cross-link can land on a specific record.
let pendingFocus: { query?: string } | null = null;

function navigate(id: string, focus?: { query?: string }) {
  current = id;
  pendingFocus = focus ?? null;
  app.querySelectorAll<HTMLElement>("[data-nav]").forEach((el) => {
    el.classList.toggle("active", el.dataset.nav === id);
  });
  renderPage();
}

// Global cross-link bus: any view can dispatch `studio:navigate` with a target
// view id and optional search query to jump the user there. This powers the
// clickable entity references (spell -> summon, card -> effect, etc.).
window.addEventListener("studio:navigate", (ev) => {
  const d = (ev as CustomEvent).detail as { view: string; query?: string };
  if (d?.view) navigate(d.view, { query: d.query });
});

// Keyboard nav: Alt+1..9 jumps to the Nth sidebar entry; "/" focuses the active
// view's search. Ignored while typing in an input (except Alt combos).
window.addEventListener("keydown", (e) => {
  const typing =
    document.activeElement instanceof HTMLInputElement ||
    document.activeElement instanceof HTMLTextAreaElement ||
    document.activeElement instanceof HTMLSelectElement;
  if (e.altKey && e.key >= "1" && e.key <= "9") {
    const idx = Number(e.key) - 1;
    if (idx < NAV.length) {
      e.preventDefault();
      navigate(NAV[idx].id);
    }
    return;
  }
  if (e.key === "/" && !typing) {
    const search = document.querySelector<HTMLInputElement>(".tb-search, .sprite-toolbar input, #entryFilter");
    if (search) {
      e.preventDefault();
      search.focus();
    }
  }
});

function renderPage() {
  const main = document.querySelector<HTMLDivElement>("#main");
  if (!main) return;
  const entry = NAV.find((e) => e.id === current)!;

  if (current === "overview") {
    main.innerHTML = overviewPage();
    wireOverview();
    return;
  }

  // Phase 1 data views.
  const dataViews: Record<string, (c: HTMLElement) => void> = {
    deploy: viewDeploy,
    spellbook: viewSpellbook,
    translations: viewTranslations,
    spells: viewSpells,
    cards: viewCards,
    summonings: viewSummonings,
    staticEffects: viewStaticEffects,
    events: viewEvents,
    scripts: viewScripts,
    assets: viewAssets,
    sprites: viewSprites,
    animations: viewAnimations,
    maps: viewMaps,
    builder: viewBuilder,
  };
  if (dataViews[current]) {
    dataViews[current](main);
    applyPendingFocus(main);
    return;
  }

  // Placeholder for phases not yet implemented in the frontend.
  main.innerHTML = h(`
    <div class="page-head">
      <h1>${esc(entry.label)}</h1>
      <span class="sub">roadmap phase ${entry.phase}</span>
    </div>
    <div class="placeholder">
      <div class="big">${entry.icon}</div>
      <div><b>${esc(entry.label)}</b> lands in Phase ${entry.phase}.</div>
      <div style="margin-top:6px;font-size:12.5px">
        This view is wired into the shell and will be filled in as the phases are implemented.
      </div>
    </div>
  `);
}

// applyPendingFocus pre-fills the table's search with a deep-link query, so a
// cross-link ("go to summon 42") lands filtered on that record. Retries briefly
// because the data view fetches asynchronously before mounting its table.
function applyPendingFocus(main: HTMLElement) {
  if (!pendingFocus?.query) return;
  const query = pendingFocus.query;
  pendingFocus = null;
  let tries = 0;
  const tick = () => {
    const input = main.querySelector<HTMLInputElement>(".tb-search");
    if (input) {
      input.value = query;
      input.dispatchEvent(new Event("input", { bubbles: true }));
      input.focus();
      return;
    }
    if (tries++ < 40) setTimeout(tick, 50);
  };
  tick();
}

function overviewPage(): string {
  const dataRow = pathRow("Data directory", paths.dataDir, paths.dataDirValid, "data");
  const clientRow = pathRow("Client directory", paths.clientDir, paths.clientDirValid, "client");

  const jump = (view: string, icon: string, title: string, desc: string) =>
    `<button class="ov-jump" data-nav-jump="${view}">
       <span class="ov-jump-ico">${icon}</span>
       <span class="ov-jump-t"><b>${esc(title)}</b><span>${esc(desc)}</span></span>
       <span class="ov-jump-arrow">\u2192</span>
     </button>`;

  return h(`
    <div class="ov-hero">
      <div class="ov-hero-badge">DA</div>
      <div>
        <h1 class="ov-hero-title">DofusArena Studio</h1>
        <p class="ov-hero-sub">Reverse-engineering workbench &mdash; browse the game's spells, cards, sprites, animations and arenas, all decoded to human terms.</p>
      </div>
    </div>

    <div class="ov-statband">
      ${overviewStat(counts?.spells, "Spells")}
      ${overviewStat(counts ? counts.coachCards + counts.fighterCards : undefined, "Cards")}
      ${overviewStat(counts?.summonings, "Summonings")}
      ${overviewStat(counts?.staticEffects, "Static effects")}
      ${overviewStat(counts?.events, "Events")}
      ${overviewStat(scriptCount ?? undefined, "Lua scripts")}
    </div>

    <div class="ov-section-title">Jump in</div>
    <div class="ov-jumps">
      ${jump("spellbook", "\u2727", "Spellbook", "Every spell by class, with live damage sim")}
      ${jump("cards", "\u2617", "Cards", "Weapons, pets, cloaks, hats & dofus")}
      ${jump("scripts", "\u2328", "Lua Scripts", "Edit spell scripts in-place, with highlighting")}
      ${jump("sprites", "\u25A3", "Sprites", "The full gfx.jar tile atlas")}
      ${jump("animations", "\u2637", "Animations", "Play, onion-skin & export .sba")}
      ${jump("maps", "\u25C6", "Maps", "Isometric arena viewer")}
      ${jump("assets", "\u25A6", "Assets", "Browse & extract every jar")}
    </div>

    <div class="card">
      <h2>Sources</h2>
      <p class="hint">
        The studio auto-detects the repo's <code>data/</code> and
        <code>client-compiled/</code> folders. Override them here if needed.
      </p>
      ${dataRow}
      ${clientRow}
    </div>

    <div class="card">
      <h2>Preferences</h2>
      <p class="hint">Language used for names &amp; descriptions (from the client's i18n).</p>
      <div class="pref-row">
        <div class="pref-label">Language</div>
        <select id="langSelect" class="pref-select">
          ${langState.options
            .map(
              (o) =>
                `<option value="${esc(o.code)}" ${o.code === langState.current ? "selected" : ""}>${esc(
                  o.label
                )}</option>`
            )
            .join("")}
        </select>
      </div>
    </div>

  `);
}

function overviewStat(n: number | undefined, label: string): string {
  const val = n == null ? "\u2014" : n.toLocaleString();
  return `<div class="stat"><div class="n">${val}</div><div class="l">${esc(label)}</div></div>`;
}

function pathRow(label: string, value: string, valid: boolean, kind: string): string {
  return h(`
    <div class="path-row">
      <div class="path-info">
        <div class="path-label">${esc(label)}</div>
        <div class="path-value ${value ? "" : "empty"}">${value ? esc(value) : "not set"}</div>
      </div>
      <span class="pill ${valid ? "ok" : "err"}">${valid ? "valid" : "missing"}</span>
      <button data-pick="${kind}">Change\u2026</button>
    </div>
  `);
}

function wireOverview() {
  document.querySelectorAll<HTMLElement>("[data-nav-jump]").forEach((btn) => {
    btn.addEventListener("click", () => navigate(btn.dataset.navJump!));
  });

  const langSel = document.querySelector<HTMLSelectElement>("#langSelect");
  langSel?.addEventListener("change", async () => {
    langState = await setLanguage(langSel.value);
    await loadNames(true); // refresh cached names for the new language
    await refreshCounts();
    renderPage(); // re-render current page with new names
  });

  document.querySelectorAll<HTMLButtonElement>("[data-pick]").forEach((btn) => {
    btn.addEventListener("click", async () => {
      const kind = btn.dataset.pick!;
      // Native directory dialog binding lands with Phase 2 (needs the
      // Wails runtime dialog API); for Phase 0 we accept a manual path via
      // prompt so the mechanism is testable in and out of the webview.
      const entered = window.prompt(
        `Enter the ${kind} directory path:`,
        kind === "data" ? paths.dataDir : paths.clientDir
      );
      if (entered == null) return;
      paths = kind === "data" ? await setDataDir(entered) : await setClientDir(entered);
      if (kind === "data") await refreshCounts();
      renderStatus();
      renderPage();
    });
  });
}

async function refreshCounts() {
  try {
    counts = await getDataCounts();
  } catch {
    counts = null;
  }
  // Script count comes from the client jar (independent of the data dir).
  listScriptIDs()
    .then((ids) => {
      scriptCount = ids.length;
      if (current === "overview") renderPage();
    })
    .catch(() => {
      scriptCount = null;
    });
  // Re-render nav badges if the shell is up.
  if (document.querySelector(".nav")) renderShell();
}

// -------- boot --------
async function boot() {
  paths = await getPaths();
  langState = await getLanguage();
  renderShell();
  loadNames(); // warm the name cache in the background
  if (paths.dataDirValid) refreshCounts();
  // Script count only needs the client jar; fetch it even if data dir is unset.
  if (paths.clientDirValid && !paths.dataDirValid) {
    listScriptIDs()
      .then((ids) => {
        scriptCount = ids.length;
        if (current === "overview") renderPage();
      })
      .catch(() => {});
  }
}

boot();
