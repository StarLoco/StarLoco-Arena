# Spell Duration

Some [spells](../rules/Spells.md) have effects that last longer than just the moment it is cast. [Spells](../rules/Spells.md) can have any of the following spell duration:

| Duration | Meaning |
|---|---|
| - | Spell effect is immediate. |
| Game | Spell effect last whole game from the point it is cast. |
| X Rounds | Spell effect last X [rounds](./Round.md), which means caster turn + X full [rounds](./Round.md) + caster turn. |

The effect of a spell is immediately canceled if the source of the spell is killed. For example if you kill an eniripsa the extra AP is lost from [Stimulating Word](../breeds/Eniripsa/spells/StimulatingWord.md).

In general all spells will be canceled this way. ([Feca Shield](../breeds/Feca/spells/FecaShield.md), [Immunity](../breeds/Feca/spells/Immunity.md), [Truce](../breeds/Feca/spells/Truce.md), [Weakness](../breeds/Feca/spells/Weakness.md), [Bear's Cry](../breeds/Osamoda/spells/BearsCry.md), [Carapace](../breeds/Osamoda/spells/Carapace.md), [High Energy Shot](../breeds/Osamoda/spells/HighEnergyShot.md), [Acceleration](../breeds/Enutrof/spells/Acceleration.md), [Bribery](../breeds/Enutrof/spells/Bribery.md), [Clumsiness](../breeds/Enutrof/spells/Clumsiness.md), [Mass Clumsiness](../breeds/Enutrof/spells/MassClumsiness.md), [Petrifaction](../breeds/Enutrof/spells/Petrifaction.md), [Fog](../breeds/Sram/spells/Fog.md), [Invisibility Of Others](../breeds/Sram/spells/InvisibilityOfOthers.md), [Lethal Trap](../breeds/Sram/spells/LethalTrap.md), [Devotion](../breeds/Xelor/spells/Devotion.md), [Slow Down](../breeds/Xelor/spells/SlowDown.md), [Time Theft](../breeds/Xelor/spells/TimeTheft.md), [Clover](../breeds/Ecaflip/spells/Clover.md), [Risky Petrifaction](../breeds/Ecaflip/spells/RiskyPetrifaction.md), [Rotten Luck](../breeds/Ecaflip/spells/RottenLuck.md), [Roulette](../breeds/Ecaflip/spells/Roulette.md), [Regenerating Word](../breeds/Eniripsa/spells/RegeneratingWord.md), [Stimulating Word](../breeds/Eniripsa/spells/StimulatingWord.md), [Word Of Torture](../breeds/Eniripsa/spells/WordOfTorture.md), [Bravery Guide](../breeds/Iop/spells/BraveryGuide.md), [Increase](../breeds/Iop/spells/Increase.md), [Eagle Eye](../breeds/Cra/spells/EagleEye.md), [Paralyzing Arrow](../breeds/Cra/spells/ParalyzingArrow.md), [Visual Torment](../breeds/Cra/spells/VisualTorment.md), [Poisoning](../breeds/Sadida/spells/Poisoning.md), [Sacrier's Foot](../breeds/Sacrier/spells/SacriersFoot.md), [Sacrifice](../breeds/Sacrier/spells/Sacrifice.md), [Karcham](../breeds/Panda/spells/Karcham.md), [Karzam](../breeds/Panda/spells/Karzam.md), [Stabilization For Others](../breeds/Panda/spells/StabilizationForOthers.md) and all [summon spells](../rules/Summons.md).)

Exceptions to the above rule:

- Damage is never recoverable, nor can you lose HP by killing a character. (For example extra HPs from [Word Of Torture](../breeds/Eniripsa/spells/WordOfTorture.md) are not lost by killing eniripsa.)
- MP or AP lost from a spell that simultaneously cause damage is not recoverable. There are three spells with this property: [Cut](../breeds/Iop/spells/Cut.md), [Frozen Arrow](../breeds/Cra/spells/FrozenArrow.md) and [Crackler's Crushing](../breeds/Osamoda/spells/SummoningOfCrackler.md). This might very well be a bug.

Note that if you kill an eniripsa that have cast [Word Of Torture](../breeds/Eniripsa/spells/WordOfTorture.md) all character lose their max HP level though the HP is not lost. In this situation they could have more current HPs than max HPs, and in this case they cannot heal HPs until they are again below max limit. If you try heal characters or opponent cast [Bribery](../breeds/Enutrof/spells/Bribery.md) or other healing spell on targets with more HPs than max, the targets will lose the extra HPs.
