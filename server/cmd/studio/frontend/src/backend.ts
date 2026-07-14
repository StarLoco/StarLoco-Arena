// Thin typed wrapper over the Wails-generated Go bindings.
//
// Wails writes JS bindings to ./wailsjs/go/main/App at `wails dev`/`build`
// time. We import them lazily so the frontend still type-checks/builds even
// before those files are generated (e.g. a plain `vite build`). At runtime
// inside the Wails webview the real bindings are present.

export interface Paths {
  dataDir: string;
  clientDir: string;
  dataDirValid: boolean;
  clientDirValid: boolean;
}

export interface Env {
  version: string;
  os: string;
}

// -------- Phase 1 data-model types (mirror internal/gamedata templates) --------

export interface EffectDef {
  ID: number;
  ParentType: string;
  ParentID: number;
  Duration: number[] | null;
  ActionID: number;
  IsCritical: boolean;
  Params: number[] | null;
  AreaShape: number;
  AreaSize: number[] | null;
  Targets: number[] | null;
  TriggersAfter: number[] | null;
  TriggersBefore: number[] | null;
  AffectedByLocalisation: boolean;
}

// EffectSemantic mirrors combat.EffectSemantic: one actionID decoded to human
// terms (kind/verb/stat/element/polarity) so the UI reads like a game tooltip.
export interface EffectSemantic {
  actionId: number;
  kind: string;
  verb: string;
  stat: string;
  element: string;
  polarity: "good" | "bad" | "neutral";
  note: string;
}

export interface EffectLore {
  effects: EffectSemantic[];
  areaShapes: Record<number, string>;
  targetConditions: Record<number, string>;
}

// One editable translation row (mirror of Go TransRow).
export interface TransRow {
  cat: number;
  id: number;
  label: string;
  kind: string;
  isDesc: boolean;
  value: string;
  alt: string;
  altLang: string;
}

export interface TranslationSet {
  lang: string;
  altLang: string;
  rows: TransRow[];
  error: string;
}

export interface TransEdit {
  cat: number;
  id: number;
  value: string;
}

export interface SummoningEdit {
  id: number;
  hp: number;
  ap: number;
  mp: number;
  gfx: number;
  spellId: number;
}
export interface CoachCardEdit {
  id: number;
  type: number;
  value: number;
  set: number;
}
export interface FighterCardEdit {
  id: number;
  type: number;
  value: number;
  scriptId: number;
  subType: number;
}
export interface StaticEffectEdit {
  id: number;
  scriptId: number;
  areaShapeId: number;
  maxExecutionCount: number;
  targetsToShow: number;
}
export interface EventEdit {
  id: number;
  useAutoDescription: boolean;
}

// -------- new-record creation payloads (mirror datacreate.go) --------
export interface SpellCreate {
  id: number;
  actionPointsCost: number;
  castFrequencyMaxPerPlayer: number;
  castFrequencyMaxPerTurn: number;
  castFrequencyMinInterval: number;
  castTestLineOfSight: boolean;
  castOnlyLine: boolean;
  rangeMin: number;
  rangeMax: number;
  price: number;
  aiTargetId: number;
  needFreeCell: boolean;
  scriptId: number;
  breedId: number;
  criterion: string;
  useAutoDescription: boolean;
}
export interface SummoningCreate {
  id: number;
  hp: number;
  ap: number;
  mp: number;
  gfx: number;
  spellId: number;
}
export interface CoachCardCreate {
  id: number;
  type: number;
  value: number;
  set: number;
}
export interface FighterCardCreate {
  id: number;
  type: number;
  value: number;
  scriptId: number;
  subType: number;
}

// EffectEditDTO mirrors the Go effect editor DTO (one editable effect row).
export interface EffectEditDTO {
  id: number;
  reserved: number;
  actionId: number;
  isCritical: boolean;
  duration: number[] | null;
  params: number[] | null;
  areaShape: number;
  areaSize: number[] | null;
  targets: number[] | null;
  triggersAfter: number[] | null;
  triggersBefore: number[] | null;
  affectedByLocalisation: boolean;
}

export interface PushFileStatus {
  name: string;
  jarEntry: string;
  localOk: boolean;
  inClient: boolean;
  differs: boolean;
  localHash: string;
  clientHash: string;
  error: string;
}
export interface PushStatus {
  clientJar: string;
  files: PushFileStatus[];
  error: string;
}

