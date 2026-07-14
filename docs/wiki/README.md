# Dofus Arena Wiki (local export)

This is a local Markdown export of the [Dofus Arena PBworks wiki](http://dofusarena.pbworks.com),
reorganized into topic folders. All 174 pages from the original wiki are included.
Internal links have been rewritten to relative Markdown links pointing at the new locations.

## Structure

### `breeds/` — the 12 character classes
Each breed has an overview page and a `spells/` subfolder with its class-specific spells.

- [Cra](breeds/Cra/Cra.md)
- [Ecaflip](breeds/Ecaflip/Ecaflip.md)
- [Eniripsa](breeds/Eniripsa/Eniripsa.md)
- [Enutrof](breeds/Enutrof/Enutrof.md)
- [Feca](breeds/Feca/Feca.md)
- [Iop](breeds/Iop/Iop.md)
- [Osamoda](breeds/Osamoda/Osamoda.md)
- [Panda](breeds/Panda/Panda.md)
- [Sacrier](breeds/Sacrier/Sacrier.md)
- [Sadida](breeds/Sadida/Sadida.md)
- [Sram](breeds/Sram/Sram.md)
- [Xelor](breeds/Xelor/Xelor.md)

### `mechanics/` — core game-mechanic lexicon
Generic game concepts not tied to one class, e.g. [Damage](mechanics/Damage.md),
[Range](mechanics/Range.md), [CriticalHits](mechanics/CriticalHits.md),
[Elements](mechanics/Elements.md), [Tiles](mechanics/Tiles.md), [Round](mechanics/Round.md),
[Initiative](mechanics/Initiative.md), and 17 more (24 pages total).

### `rules/` — overall game rules & meta-game
Team setup, cards, ladder, game modes: [Rules](rules/Rules.md), [Team](rules/Team.md),
[BonusCards](rules/BonusCards.md), [EventCards](rules/EventCards.md), [Ladder](rules/Ladder.md),
[Modes](rules/Modes.md), [Combos](rules/Combos.md), [Summons](rules/Summons.md),
[Spells](rules/Spells.md) (full cross-class spell index), [Classes](rules/Classes.md),
[Characters](rules/Characters.md), [Skills](rules/Skills.md), [Maps](rules/Maps.md),
[Dodge](rules/Dodge.md), [2vs2](rules/2vs2.md).

### `items/` — equipment
[Items](items/Items.md) (weapons, pets, cloaks, hats, dofuses) and
[weapons/Fists](items/weapons/Fists.md) (unarmed combat, not tied to a class).

### `meta/` — site meta / non-game-mechanic pages
[FrontPage](meta/FrontPage.md), [FAQ](meta/FAQ.md), [Bugs](meta/Bugs.md),
[To-Do](meta/To-Do.md), [Shankals-page](meta/Shankals-page.md) (a user's personal page).

## Notes on the export

- One page from the original wiki (`TeamLeader`) had been deleted by its editors before this
  export was taken (confirmed via the site's RSS history) and therefore has no file here;
  the one remaining reference to it (in `rules/Rules.md`) is kept as plain text.
- A handful of pages whose names looked like vandalism/anagrams (`Chamrak`, `Karcham`, `Karzam`,
  `Pandatak`) turned out to be legitimate Panda-class spells and are filed normally under
  `breeds/Panda/spells/`.
- Content is preserved as close to the original article text as possible; PBworks site chrome
  (navigation, login/footer links, edit buttons) was stripped during export.
