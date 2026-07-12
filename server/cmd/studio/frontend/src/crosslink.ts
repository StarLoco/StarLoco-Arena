// crosslink.ts -- clickable entity references that jump between views. Turns
// the data set into a connected knowledge base: a spell's summon effect links
// to the Summonings view filtered on that creature, a card links to its icon,
// etc. Any chip dispatches the global `studio:navigate` bus (handled in main.ts).

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// link renders a pill that navigates to `view` pre-filtered by `query`.
export function link(view: string, query: string, text: string, icon = "\u2197"): string {
  return `<button class="xlink" data-xview="${esc(view)}" data-xquery="${esc(query)}" title="Go to ${esc(
    view
  )}">${esc(text)} <span class="xlink-ico">${icon}</span></button>`;
}

// wireCrosslinks attaches the click handler to every .xlink inside root. Safe to
// call repeatedly (idempotent via a data flag).
export function wireCrosslinks(root: HTMLElement): void {
  root.querySelectorAll<HTMLElement>(".xlink").forEach((el) => {
    if (el.dataset.xwired) return;
    el.dataset.xwired = "1";
    el.addEventListener("click", (e) => {
      e.stopPropagation();
      window.dispatchEvent(
        new CustomEvent("studio:navigate", {
          detail: { view: el.dataset.xview, query: el.dataset.xquery },
        })
      );
    });
  });
}