export interface BackupEntry {
  path: string;
  original: string;
  origName: string;
  stamp: string;
  bytes: number;
  area: string;
  restorable: boolean;
}
export interface BackupsResult {
  entries: BackupEntry[];
  error: string;
}

export interface Spell {
  ID: number;
  ActionPointsCost: number;
  CastFrequencyMaxPerPlayer: number;
  CastFrequencyMaxPerTurn: number;
  CastFrequencyMinInterval: number;
  CastTestLineOfSight: boolean;
  CastOnlyLine: boolean;
  RangeMin: number;
  RangeMax: number;
  Price: number;
  AiTargetID: number;
  NeedFreeCell: boolean;
  ScriptID: number;
  BreedID: number;
  Criterion: string;
  UseAutoDescription: boolean;
  Effects: EffectDef[] | null;
}

export interface CoachCard {
  ID: number;
  Type: number;
  Value: number;
  Set: number;
}

export interface FighterCard {
  ID: number;
  Type: number;
  Value: number;
  ScriptID: number;
  SubType: number;
  Effects: EffectDef[] | null;
}

export interface Summoning {
  ID: number;
  HP: number;
  AP: number;
  MP: number;
  Gfx: number;
  SpellID: number;
}

export interface StaticEffect {
  ID: number;
  ScriptID: number;
  AreaShapeID: number;
  AreaParams: number[] | null;
  ApplicationTriggers: number[] | null;
  UnapplicationTriggers: number[] | null;
  MaxExecutionCount: number;
  ApplicationTargets: number[] | null;
  UnapplicationTargets: number[] | null;
  TargetsToShow: number;
  EffectAreaType: string;
  DeactivationDelay: number[] | null;
  ApplicationCondition: number;
  Effects: EffectDef[] | null;
}

export interface GameEvent {
  ID: number;
  UseAutoDescription: boolean;
  Effects: EffectDef[] | null;
}

export interface DataCounts {
  spells: number;
  coachCards: number;
  fighterCards: number;
  summonings: number;
  staticEffects: number;
  events: number;
  error: string;
}

// -------- Phase 2 asset-browser types --------

export interface JarInfo {
  name: string;
  entryCount: number;
  sizeBytes: number;
  error: string;
}

export interface AssetEntry {
  path: string;
  name: string;
  ext: string;
  size: number;
  kind: "image" | "text" | "binary";
}

export interface AssetPreview {
  path: string;
  kind: "image" | "text" | "binary";
  mime: string;
  dataUrl: string;
  text: string;
  truncated: boolean;
  size: number;
}

