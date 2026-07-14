// Shared name/icon resolution used by every view, so ids render as
// "Real Name (id)" and rows/tiles can lazily show their gui.jar icon with a
// graceful placeholder when none exists.

import { getNames, getIcon, type NamesBundle } from "./backend";

let bundle: NamesBundle | null = null;

// loadNames fetches (and caches) the active-language name bundle. Call after
// a language change with force=true to refresh.
export async function loadNames(force = false): Promise<NamesBundle> {
  if (bundle && !force) return bundle;
  bundle = await getNames();
  return bundle;
}

export function namesReady(): NamesBundle | null {
  return bundle;
}

type NameKind = "spells" | "breeds" | "fighterCards" | "coachCards" | "events" | "effects" | "summons";

// nameOf returns the localized name for an id, or "" if unknown. Client i18n
// strings carry printf-style placeholders the game fills at runtime (e.g. summon
// names are "%1s Gobball", where %1s is the owner's article/prefix). Those are
// meaningless out of a live fight, so strip them for display.
export function nameOf(kind: NameKind, id: number): string {
  if (!bundle) return "";
  return cleanName(bundle[kind][String(id)] ?? "");
}

// cleanName removes leading/embedded printf placeholders (%1s, %1$s, %s, %d …)
// and collapses the resulting whitespace, so "%1s Gobball" -> "Gobball".
export function cleanName(s: string): string {
  return s
    .replace(/%\d+\$?[sd]/g, "") // %1s, %1$s, %2d, …
    .replace(/%[sd]/g, "") // bare %s / %d
    .replace(/\s{2,}/g, " ")
    .trim();
}

// label renders "Name (id)" when a name exists, else just the id.
export function label(kind: NameKind, id: number): string {
  const n = nameOf(kind, id);
  return n ? `${n} (${id})` : String(id);
}

// labelHTML renders a name with the id in a muted span; falls back to the id.
export function labelHTML(kind: NameKind, id: number): string {
  const n = nameOf(kind, id);
  if (!n) return `<span class="id-only">${id}</span>`;
  return `${escape(n)} <span class="id-dim">(${id})</span>`;
}

function escape(s: string): string {
  return s.replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// ---- lazy icons ----

// In-session cache: "kind/id" -> dataURL | null (null = confirmed missing).
const iconCache = new Map<string, string | null>();

// iconCell returns HTML for a lazily-loaded icon cell. The <span> carries the
// kind/id in data-* attrs; wireIconCells (below) hydrates them via an
// IntersectionObserver so large tables stay fast.
export function iconCell(kind: string, id: number, size = 40): string {
  return `<span class="icon-cell" data-icon-kind="${kind}" data-icon-id="${id}" style="--isz:${size}px"><span class="icon-ph">${placeholderGlyph(
    kind
  )}</span></span>`;
}

function placeholderGlyph(kind: string): string {
  switch (kind) {
    case "spell":
      return "\u2726";
    case "fighterCard":
    case "coachCard":
      return "\u2617";
    case "event":
      return "\u26A1";
    default:
      return "\u25A0";
  }
}

let iconObserver: IntersectionObserver | null = null;

// wireIconCells attaches lazy loading to every .icon-cell inside root. Safe to
// call after each table re-render.
export function wireIconCells(root: HTMLElement) {
  if (!iconObserver) {
    iconObserver = new IntersectionObserver(
      (entries) => {
        for (const e of entries) {
          if (!e.isIntersecting) continue;
          const el = e.target as HTMLElement;
          iconObserver!.unobserve(el);
          hydrateIcon(el);
        }
      },
      { rootMargin: "150px" }
    );
  }
  root.querySelectorAll<HTMLElement>(".icon-cell:not([data-icon-done])").forEach((el) => {
    iconObserver!.observe(el);
  });
}

function hydrateIcon(el: HTMLElement) {
  const kind = el.dataset.iconKind!;
  const id = Number(el.dataset.iconId);
  el.dataset.iconDone = "1";
  const key = `${kind}/${id}`;
  const cached = iconCache.get(key);
  if (cached !== undefined) {
    applyIcon(el, cached);
    return;
  }
  getIcon(kind, id).then((url) => {
    iconCache.set(key, url);
    applyIcon(el, url);
  });
}

function applyIcon(el: HTMLElement, url: string | null) {
  if (url) {
    el.innerHTML = `<img class="icon-img" src="${url}" alt="" />`;
    el.classList.add("has-icon");
  } else {
    el.classList.add("no-icon"); // keep the placeholder glyph
  }
}
