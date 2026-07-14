# Damage

1. [Randomness](#randomness)
2. [Spell damage](#spell-damage)
3. [Altitude damage](#altitude-damage)
4. [Push damage](#push-damage)
5. [Backstab damage](#backstab-damage)
6. [Motivation damage](#motivation-damage)
7. [Rebound damage](#rebound-damage)
8. [Resistance](#resistance)

## Randomness

Some damage is random and thus have a dice. This is noted as for example 2d6+6. This is in most rpgs calculated as rolling two six-sided dices and then adding 6. In dofus arena however only one dice is rolled and then multiplied by 2 and then adding 6. The average is still the same but the deviation is much larger in this latter case. Possible outcome for our example is 8, 10, 12, 14, 16 and 18. (This is the damage a [Gobball](../breeds/Osamoda/spells/SummoningOfGobball.md) can do.)

As requested, here are some further examples:

| Notation | Explanation | Possible outcome (all with equal probability) | Average |
|---|---|---|---|
| 6d6 | Roll one 6-sided dice, multiply with 6. | 6, 12, 18, 24, 30, 36 | 21 |
| 5d5 | Roll one 5-sided dice, multiply with 5. | 5, 10, 15, 20, 25 | 15 |
| 1d5 | Roll one 5-sided dice. | 1, 2, 3, 4, 5 | 3 |
| 6d6+6 | Roll one 6-sided dice, multiply with 6, add then add 6. | 12, 18, 24, 30, 36, 42 | 27 |
| 1d5+3 | Roll one 5-sided dice, add 3. | 4, 5, 6, 7, 8 | 6 |
| XdY+Z | Roll one Y-sided dice, multiply with X, add Z. | X+Z, 2*X+Z, 3*X+Z, ... , Y*X+Z | (Y+1)*X/2+Z |

Spells with random damage: [All Or Nothing](../breeds/Ecaflip/spells/AllOrNothing.md), [Feline Spirit](../breeds/Ecaflip/spells/FelineSpirit.md), [Heads or Tails](../breeds/Ecaflip/spells/HeadsOrTails.md), [Lottery](../breeds/Ecaflip/spells/Lottery.md), [Altruism](../breeds/Eniripsa/spells/Altruism.md), [Magic Arrow](../breeds/Cra/spells/MagicArrow.md), [Dollical Sacrifice](../breeds/Sadida/spells/DollicalSacrifice.md), [Bite of Gobball](../breeds/Osamoda/spells/SummoningOfGobball.md), [Kiss of Tofu](../breeds/Osamoda/spells/SummoningOfTofu.md).

## Spell damage

Spell damage is the most common form of damage. There are a huge variety of damage spells.

_Here should follow a comparison chart. Under production._

## Altitude damage

A character walking or falling down a cliff hurt damage according to the table below:

| Altitude | Walking voluntary | Being pushed/pulled down |
|---|---|---|
| 1 | - | 5 HP |
| 2 | 6 HP | 10 HP |
| 3 | 9 HP | 15 HP |
| 4 | 12 HP | 20 HP |

## Push damage

When pushed into an obstacle, a character takes damage. Depending on how powerful the push/pull spell is the character takes different amount of damage. The strength of the push is measured as the remaining number of tiles left to be pushed when reached the obstacle. For example suppose someone is shot with [Retreat Arrow](../breeds/Cra/spells/RetreatArrow.md) (pushes back 2 tiles) and the character is pushed back one step before hitting the tree, then the remaining strength of the push will be 1. If the obstacle is in fact another character, both character split the damage as seen in the table below.

Following five spells can cause push damage: [Fear](../breeds/Sram/spells/Fear.md), [Tricky Blow](../breeds/Sram/spells/TrickyBlow.md), [Attraction](../breeds/Sacrier/spells/Attraction.md), [Alcoholic Blow](../breeds/Panda/spells/AlcoholicBlow.md) and [Retreat Arrow](../breeds/Cra/spells/RetreatArrow.md).

Push damage chart:

| Push strength | Damage taken (single character) | Damage taken (two characters) |
|---|---|---|
| 1 | 6 dmg | 3 dmg each |
| 2 | 9 dmg | 4 dmg each |
| 3 | 12 dmg | 6 dmg each |
| 4 | 15 dmg | 7 dmg each |
| 5 | 18 dmg | 9 dmg each |
| 6 | 21 dmg | 10 dmg each |

Note that in the special case of [Attraction](../breeds/Sacrier/spells/Attraction.md) you need an obstacle between you and the target to do damage. In this case the strength can never be larger than the number of tiles the target would be attracted should there be no obstacle. For example suppose there is one tile between you and the enemy and that tile contain a fence. If you at this position launch attract you will only do 6 dmg, while if you step back two steps before attracting you will do 12 dmg. This might very well be a bug.

## Backstab damage

Hitting someone from behind yields 6 extra damage. Hitting someone from the side yields 2 extra damage. To avoid getting stabbed at your back, turn your character around by using the arrows at the bottom of the screen before ending your turn. The sram spell [Diversion](../breeds/Sram/spells/Diversion.md) is used to turn enemies around to get this damage bonus.

All direct damage spells that have targets, including all area spells can give extra backstab damage bonus except for the following cases:

- Spells that steal HP: [Life Theft](../breeds/Sram/spells/LifeTheft.md), [Absorptive Arrow](../breeds/Cra/spells/AbsorptiveArrow.md), [Dollical Sacrifice](../breeds/Sadida/spells/DollicalSacrifice.md), [Bloodthirsty Madness](../breeds/Sacrier/spells/BloodthirstyMadness.md).
- Spells that affect all players: [Shaking](../breeds/Sadida/spells/Shaking.md), [All or Nothing](../breeds/Ecaflip/spells/AllOrNothing.md).

Acquiring this bonus using Area of Effect spells (AoE) depends on the position of the character using the spell. To get a +2 damage bonus, you need to launch the spell to a character/s who are facing sideways to the character. To get a +6 damage bonus, you need to launch the spell to a character/s who are facing away from the character. It is possible for different damage bonuses to apply to different characters within the AoE, as it judges on a character to character basis, not a character to AoE basis.

## Motivation damage

Standing on the Enthusiasm tile on the beginning of a turn yields a +5 Neutral damage bonus for that turn. This tile cause in most cases unexpected results:

- All spells with elemental damage has no effect from this tile.
- Spell that is self-inflicting cause 5 extra damage to self: [Feline Spirit](../breeds/Ecaflip/spells/FelineSpirit.md), [Word of Sacrifice](../breeds/Eniripsa/spells/WordOfSacrifice.md), [Word of Torture](../breeds/Eniripsa/spells/WordOfTorture.md), [Mutilation](../breeds/Iop/spells/Mutilation.md), [Life Transfer](../breeds/Sacrier/spells/LifeTransfer.md), [Punishment](../breeds/Sacrier/spells/Punishment.md)
- Spell that cause neutral damage cause 5 extra damage: [Whip](../breeds/Osamoda/spells/Whip.md), [Petrifaction](../breeds/Enutrof/spells/Petrifaction.md), [Life Theft](../breeds/Sram/spells/LifeTheft.md), [All or Nothing](../breeds/Ecaflip/spells/AllOrNothing.md), [Absorptive Arrow](../breeds/Cra/spells/AbsorptiveArrow.md), [Paralyzing Arrow](../breeds/Cra/spells/ParalyzingArrow.md), [Retreat Arrow](../breeds/Cra/spells/RetreatArrow.md), [Shaking](../breeds/Sadida/spells/Shaking.md).
- Standing on a tile under the influence of [Poisoning](../breeds/Sadida/spells/Poisoning.md) will not cause poison to hurt 5 extra damage.

## Rebound damage

Read more about rebound damage [here](./DamageRebound.md).

## Resistance

Read more about resistance [here](./Resistance.md).
