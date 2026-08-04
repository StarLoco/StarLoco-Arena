# Range

Range in Dofus Arena is measured in tiles, and are not the euclidian distance but rather number of tiles horizontally plus vertically. Spells and weapons have both a minimum and maximum range. If (and only if) a spell has a maximum range larger than 1, it can be boosted by certain [items](../items/Items.md), [spells](../rules/Spells.md), [special tiles](./Tiles.md), [bonus](../rules/BonusCards.md)-, and [event](../rules/EventCards.md)-cards.

Note:

* A boosted range only boost the maximum value and not the minimum one.
* Some [spells](../rules/Spells.md) places a debuff which decreases the range of characters. This only decreases the maximum value so in the special case where the minimum and maximum value is the same (for example [Bubble](../breeds/Feca/spells/Bubble.md)), the range remain unaltered.
* No spell with maximum range 0 or 1 can get boosted nor debuffed.

**Items that increase range**

| Item | Cost | Bonus |
|---|---|---|
| Moon | 600K | +1 MP, **+1 range**, +60 initiative |
| Wabbit | 250K | **+1 Range**, +30 initiative |
| Moskitogalurette | 250K | **+1 range**, +10 HP |

**Spells that affect range**

| Spell | Kamas | AP | Target | Range | Duration | Recast | Normal Effect | Critical Effect |
|---|---|---|---|---|---|---|---|---|
| [Bat's Eye](../breeds/Cra/spells/BatsEye.md) | 100 | 1 | Enemy | 1 to 6 | 1 Round | Once Each | **-1 range** | **-2 range** |
| [Eagle Eye](../breeds/Cra/spells/EagleEye.md) | 200 | 4 | Ally | 0 to 3 | Game | No | **+1 range** | **+2 range** |
| [Visual Torment](../breeds/Cra/spells/VisualTorment.md) | 300 | 4 | All Enemies | - | 2 Rounds | 3 Rounds | **-1 range** | **-2 range** |
| [Sacrier's Foot](../breeds/Sacrier/spells/SacriersFoot.md) | 150 | 3 | Enemy | 1 to 1 | 1 Round | Yes | 7 (Earth) dmg, **-1 range** | 12 (Earth) dmg, **-1 range** |