type AppBindings = {
  GetPaths(): Promise<Paths>;
  SetDataDir(dir: string): Promise<Paths>;
  SetClientDir(dir: string): Promise<Paths>;
  GetEnv(): Promise<Env>;
  GetDataCounts(): Promise<DataCounts>;
  GetEffectLore(): Promise<EffectLore>;
  GetGameIcons(): Promise<Record<string, string>>;
  GetTranslations(): Promise<TranslationSet>;
  SaveTranslations(lang: string, edits: TransEdit[]): Promise<RepackResult>;
  SaveSummonings(edits: SummoningEdit[]): Promise<ExportResult>;
  SaveCoachCards(edits: CoachCardEdit[]): Promise<ExportResult>;
  SaveFighterCards(edits: FighterCardEdit[]): Promise<ExportResult>;
  SaveStaticEffects(edits: StaticEffectEdit[]): Promise<ExportResult>;
  SaveSpellEffects(spellId: number, effects: EffectEditDTO[]): Promise<ExportResult>;
  SaveFighterCardEffects(cardId: number, effects: EffectEditDTO[]): Promise<ExportResult>;
  SaveEventEffects(eventId: number, effects: EffectEditDTO[]): Promise<ExportResult>;
  SaveEvents(edits: EventEdit[]): Promise<ExportResult>;
  CreateSpell(c: SpellCreate): Promise<NewRecordResult>;
  CreateSummoning(c: SummoningCreate): Promise<NewRecordResult>;
  CreateCoachCard(c: CoachCardCreate): Promise<NewRecordResult>;
  CreateFighterCard(c: FighterCardCreate): Promise<NewRecordResult>;
  SuggestSpellID(): Promise<number>;
  SuggestSummoningID(): Promise<number>;
  SuggestCardIDs(): Promise<Record<string, number>>;
  GetPushStatus(): Promise<PushStatus>;
  PushDataToClient(names: string[]): Promise<RepackResult>;
  SaveJarText(jar: string, entry: string, content: string): Promise<RepackResult>;
  ReplaceJarEntry(jar: string, entry: string, base64Data: string): Promise<RepackResult>;
  GetSpellScript(scriptId: number): Promise<SpellScript>;
  SaveSpellScript(scriptId: number, source: string): Promise<RepackResult>;
  ListScriptIDs(): Promise<number[]>;
  ListBackups(): Promise<BackupsResult>;
  RestoreBackup(backupPath: string): Promise<ExportResult>;
  DeleteBackup(backupPath: string): Promise<void>;
  GetSpells(): Promise<Spell[]>;
  GetCoachCards(): Promise<CoachCard[]>;
  GetFighterCards(): Promise<FighterCard[]>;
  GetSummonings(): Promise<Summoning[]>;
  GetStaticEffects(): Promise<StaticEffect[]>;
  GetEvents(): Promise<GameEvent[]>;
  ListJars(): Promise<JarInfo[]>;
  ListJarEntries(jar: string): Promise<AssetEntry[]>;
  PreviewEntry(jar: string, entry: string): Promise<AssetPreview>;
  ExtractEntry(jar: string, entry: string, dest: string): Promise<void>;
  ListSprites(): Promise<SpriteInfo[]>;
  GetSprite(entry: string): Promise<SpriteImage>;
  GetSpriteByID(id: number): Promise<SpriteImage>;
  ListMaps(): Promise<MapSummary[]>;
  GetMap(id: number): Promise<MapData>;
  SaveSpells(edits: SpellEdit[]): Promise<ExportResult>;
  ListAnimations(): Promise<AnimationInfo[]>;
  GetAnimation(jar: string, entry: string): Promise<AnimationDoc>;
  GetAnimationPlayback(jar: string, entry: string, symbolID: number): Promise<AnimationPlayback>;
  ComposeFighter(base: LayerRef, equipment: LayerRef[]): Promise<AnimationPlayback>;
  GetSpecialCells(mapID: number): Promise<SpecialCellDTO[]>;
  SaveSpecialCells(mapID: number, cells: SpecialCellDTO[]): Promise<ExportResult>;
  ExportMapToClientJar(mapID: number): Promise<RepackResult>;
  GetNames(): Promise<NamesBundle>;
  GetIcon(kind: string, id: number): Promise<IconResult>;
  GetIcons(kind: string, ids: number[]): Promise<Record<string, string>>;
  GetLanguage(): Promise<LanguageState>;
  SetLanguage(code: string): Promise<LanguageState>;
  GetMapRender(id: number): Promise<MapRender>;
  GetMapGfxBatch(ids: number[]): Promise<MapGfx[]>;
  ListPaletteElements(): Promise<PaletteElement[]>;
  GetMapEditData(id: number): Promise<MapEditData>;
  SaveMapEditData(id: number, cells: MapEditCell[]): Promise<ExportResult[]>;
  PreviewMapRender(cells: MapEditCell[]): Promise<MapRender>;
  DuplicateMap(srcID: number, newID: number): Promise<void>;
  CreateBlankMap(newID: number, size: number): Promise<void>;
  DeleteMap(id: number): Promise<void>;
  GetMarkerElements(): Promise<MarkerSpec[]>;
  ListClientMaps(): Promise<ClientMapInfo[]>;
  ImportMapFromClient(id: number): Promise<number>;
};

export interface ClientMapInfo {
  id: number;
  chunkCount: number;
  alreadyLocal: boolean;
}

export interface MarkerSpec {
  label: string;
  color: string;
  element: MapEditElement;
}

// -------- Map builder types --------

export interface PaletteElement {
  id: number;
  kind: number;
  gfxId: number;
  defaultState: number;
  stateCount: number;
  walkable: boolean;
  height: number;
  piled: boolean;
}

export interface MapEditElement {
  elementId: number;
  state: number;
  groupId: number;
  paramsB64: string;
  paramTypes: number[];
  gfxId: number;
  kind: number;
}

