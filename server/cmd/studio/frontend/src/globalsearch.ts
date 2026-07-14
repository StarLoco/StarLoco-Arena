// globalsearch.ts -- a scoped global content finder for the sidebar. It indexes
// every named record (spells, cards, breeds, events, summons) from the loaded
// name bundle plus the client's Lua script ids, and lets you jump straight to
// any of them. This is a *content* search (find a record/asset/string), NOT a
// command palette -- it never runs actions, it only navigates.
//
// Selecting a result dispatches the same `studio:navigate` bus the cross-links
// use, deep-linking into the target view pre-filtered on the match.

import { loadNames } from "./names";
import { listScriptIDs } from "./backend";

// A single searchable entry.
interface SearchItem {
  label: string; // display name
  sub: string; // secondary line (kind + id)
  view: string; // target nav view
  query: string; // search query to focus the record in that view
  kind: string; // group label
  haystack: string; // lowercased text to match against
}

let index: SearchItem[] | null = null;
let building: Promise<SearchItem[]> | null = null;

// Maps a name-bundle kind to its target view + human group label.
const KIND_VIEW: Record<string, { view: string; group: string }> = {
  spells: { view: "spells", group: "Spell" },
  fighterCards: { view: "cards", group: "Fighter card" },
  coachCards: { view: "cards", group: "Coach card" },
  events: { view: "events", group: "Event" },
  summons: { view: "summonings", group: "Summon" },
  breeds: { view: "spellbook", group: "Breed" },
};

// buildIndex assembles the flat search index once (cached). Safe to call often.
async function buildIndex(): Promise<SearchItem[]> {
  if (index) return index;
  if (building) return building;
  building = (async () => {
    const items: SearchItem[] = [];
    try {
      const names = await loadNames();
      for (const kind of Object.keys(KIND_VIEW)) {
        const map = (names as unknown as Record<string, Record<string, string>>)[kind];
        if (!map) continue;
        const { view, group } = KIND_VIEW[kind];
        for (const [id, name] of Object.entries(map)) {
          if (!name) continue;
          items.push({
            label: name,
            sub: `${group} \u00B7 ${id}`,
            view,
            query: name,
            kind: group,
            haystack: `${name} ${id} ${group}`.toLowerCase(),
          });
        }
      }
    } catch {
      /* names may be unavailable; scripts still index below */
    }
    try {
      const scriptIds = await listScriptIDs();
      for (const id of scriptIds) {
        items.push({
          label: `scripts/${id}.lua`,
          sub: `Lua script \u00B7 ${id}`,
          view: "scripts",
          query: String(id),
          kind: "Lua script",
          haystack: `script lua ${id}`,
        });
      }
    } catch {
      /* client jar may be unavailable */
    }
    index = items;
    return items;
  })();
  return building;
}

// invalidateSearchIndex drops the cache (call after a language change so names
// re-index in the new language).
export function invalidateSearchIndex(): void {
  index = null;
  building = null;
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// search returns up to `limit` items ranked by match quality (prefix > word-
// start > substring). Empty query returns nothing.
function search(items: SearchItem[], q: string, limit = 12): SearchItem[] {
  const needle = q.trim().toLowerCase();
  if (!needle) return [];
  const scored: Array<{ it: SearchItem; score: number }> = [];
  for (const it of items) {
    const h = it.haystack;
    const at = h.indexOf(needle);
    if (at < 0) continue;
    let score = at; // earlier match = better
    if (it.label.toLowerCase().startsWith(needle)) score -= 1000; // strong prefix boost
    else if (h.includes(" " + needle)) score -= 200; // word-start boost
    scored.push({ it, score });
  }
  scored.sort((a, b) => a.score - b.score || a.it.label.length - b.it.label.length);
  return scored.slice(0, limit).map((s) => s.it);
}

// mountGlobalSearch renders the search box + results dropdown into host and
// wires keyboard + click navigation. Idempotent per host.
export function mountGlobalSearch(host: HTMLElement) {
  host.innerHTML = `
    <div class="gs">
      <input class="gs-input" type="search" placeholder="Search records & scripts\u2026" aria-label="Global search" />
      <div class="gs-results" hidden></div>
    </div>`;
  const input = host.querySelector<HTMLInputElement>(".gs-input")!;
  const results = host.querySelector<HTMLElement>(".gs-results")!;
  let items: SearchItem[] = [];
  let active = -1;
  let current: SearchItem[] = [];

  buildIndex().then((idx) => {
    items = idx;
  });

  const render = () => {
    if (current.length === 0) {
      results.hidden = true;
      results.innerHTML = "";
      return;
    }
    results.hidden = false;
    results.innerHTML = current
      .map(
        (it, i) => `
        <button class="gs-item ${i === active ? "active" : ""}" data-i="${i}">
          <span class="gs-item-label">${esc(it.label)}</span>
          <span class="gs-item-sub">${esc(it.sub)}</span>
        </button>`
      )
      .join("");
    results.querySelectorAll<HTMLButtonElement>(".gs-item").forEach((btn) => {
      btn.addEventListener("mousedown", (e) => {
        e.preventDefault(); // keep focus; run before blur
        choose(Number(btn.dataset.i));
      });
    });
  };

  const choose = (i: number) => {
    const it = current[i];
    if (!it) return;
    input.value = "";
    current = [];
    active = -1;
    render();
    input.blur();
    window.dispatchEvent(
      new CustomEvent("studio:navigate", { detail: { view: it.view, query: it.query } })
    );
  };

  input.addEventListener("input", () => {
    current = search(items, input.value);
    active = current.length ? 0 : -1;
    render();
  });

  input.addEventListener("keydown", (e) => {
    if (results.hidden) return;
    if (e.key === "ArrowDown") {
      e.preventDefault();
      active = Math.min(active + 1, current.length - 1);
      render();
    } else if (e.key === "ArrowUp") {
      e.preventDefault();
      active = Math.max(active - 1, 0);
      render();
    } else if (e.key === "Enter") {
      e.preventDefault();
      if (active >= 0) choose(active);
    } else if (e.key === "Escape") {
      input.value = "";
      current = [];
      render();
      input.blur();
    }
  });

  // Hide results when focus leaves the search.
  input.addEventListener("blur", () => {
    // Delay so a result mousedown can fire first.
    setTimeout(() => {
      current = [];
      render();
    }, 120);
  });
}
