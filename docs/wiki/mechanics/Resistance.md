# Resistance

Each character has a resistance value for each [element](./Elements.md). As default this value is 0, but this can be modified by [items](../items/Items.md) and [spells](../rules/Spells.md).

A character's resistance simply absorb incoming damage of a specific [element](./Elements.md) every time the character is hit. Assume for example you have 80 HP and +2 resistance to all resistances. If someone hit you with a 10 dmg spell, you will be hit for 8 dmg instead. In this example it would take opponent 10 hits instead of 8 to take you down. It is as if you had 20 extra HP.

**Notes**
- Resistance is especially useful together with [Weakness](../breeds/Feca/spells/Weakness.md) which will reduce the damage of opponent.
- [Healing spells](../rules/Spells.md#EniripsasHand) are more potent to use with characters with resistance. (In example above, healing 8 would be as healing 10.)
- Resistance is more useful against opponents with many weak spells than against opponents with few high damage spells.

## Items that affect resistance

| Item | Cost | Bonus |
|---|---|---|
| Caralining | 150K | +4 water resistance |
| Clint | 150K | +4 air resistance |
| Crackler Helmet | 150K | +4 earth resistance |
| Dragolining | 200K | +2 to all resistances |
| Gobball Lining | 350K | +1 AP, +2 to all dmg, +1 to all resistances |
| Mush Lining | 150K | +4 fire resistance |

## Spells that affect resistance

| Spell | Kamas | AP | Target | Range | Duration | Recast | Effect |
|---|---|---|---|---|---|---|---|
| [Earth and Water Armor](../breeds/Feca/spells/EarthAndWaterArmor.md) | 100 | 3 | Self | - | Game | No | +4 earth and +4 water resistance |
| [Feca Shield](../breeds/Feca/spells/FecaShield.md) | 200 | 5 | Ally | 0 to 1 | Game | No | +2 resistance of all elements |
| [Wind and Fire Armor](../breeds/Feca/spells/WindAndFireArmor.md) | 100 | 3 | Self | - | Game | No | +4 air and +4 fire resistance |
| [Counter](../breeds/Xelor/spells/Counter.md) (*) | 100 | 2 | Self | - | Game | No | +3 dmg reflect |

(*) [Counter](../breeds/Xelor/spells/Counter.md) gives damage reflection which is similar to resistance. Only difference is that resisted damage also will reflect back to caster. All elements including neutral damage is reflectable.