export interface MapEditCell {
  x: number;
  y: number;
  levels: MapEditElement[][];
}

export interface MapEditData {
  id: number;
  minX: number;
  minY: number;
  maxX: number;
  maxY: number;
  cells: MapEditCell[];
}

// -------- Tile-render types (W4) --------

export interface MapDrawable {
  x: number;
  y: number;
  gfxId: number;
  originX: number;
  originY: number;
  flip: boolean;
  altitude: number;
  level: number;
  order: number;
  altitudeOrder: number;
}

export interface MapRender {
  id: number;
  minX: number;
  minY: number;
  maxX: number;
  maxY: number;
  drawables: MapDrawable[];
  gfxIds: number[];
}

export interface MapGfx {
  gfxId: number;
  width: number;
  height: number;
  dataUrl: string;
}

// -------- Names / icons / language types --------

export interface NamesBundle {
  language: string;
  available: boolean;
  spells: Record<string, string>;
  breeds: Record<string, string>;
  fighterCards: Record<string, string>;
  coachCards: Record<string, string>;
  events: Record<string, string>;
  effects: Record<string, string>;
  summons: Record<string, string>;
}

export interface LanguageOption {
  code: string;
  label: string;
}

export interface LanguageState {
  current: string;
  options: LanguageOption[];
}

export interface IconResult {
  url: string;
  found: boolean;
}

// -------- Phase 7 map-edit / export types --------

export interface SpecialCellDTO {
  x: number;
  y: number;
  type: string;
  cellBaseId: number;
}

export interface RepackResult {
  jar: string;
  backupPath: string;
  replaced: string[] | null;
  missing: string[] | null;
}

// -------- Phase 6 animation (.sba) types --------

export interface AnimationInfo {
  jar: string;
  path: string;
  name: string;
  bytes: number;
}

export interface AnimationTag {
  code: number;
  name: string;
  length: number;
  id: number;
  hasId: boolean;
}

export interface AnimationDoc {
  jar: string;
  path: string;
  signature: string;
  compressed: boolean;
  version: number;
  fileLength: number;
  tagCount: number;
  tags: AnimationTag[];
  counts: Record<string, number>;
}

// Sentinel symbol id meaning "the movie's own best default symbol".
export const ANIM_DEFAULT_SYMBOL = -999999;

export interface PlayerBitmap {
  id: number;
  width: number;
  height: number;
  dataUrl: string;
}

export interface PlayerOp {
  bitmapId: number;
  depth: number;
  // Fully-composed canvas affine (hot point baked in): setTransform(matrix)
  // then drawImage(img, 0, 0).
  matrix: [number, number, number, number, number, number]; // a,b,c,d,e,f
  colorMul: [number, number, number, number];
  colorAdd: [number, number, number, number];
}

export interface PlayerFrame {
  duration: number;
  ops: PlayerOp[];
}

export interface PlayerSymbol {
  id: number;
  linkage: string;
  kind: string;
  frameCount: number;
}

export interface AnimationPlayback {
  jar: string;
  path: string;
  signature: string;
  version: number;
  compressed: boolean;
  symbols: PlayerSymbol[];
  selected: number;
  loopCount: number;
  bitmaps: PlayerBitmap[];
  frames: PlayerFrame[];
  bounds: [number, number, number, number]; // minX, minY, maxX, maxY
}

export interface LayerRef {
  jar: string;
  path: string;
  symbolId: number;
}

// -------- Phase 5 edit/export types --------

export interface SpellEdit {
  id: number;
  actionPointsCost: number;
  rangeMin: number;
  rangeMax: number;
  castTestLineOfSight: boolean;
  castOnlyLine: boolean;
  needFreeCell: boolean;
  castFrequencyMaxPerTurn: number;
  castFrequencyMaxPerPlayer: number;
  castFrequencyMinInterval: number;
  price: number;
  criterion: string;
}

export interface ExportResult {
  target: string;
  backupPath: string;
  bytes: number;
}

// NewRecordResult mirrors datacreate.go: an ExportResult plus the created id.
export interface NewRecordResult extends ExportResult {
  newId: number;
}

// SpellScript mirrors scriptedit.go: a spell's resolved Lua source in data.jar.
export interface SpellScript {
  scriptId: number;
  entry: string;
  exists: boolean;
  source: string;
  bytes: number;
}

