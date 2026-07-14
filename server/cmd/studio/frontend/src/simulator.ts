// simulator.ts -- an interactive spell damage/heal simulator. It replicates the
// server's exact ComputeHPLoss formula (internal/combat/damage.go) so you can
// see a spell's real output against a target's resistances, tweak caster damage
// stats and target resists with sliders, and watch min/avg/max update live.
//
//   value      = base + casterDmg + casterElemDmg - targetElemRes - targetRes
//   modPercent = casterDmg% + casterElemDmg% - targetRes% - targetElemRes%
//   final      = value * (100 + modPercent) / 100   (clamped >= 0)
//
// Physical damage ignores every element/flat modifier (base value only), per
// HPLoss.computeValue()'s element switch (no PHYSICAL case).

import type { EffectDef } from "./backend";
import { gameIcon } from "./effectlore";
import { t } from "./i18n";

// A minimal caster/target stat sheet the simulator lets you edit.
export interface SimStats {
  dmg: number; // flat Dmg (all-element)
  dmgElem: number; // flat element-specific Dmg
  dmgPct: number; // Damage %
  dmgElemPct: number; // element Damage %
  res: number; // flat Res (all-element)
  resElem: number; // flat element Res
  resPct: number; // Resistance %
  resElemPct: number; // element Resistance %
}

const DEFAULT_STATS: SimStats = {
  dmg: 0,
  dmgElem: 0,
  dmgPct: 0,
  dmgElemPct: 0,
  res: 0,
  resElem: 0,
  resPct: 0,
  resElemPct: 0,
};

// param range: [] -> (0,0); [v] -> (v,v); [dice,faces,mod] -> (dice+mod, dice*faces+mod)
function baseRange(params: number[] | null): [number, number] {
  if (!params || params.length === 0) return [0, 0];
  if (params.length === 1) return [params[0], params[0]];
  const [dice, faces, mod] = params;
  if (dice <= 0 || faces <= 0) return [mod, mod];
  return [dice * 1 + mod, dice * faces + mod];
}

const ELEMENTS = ["fire", "water", "wind", "earth", "physical"] as const;
type Elem = (typeof ELEMENTS)[number];

// isDamageAction returns the element for HP-loss/leech actions, or null.
function damageElement(actionId: number): Elem | null {
  // ids 1-5 = HP loss (phys/fire/earth/water/wind), 6-10 = leech, 61 poison.
  const map: Record<number, Elem> = {
    1: "physical", 2: "fire", 3: "earth", 4: "water", 5: "wind",
    6: "physical", 7: "fire", 8: "earth", 9: "water", 10: "wind",
    61: "physical",
  };
  return map[actionId] ?? null;
}

function computeDamage(base: number, elem: Elem, c: SimStats, t: SimStats): number {
  if (elem === "physical") return Math.max(0, base);
  let value = base + c.dmg + c.dmgElem - t.resElem - t.res;
  const modPct = c.dmgPct + c.dmgElemPct - t.resPct - t.resElemPct;
  value = (value * (100 + modPct)) / 100;
  return Math.max(0, Math.round(value));
}

function esc(v: unknown): string {
  return String(v).replace(
    /[&<>"]/g,
    (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c] as string)
  );
}

// firstDamageEffect returns the first HP-damaging effect of a spell (the one the
// simulator models), preferring non-critical.
function firstDamageEffect(effects: EffectDef[] | null): EffectDef | null {
  if (!effects) return null;
  const dmg = effects.filter((e) => damageElement(e.ActionID) !== null);
  if (dmg.length === 0) return null;
  return dmg.find((e) => !e.IsCritical) ?? dmg[0];
}

