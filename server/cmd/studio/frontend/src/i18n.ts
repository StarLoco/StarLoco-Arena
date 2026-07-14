// i18n.ts -- application UI translations (separate from the game-data i18n that
// comes from the client's texts_<lang>.properties). This translates the
// studio's own chrome: nav labels, headings, buttons, status. The active
// language mirrors the game-data language toggle so the whole app switches at
// once.
//
// t(key) returns the string for the current UI language, falling back to the
// English source if a key is missing in the target language. Keys are dotted
// (e.g. "nav.spells", "deploy.push"). Interpolation: t("x", {n: 5}) replaces
// {n} in the string.

type Lang = "en" | "fr";

let current: Lang = "en";

// setUILang switches the active UI language ("en" | "fr"; unknown -> "en").
export function setUILang(code: string): void {
  current = code === "fr" ? "fr" : "en";
}

export function uiLang(): Lang {
  return current;
}

// t looks up a key in the current language table (English fallback), applying
// {placeholder} interpolation from vars.
export function t(key: string, vars?: Record<string, string | number>): string {
  const table = current === "fr" ? FR : EN;
  let s = table[key] ?? EN[key] ?? key;
  if (vars) {
    for (const [k, v] of Object.entries(vars)) {
      s = s.replace(new RegExp(`\\{${k}\\}`, "g"), String(v));
    }
  }
  return s;
}

// EN is the source table (also the fallback). FR overrides the ones that differ.
const EN: Record<string, string> = {
  // brand + shell
  "app.subtitle": "Data & Asset Explorer",
  "status.data.loaded": "data loaded",
  "status.data.missing": "data not found",
  "status.client.detected": "client detected",
  "status.client.missing": "client not found",
  "status.unsaved": "{n} unsaved change",
  "status.unsaved.plural": "{n} unsaved changes",

  // nav sections
  "section.Studio": "Studio",
  "section.Game Data": "Game Data",
  "section.Assets": "Assets",
  "section.World": "World",

  // nav items (keyed by nav id)
  "nav.overview": "Overview",
  "nav.diagnostics": "Diagnostics",
  "nav.deploy": "Deploy",
  "nav.spellbook": "Spellbook",
  "nav.spells": "Spells",
  "nav.cards": "Cards",
  "nav.summonings": "Summonings",
  "nav.staticEffects": "Static Effects",
  "nav.events": "Events",
  "nav.scripts": "Lua Scripts",
  "nav.translations": "Translations",
  "nav.assets": "Asset Browser",
  "nav.sprites": "Sprites (TGA)",
  "nav.animations": "Animations",
  "nav.maps": "Maps",
  "nav.builder": "Map Builder",

  // overview
  "overview.jumpIn": "Jump in",
  "overview.preferences": "Preferences",
  "overview.prefLang": "Language used for names & descriptions (from the client's i18n).",
  "overview.language": "Language",
  "overview.records": "records",
  "overview.stat.spells": "spells",
  "overview.stat.cards": "cards",
  "overview.stat.summonings": "summonings",
  "overview.stat.staticEffects": "static effects",
  "overview.stat.events": "events",
  "overview.stat.scripts": "Lua scripts",
  "overview.health.checking": "Checking data integrity\u2026",
  "overview.health.clean": "Data integrity: all clear",
  "overview.health.openDiag": "Open Diagnostics",

  // common table / actions
  "common.search": "Search {n} rows\u2026",
  "common.export": "Export CSV",
  "common.save": "Save",
  "common.revert": "Revert",
  "common.cancel": "Cancel",
  "common.close": "Close",
  "common.loading": "Loading\u2026",
  "common.rerun": "Re-run",
  "common.records": "records",
  "common.couldNotLoad": "Could not load {what}.",
  "common.noMatch": "No matching rows",

  // view page headings (title reuses nav.<id>; these are the subtitles)
  "view.spellbook.sub": "spells by class",
  "view.spellbook.count": "{spells} spells \u00B7 {breeds} classes",
  "view.spellbook.generic": "Generic / no class",
  "view.cards.sub": "cards.dat \u00B7 coach + fighter cards",
  "view.cards.count": "{coach} coach \u00B7 {fighter} fighter \u00B7 cards.dat",
  "cards.newFighter": "New fighter card",
  "cards.newCoach": "New coach card",
  "common.newSpell": "New spell",
  "common.newSummon": "New summoning",
  "view.maps.sub": "isometric viewer",
  "view.animations.sub": ".sba (Sprite Byte Animation) \u00B7 player",
  "view.builder.sub": "create & edit fight maps",
  "view.translations.sub": "edit the client's i18n strings",
  "view.translations.count": "{n} strings \u00B7 editing {lang}",
  "view.scripts.sub": "data.jar \u00B7 scripts/*.lua",
  "view.scripts.count": "{n} scripts \u00B7 {used} referenced \u00B7 data.jar",
  "view.scripts.loading": "Loading scripts\u2026",
  "view.assets.sub": "client jars \u00B7 browse & preview",
  "view.sprites.title": "Sprites",
  "view.sprites.sub": "gfx.jar \u00B7 TGA \u2192 PNG",
  "view.sprites.count": "{n} tiles \u00B7 gfx.jar \u00B7 TGA \u2192 PNG",

  // translations editor chrome
  "tr.search": "Search names, ids, values\u2026",
  "tr.missingOnly": "missing only",
  "tr.changedOnly": "changed only",
  "tr.noChanges": "No unsaved changes",
  "tr.unsaved": "{n} unsaved",
  "tr.saveTo": "Save to i18n.jar",
  "tr.string": "STRING",
  "tr.ref": "{lang} (REF)",
  "tr.editing": "{lang} (EDITING)",

  // translations facet / category labels
  "cat.all": "all",
  "cat.fighterCards": "Fighter cards",
  "cat.spells": "Spells",
  "cat.classes": "Classes",
  "cat.effects": "Effect labels",
  "cat.summons": "Summons",
  "cat.events": "Events",
  "cat.coachCards": "Coach cards",

  // deploy
  "deploy.title": "Deploy",
  "deploy.subtitle": "push edited data into the compiled client",
  "deploy.upToDate": "The client is up to date with your edits",
  "deploy.push": "Push {n} to client",
  "deploy.reviewAll": "Review all {n} pending changes",
  "deploy.changelog": "Changelog",

  // diagnostics
  "diag.title": "Diagnostics",
  "diag.subtitle": "data-integrity validation",
  "diag.validating": "Validating game data\u2026",
  "diag.checked": "{n} records checked",
  "diag.allClear": "All clear.",
};