// -------- Phase 4 map types --------

export interface MapSummary {
  id: number;
  cellCount: number;
  isFight: boolean;
}

export interface MapCell {
  x: number;
  y: number;
  walkable: boolean;
  hasStanding: boolean;
  standingAlt: number;
  // renderAlt: topmost surface BASE altitude (walkable or not).
  renderAlt: number;
  // topAlt: the cell's VISUAL TOP FACE = max(surface base + height). This is
  // where the client anchors cell-highlight/start diamonds (getScreenTopY),
  // so overlays/markers must use this (not renderAlt) to sit ON the tile top.
  topAlt: number;
  blocksLos: boolean;
  surfaceCount: number;
}

export interface MarkerCell {
  x: number;
  y: number;
  side: number;
}

export interface MapData {
  id: number;
  minX: number;
  minY: number;
  maxX: number;
  maxY: number;
  cells: MapCell[];
  fightStart: MarkerCell[];
  coachStart: MarkerCell[];
}

// -------- Phase 3 sprite types --------

export interface SpriteInfo {
  id: number;
  path: string;
  name: string;
  bytes: number;
}

export interface SpriteImage {
  path: string;
  width: number;
  height: number;
  dataUrl: string;
}

let cached: AppBindings | null = null;

async function bindings(): Promise<AppBindings | null> {
  if (cached) return cached;
  try {
    // @ts-ignore - generated at wails build time, may not exist during bare vite build
    const mod = await import("../wailsjs/go/main/App");
    cached = mod as unknown as AppBindings;
    return cached;
  } catch {
    return null;
  }
}

// Fallback data used when running outside the Wails runtime (e.g. `vite
// preview` in a plain browser) so the UI is still explorable.
const FALLBACK_PATHS: Paths = {
  dataDir: "",
  clientDir: "",
  dataDirValid: false,
  clientDirValid: false,
};

export async function getPaths(): Promise<Paths> {
  const b = await bindings();
  return b ? b.GetPaths() : FALLBACK_PATHS;
}

export async function setDataDir(dir: string): Promise<Paths> {
  const b = await bindings();
  return b ? b.SetDataDir(dir) : { ...FALLBACK_PATHS, dataDir: dir };
}

export async function setClientDir(dir: string): Promise<Paths> {
  const b = await bindings();
  return b ? b.SetClientDir(dir) : { ...FALLBACK_PATHS, clientDir: dir };
}

export async function getEnv(): Promise<Env> {
  const b = await bindings();
  return b ? b.GetEnv() : { version: "dev (no runtime)", os: navigator.platform };
}

const NO_STORE = "no data directory (run inside the app with a valid data/ folder)";

export async function getDataCounts(): Promise<DataCounts> {
  const b = await bindings();
  if (!b)
    return {
      spells: 0,
      coachCards: 0,
      fighterCards: 0,
      summonings: 0,
      staticEffects: 0,
      events: 0,
      error: NO_STORE,
    };
  return b.GetDataCounts();
}

// Static semantic tables (no data dir needed). Falls back to an empty lore set
// outside the Wails runtime so the decoder degrades gracefully.
export async function getEffectLore(): Promise<EffectLore> {
  const b = await bindings();
  if (!b) return { effects: [], areaShapes: {}, targetConditions: {} };
  return b.GetEffectLore();
}

// getGameIcons returns authentic client UI icons (element/AP/MP/HP/init) as a
// name -> PNG data URL map, cropped from the theme atlases.
export async function getGameIcons(): Promise<Record<string, string>> {
  const b = await bindings();
  if (!b) return {};
  return b.GetGameIcons();
}

// getTranslations returns every editable content.* string for the active
// language (with the other language alongside as a reference).
export async function getTranslations(): Promise<TranslationSet> {
  const b = await bindings();
  if (!b) return { lang: "en", altLang: "fr", rows: [], error: "no runtime" };
  return b.GetTranslations();
}

// saveTranslations writes the edited strings back into i18n.jar (backup +
// atomic repack) for the given language.
export async function saveTranslations(
  lang: string,
  edits: TransEdit[]
): Promise<RepackResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveTranslations(lang, edits);
}