// simulatorHTML renders the panel for a spell (given its effects). Returns "" if
// the spell has no damaging effect to simulate.
export function simulatorHTML(effects: EffectDef[] | null): string {
  const eff = firstDamageEffect(effects);
  if (!eff) return "";
  const elem = damageElement(eff.ActionID)!;
  const critEff = effects?.find((e) => e.IsCritical && damageElement(e.ActionID) !== null);

  const slider = (id: string, label: string, min: number, max: number, val: number) =>
    `<label class="sim-slider">
       <span>${esc(label)}</span>
       <input type="range" data-sim="${id}" min="${min}" max="${max}" value="${val}" step="1" />
       <b data-simval="${id}">${val}</b>
     </label>`;

  const eIco = gameIcon(elem === "physical" ? "" : elem, 16);

  return `
    <div class="sim" data-elem="${elem}"
         data-base='${JSON.stringify(baseRange(eff.Params))}'
         data-crit='${critEff ? JSON.stringify(baseRange(critEff.Params)) : ""}'>
      <div class="sim-head">
        <span class="sim-title">${eIco} Damage simulator</span>
        <span class="sim-sub">${esc(elem)} \u00B7 live formula</span>
        <button class="sim-reset" data-sim-reset>reset</button>
      </div>
      <div class="sim-readout">
        <div class="sim-big">
          <div class="sim-num" data-sim-min>0</div>
          <span>min</span>
        </div>
        <div class="sim-big sim-avg">
          <div class="sim-num" data-sim-avg>0</div>
          <span>average</span>
        </div>
        <div class="sim-big">
          <div class="sim-num" data-sim-max>0</div>
          <span>max</span>
        </div>
        ${
          critEff
            ? `<div class="sim-big sim-crit">
                 <div class="sim-num" data-sim-crit>0</div>
                 <span>crit max</span>
               </div>`
            : ""
        }
      </div>
      <div class="sim-bar"><div class="sim-bar-fill" data-sim-bar></div></div>
      <div class="sim-cols">
        <div class="sim-col sim-attacker">
          <div class="sim-col-h">${t("sim.attacker")}</div>
          ${slider("dmg", "flat Dmg", 0, 100, 0)}
          ${slider("dmgElem", "flat elem Dmg", 0, 100, 0)}
          ${slider("dmgPct", "Dmg %", 0, 300, 0)}
          ${slider("dmgElemPct", "elem Dmg %", 0, 300, 0)}
        </div>
        <div class="sim-col sim-defender">
          <div class="sim-col-h">${t("sim.defender")}</div>
          ${slider("res", "flat Res", 0, 100, 0)}
          ${slider("resElem", "flat elem Res", 0, 100, 0)}
          ${slider("resPct", "Res %", 0, 100, 0)}
          ${slider("resElemPct", "elem Res %", 0, 100, 0)}
        </div>
      </div>
      <div class="sim-note">${t("sim.note")}</div>
    </div>`;
}

// wireSimulator hooks up the sliders inside a rendered simulator panel (call
// after inserting simulatorHTML into the DOM).
export function wireSimulator(root: HTMLElement): void {
  root.querySelectorAll<HTMLElement>(".sim").forEach((panel) => {
    const elem = panel.dataset.elem as Elem;
    const base = JSON.parse(panel.dataset.base || "[0,0]") as [number, number];
    const critStr = panel.dataset.crit || "";
    const crit = critStr ? (JSON.parse(critStr) as [number, number]) : null;

    const stats = (): { c: SimStats; t: SimStats } => {
      const get = (id: string) =>
        Number(panel.querySelector<HTMLInputElement>(`[data-sim="${id}"]`)?.value ?? 0);
      const c: SimStats = {
        ...DEFAULT_STATS,
        dmg: get("dmg"),
        dmgElem: get("dmgElem"),
        dmgPct: get("dmgPct"),
        dmgElemPct: get("dmgElemPct"),
      };
      const t: SimStats = {
        ...DEFAULT_STATS,
        res: get("res"),
        resElem: get("resElem"),
        resPct: get("resPct"),
        resElemPct: get("resElemPct"),
      };
      return { c, t };
    };

    const update = () => {
      const { c, t } = stats();
      const dmin = computeDamage(base[0], elem, c, t);
      const dmax = computeDamage(base[1], elem, c, t);
      const davg = Math.round((dmin + dmax) / 2);
      setNum(panel, "min", dmin);
      setNum(panel, "avg", davg);
      setNum(panel, "max", dmax);
      if (crit) setNum(panel, "crit", computeDamage(crit[1], elem, c, t));
      // bar scales avg against a 0-100 reference for a quick visual pulse.
      const bar = panel.querySelector<HTMLElement>("[data-sim-bar]");
      if (bar) bar.style.width = `${Math.min(100, (davg / 100) * 100)}%`;
      // reflect each slider's live value
      panel.querySelectorAll<HTMLInputElement>("[data-sim]").forEach((sl) => {
        const out = panel.querySelector(`[data-simval="${sl.dataset.sim}"]`);
        if (out) out.textContent = sl.value;
      });
    };

    panel.querySelectorAll<HTMLInputElement>("[data-sim]").forEach((sl) => {
      sl.addEventListener("input", (e) => {
        e.stopPropagation();
        update();
      });
    });
    panel.querySelector<HTMLElement>("[data-sim-reset]")?.addEventListener("click", (e) => {
      e.stopPropagation();
      panel.querySelectorAll<HTMLInputElement>("[data-sim]").forEach((sl) => (sl.value = "0"));
      update();
    });
    // prevent the drawer from collapsing when dragging a slider
    panel.addEventListener("mousedown", (e) => e.stopPropagation());
    panel.addEventListener("click", (e) => e.stopPropagation());

    update();
  });
}

function setNum(panel: HTMLElement, key: string, val: number): void {
  const el = panel.querySelector<HTMLElement>(`[data-sim-${key}]`);
  if (!el) return;
  if (el.textContent !== String(val)) {
    el.textContent = String(val);
    el.classList.remove("sim-pop");
    void el.offsetWidth; // reflow to restart the animation
    el.classList.add("sim-pop");
  }
}