const FR: Record<string, string> = {
  "app.subtitle": "Explorateur de donn\u00E9es et ressources",
  "status.data.loaded": "donn\u00E9es charg\u00E9es",
  "status.data.missing": "donn\u00E9es introuvables",
  "status.client.detected": "client d\u00E9tect\u00E9",
  "status.client.missing": "client introuvable",
  "status.unsaved": "{n} modification non enregistr\u00E9e",
  "status.unsaved.plural": "{n} modifications non enregistr\u00E9es",

  "section.Studio": "Studio",
  "section.Game Data": "Donn\u00E9es du jeu",
  "section.Assets": "Ressources",
  "section.World": "Monde",

  "nav.overview": "Aper\u00E7u",
  "nav.diagnostics": "Diagnostics",
  "nav.deploy": "D\u00E9ployer",
  "nav.spellbook": "Grimoire",
  "nav.spells": "Sorts",
  "nav.cards": "Cartes",
  "nav.summonings": "Invocations",
  "nav.staticEffects": "Effets statiques",
  "nav.events": "\u00C9v\u00E9nements",
  "nav.scripts": "Scripts Lua",
  "nav.translations": "Traductions",
  "nav.assets": "Explorateur de ressources",
  "nav.sprites": "Sprites (TGA)",
  "nav.animations": "Animations",
  "nav.maps": "Cartes du monde",
  "nav.builder": "\u00C9diteur de cartes",

  "overview.jumpIn": "Acc\u00E8s rapide",
  "overview.preferences": "Pr\u00E9f\u00E9rences",
  "overview.prefLang": "Langue utilis\u00E9e pour les noms et descriptions (depuis l'i18n du client).",
  "overview.language": "Langue",
  "overview.records": "enregistrements",
  "overview.stat.spells": "sorts",
  "overview.stat.cards": "cartes",
  "overview.stat.summonings": "invocations",
  "overview.stat.staticEffects": "effets statiques",
  "overview.stat.events": "\u00E9v\u00E9nements",
  "overview.stat.scripts": "scripts Lua",
  "overview.health.checking": "V\u00E9rification de l'int\u00E9grit\u00E9\u2026",
  "overview.health.clean": "Int\u00E9grit\u00E9 des donn\u00E9es : tout est bon",
  "overview.health.openDiag": "Ouvrir les diagnostics",

  "common.search": "Rechercher dans {n} lignes\u2026",
  "common.export": "Exporter CSV",
  "common.save": "Enregistrer",
  "common.revert": "Annuler",
  "common.cancel": "Annuler",
  "common.close": "Fermer",
  "common.loading": "Chargement\u2026",
  "common.rerun": "Relancer",
  "common.records": "enregistrements",
  "common.couldNotLoad": "Impossible de charger {what}.",
  "common.noMatch": "Aucune ligne correspondante",

  "view.spellbook.sub": "sorts par classe",
  "view.spellbook.count": "{spells} sorts \u00B7 {breeds} classes",
  "view.spellbook.generic": "G\u00E9n\u00E9rique / sans classe",
  "view.cards.sub": "cards.dat \u00B7 cartes coach + combattant",
  "view.cards.count": "{coach} coach \u00B7 {fighter} combattant \u00B7 cards.dat",
  "cards.newFighter": "Nouvelle carte combattant",
  "cards.newCoach": "Nouvelle carte coach",
  "common.newSpell": "Nouveau sort",
  "common.newSummon": "Nouvelle invocation",
  "view.maps.sub": "vue isom\u00E9trique",
  "view.animations.sub": ".sba (Sprite Byte Animation) \u00B7 lecteur",
  "view.builder.sub": "cr\u00E9er et modifier des cartes de combat",
  "view.translations.sub": "modifier les cha\u00EEnes i18n du client",
  "view.translations.count": "{n} cha\u00EEnes \u00B7 \u00E9dition {lang}",
  "view.scripts.sub": "data.jar \u00B7 scripts/*.lua",
  "view.scripts.count": "{n} scripts \u00B7 {used} r\u00E9f\u00E9renc\u00E9s \u00B7 data.jar",
  "view.scripts.loading": "Chargement des scripts\u2026",
  "view.assets.sub": "jars du client \u00B7 parcourir et pr\u00E9visualiser",
  "view.sprites.title": "Sprites",
  "view.sprites.sub": "gfx.jar \u00B7 TGA \u2192 PNG",
  "view.sprites.count": "{n} tuiles \u00B7 gfx.jar \u00B7 TGA \u2192 PNG",

  "tr.search": "Rechercher noms, ids, valeurs\u2026",
  "tr.missingOnly": "manquantes uniquement",
  "tr.changedOnly": "modifi\u00E9es uniquement",
  "tr.noChanges": "Aucune modification",
  "tr.unsaved": "{n} non enregistr\u00E9es",
  "tr.saveTo": "Enregistrer dans i18n.jar",
  "tr.string": "CHA\u00CENE",
  "tr.ref": "{lang} (R\u00C9F)",
  "tr.editing": "{lang} (\u00C9DITION)",

  "cat.all": "toutes",
  "cat.fighterCards": "Cartes combattant",
  "cat.spells": "Sorts",
  "cat.classes": "Classes",
  "cat.effects": "Libell\u00E9s d'effets",
  "cat.summons": "Invocations",
  "cat.events": "\u00C9v\u00E9nements",
  "cat.coachCards": "Cartes coach",

  "deploy.title": "D\u00E9ployer",
  "deploy.subtitle": "envoyer les donn\u00E9es modifi\u00E9es vers le client compil\u00E9",
  "deploy.upToDate": "Le client est \u00E0 jour avec vos modifications",
  "deploy.push": "Envoyer {n} vers le client",
  "deploy.reviewAll": "Examiner les {n} modifications en attente",
  "deploy.changelog": "Journal",

  "diag.title": "Diagnostics",
  "diag.subtitle": "validation de l'int\u00E9grit\u00E9 des donn\u00E9es",
  "diag.validating": "Validation des donn\u00E9es du jeu\u2026",
  "diag.checked": "{n} enregistrements v\u00E9rifi\u00E9s",
  "diag.allClear": "Tout est bon.",
};