export async function saveSummonings(edits: SummoningEdit[]): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveSummonings(edits);
}
export async function saveCoachCards(edits: CoachCardEdit[]): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveCoachCards(edits);
}
export async function saveFighterCards(edits: FighterCardEdit[]): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveFighterCards(edits);
}
export async function saveStaticEffects(edits: StaticEffectEdit[]): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveStaticEffects(edits);
}

// saveSpellEffects replaces the effect list attached to a spell.
export async function saveSpellEffects(
  spellId: number,
  effects: EffectEditDTO[]
): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveSpellEffects(spellId, effects);
}

// saveFighterCardEffects replaces the effect list attached to a fighter card.
export async function saveFighterCardEffects(
  cardId: number,
  effects: EffectEditDTO[]
): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveFighterCardEffects(cardId, effects);
}

// saveEventEffects replaces the effect list attached to an event.
export async function saveEventEffects(
  eventId: number,
  effects: EffectEditDTO[]
): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveEventEffects(eventId, effects);
}

// saveEvents writes scalar event edits (auto-description toggle).
export async function saveEvents(edits: EventEdit[]): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveEvents(edits);
}

// --- new-record creation ---
export async function createSpell(c: SpellCreate): Promise<NewRecordResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.CreateSpell(c);
}
export async function createSummoning(c: SummoningCreate): Promise<NewRecordResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.CreateSummoning(c);
}
export async function createCoachCard(c: CoachCardCreate): Promise<NewRecordResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.CreateCoachCard(c);
}
export async function createFighterCard(c: FighterCardCreate): Promise<NewRecordResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.CreateFighterCard(c);
}
export async function suggestSpellID(): Promise<number> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SuggestSpellID();
}
export async function suggestSummoningID(): Promise<number> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SuggestSummoningID();
}
export async function suggestCardIDs(): Promise<Record<string, number>> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SuggestCardIDs();
}

// --- spell-script IDE ---
export async function getSpellScript(scriptId: number): Promise<SpellScript> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetSpellScript(scriptId);
}
export async function saveSpellScript(
  scriptId: number,
  source: string
): Promise<RepackResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveSpellScript(scriptId, source);
}
export async function listScriptIDs(): Promise<number[]> {
  const b = await bindings();
  if (!b) return [];
  return b.ListScriptIDs();
}

export async function getPushStatus(): Promise<PushStatus> {
  const b = await bindings();
  if (!b) return { clientJar: "", files: [], error: "no runtime" };
  return b.GetPushStatus();
}
export async function pushDataToClient(names: string[]): Promise<RepackResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.PushDataToClient(names);
}

// saveJarText overwrites a text entry (lua/xml/properties) in a client jar.
export async function saveJarText(
  jar: string,
  entry: string,
  content: string
): Promise<RepackResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveJarText(jar, entry, content);
}

// replaceJarEntry overwrites any entry with base64 bytes (images/fonts/binary).
export async function replaceJarEntry(
  jar: string,
  entry: string,
  base64Data: string
): Promise<RepackResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.ReplaceJarEntry(jar, entry, base64Data);
}

export async function listBackups(): Promise<BackupsResult> {
  const b = await bindings();
  if (!b) return { entries: [], error: "no runtime" };
  return b.ListBackups();
}
export async function restoreBackup(backupPath: string): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.RestoreBackup(backupPath);
}
export async function deleteBackup(backupPath: string): Promise<void> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.DeleteBackup(backupPath);
}

export async function getSpells(): Promise<Spell[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetSpells();
}
export async function getCoachCards(): Promise<CoachCard[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetCoachCards();
}
export async function getFighterCards(): Promise<FighterCard[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetFighterCards();
}
export async function getSummonings(): Promise<Summoning[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetSummonings();
}
export async function getStaticEffects(): Promise<StaticEffect[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetStaticEffects();
}
export async function getEvents(): Promise<GameEvent[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetEvents();
}

const NO_CLIENT = "no client directory (run inside the app with a valid client-compiled/ folder)";

export async function listJars(): Promise<JarInfo[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ListJars();
}
export async function listJarEntries(jar: string): Promise<AssetEntry[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ListJarEntries(jar);
}
export async function previewEntry(jar: string, entry: string): Promise<AssetPreview> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.PreviewEntry(jar, entry);
}
export async function extractEntry(jar: string, entry: string, dest: string): Promise<void> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ExtractEntry(jar, entry, dest);
}

