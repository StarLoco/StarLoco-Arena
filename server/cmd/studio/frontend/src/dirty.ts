// dirty.ts -- a tiny global registry of unsaved edits across every editor in the
// studio. Each editor reports a stable key (e.g. "spell:141", "script:104",
// "trans:en") as dirty while its draft diverges from the saved value, and clean
// once saved, reverted, or matching again. The shell subscribes to render a
// top-bar "N unsaved changes" indicator and to guard navigation / window close.
//
// Keys are per-record so re-rendering a table (which happens on every row
// interaction) doesn't lose or duplicate dirty state: an editor simply re-reports
// its own key each time it mounts.

const dirtyKeys = new Set<string>();
type Listener = (count: number) => void;
const listeners = new Set<Listener>();

function notify() {
  const c = dirtyKeys.size;
  for (const l of listeners) l(c);
}

// markDirty flags key as having unsaved changes.
export function markDirty(key: string): void {
  if (!dirtyKeys.has(key)) {
    dirtyKeys.add(key);
    notify();
  }
}

// markClean clears key (saved / reverted / back to original).
export function markClean(key: string): void {
  if (dirtyKeys.delete(key)) notify();
}

// setDirty is a convenience for editors that recompute dirtiness on each input.
export function setDirty(key: string, dirty: boolean): void {
  if (dirty) markDirty(key);
  else markClean(key);
}

// isAnyDirty reports whether any editor has unsaved changes.
export function isAnyDirty(): boolean {
  return dirtyKeys.size > 0;
}

// dirtyCount returns the number of editors with unsaved changes.
export function dirtyCount(): number {
  return dirtyKeys.size;
}

// clearAllDirty drops all tracked dirty state (used when the user confirms
// discarding changes on navigation, so the abandoned view's keys don't linger).
export function clearAllDirty(): void {
  if (dirtyKeys.size > 0) {
    dirtyKeys.clear();
    notify();
  }
}

// onDirtyChange subscribes to dirty-count changes; returns an unsubscribe fn.
export function onDirtyChange(fn: Listener): () => void {
  listeners.add(fn);
  return () => listeners.delete(fn);
}

// confirmDiscardIfDirty asks the user to confirm leaving unsaved changes. Returns
// true if it's safe to proceed (nothing dirty, or the user chose to discard).
export function confirmDiscardIfDirty(): boolean {
  if (!isAnyDirty()) return true;
  const n = dirtyCount();
  const ok = window.confirm(
    `You have ${n} unsaved change${n === 1 ? "" : "s"}. Leave and discard ${
      n === 1 ? "it" : "them"
    }?`
  );
  if (ok) clearAllDirty();
  return ok;
}