export async function listSprites(): Promise<SpriteInfo[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ListSprites();
}
export async function getSprite(entry: string): Promise<SpriteImage> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.GetSprite(entry);
}

export async function listMaps(): Promise<MapSummary[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.ListMaps();
}
export async function getMap(id: number): Promise<MapData> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetMap(id);
}

export async function saveSpells(edits: SpellEdit[]): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveSpells(edits);
}

export async function listAnimations(): Promise<AnimationInfo[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ListAnimations();
}
export async function getAnimation(jar: string, entry: string): Promise<AnimationDoc> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.GetAnimation(jar, entry);
}
export async function getAnimationPlayback(
  jar: string,
  entry: string,
  symbolID: number = ANIM_DEFAULT_SYMBOL
): Promise<AnimationPlayback> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.GetAnimationPlayback(jar, entry, symbolID);
}
export async function composeFighter(
  base: LayerRef,
  equipment: LayerRef[]
): Promise<AnimationPlayback> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ComposeFighter(base, equipment);
}

export async function getSpecialCells(mapID: number): Promise<SpecialCellDTO[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetSpecialCells(mapID);
}
export async function saveSpecialCells(
  mapID: number,
  cells: SpecialCellDTO[]
): Promise<ExportResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveSpecialCells(mapID, cells);
}
export async function exportMapToClientJar(mapID: number): Promise<RepackResult> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ExportMapToClientJar(mapID);
}

// ---- names / icons / language ----

const EMPTY_NAMES: NamesBundle = {
  language: "en",
  available: false,
  spells: {},
  breeds: {},
  fighterCards: {},
  coachCards: {},
  events: {},
  effects: {},
  summons: {},
};

export async function getNames(): Promise<NamesBundle> {
  const b = await bindings();
  if (!b) return EMPTY_NAMES;
  try {
    return await b.GetNames();
  } catch {
    return EMPTY_NAMES;
  }
}

export async function getIcon(kind: string, id: number): Promise<string | null> {
  const b = await bindings();
  if (!b) return null;
  try {
    const res = await b.GetIcon(kind, id);
    return res && res.found ? res.url : null;
  } catch {
    return null;
  }
}

export async function getIcons(kind: string, ids: number[]): Promise<Record<string, string>> {
  const b = await bindings();
  if (!b) return {};
  try {
    return await b.GetIcons(kind, ids);
  } catch {
    return {};
  }
}

export async function getLanguage(): Promise<LanguageState> {
  const b = await bindings();
  if (!b) return { current: "en", options: [{ code: "en", label: "English" }] };
  return b.GetLanguage();
}

export async function setLanguage(code: string): Promise<LanguageState> {
  const b = await bindings();
  if (!b) return { current: code, options: [] };
  return b.SetLanguage(code);
}

export async function getMapRender(id: number): Promise<MapRender> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetMapRender(id);
}
export async function getMapGfxBatch(ids: number[]): Promise<MapGfx[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.GetMapGfxBatch(ids);
}

// ---- map builder ----

export async function listPaletteElements(): Promise<PaletteElement[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.ListPaletteElements();
}
export async function getMapEditData(id: number): Promise<MapEditData> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetMapEditData(id);
}
export async function saveMapEditData(id: number, cells: MapEditCell[]): Promise<ExportResult[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.SaveMapEditData(id, cells);
}
export async function previewMapRender(cells: MapEditCell[]): Promise<MapRender> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.PreviewMapRender(cells);
}
export async function duplicateMap(srcID: number, newID: number): Promise<void> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.DuplicateMap(srcID, newID);
}
export async function createBlankMap(newID: number, size: number): Promise<void> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.CreateBlankMap(newID, size);
}
export async function deleteMap(id: number): Promise<void> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.DeleteMap(id);
}
export async function getMarkerElements(): Promise<MarkerSpec[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_STORE);
  return b.GetMarkerElements();
}
export async function listClientMaps(): Promise<ClientMapInfo[]> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ListClientMaps();
}
export async function importMapFromClient(id: number): Promise<number> {
  const b = await bindings();
  if (!b) throw new Error(NO_CLIENT);
  return b.ImportMapFromClient(id);
}

export function inWails(): boolean {
  return typeof (window as any).runtime !== "undefined";
}
