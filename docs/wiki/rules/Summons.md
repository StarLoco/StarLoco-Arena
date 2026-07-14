# Summons

There are four summoning classes: osamodas, sadidas, srams and xelors. Each summoning character can hold as default one summon. Summoning another is not possible until the summoned creature is dead. However it is possible to boost the number of summons by:

- the spell Animal Blessing which increases number of summons a osamoda can have with one.
- the spell Knowledge of the Dolls which increases number of summons a sadida can have with one.
- the event card *Appearance of the God Osamodas* which increases number of summons a osamoda can have with one.
- the event card *Appearance of the God Sadida* which increases number of summons a sadida can have with one.

The spells will last forever but the event cards will only last for the round. However any extra summoned creatures from event cards will last until killed.

Following are the stats of all the summons in Dofus Arena.

| Class | Name | HP | MP | AP | Spell | Cost | Range | Effect | Critical Effect | Behavior |
|---|---|---|---|---|---|---|---|---|---|---|
| Osamoda | Crackler | 30 | 2 | 6 | Crushing | 5 AP | 1 to 1 | 20 earth dmg, -1 MP | 20 earth dmg, -3 MP | Charge in |
| Osamoda | Gobball | 20 | 2 | 6 | Bite of Gobball | 6 AP | 1 to 1 | 2d6+6 earth dmg | - | Charge in |
| Osamoda | Royal Gobball | 40 | 3 | 6 | Bite of Gobball | 6 AP | 1 to 1 | 2d6+6 earth dmg | - | Charge in |
| Osamoda | Prespic | 15 | 3 | 6 | Mockery | 6 AP | 1 to 2 | -1 AP | -2 AP | Hit and Return |
| Osamoda | Tofu | 5 | 6 | 6 | Kiss of Tofu | 6 AP | 1 to 2 | 1d6+1 air dmg | - | Hit and Return |
| Sram | Double | 70 | 3 | 6 | - | - | - | - | - | Charge in |
| Sadida | Blocker | 30 | 2 | 6 | - | - | - | - | - | Charge in |
| Sadida | Madoll | 10 | 3 | 6 | Irritation | 5 AP | 1 to 3 | -1 AP | -3 AP, -3 MP | Hit and Return |
| Sadida | Tree | 40 | 0 | 0 | - | - | - | - | - | Stand still |
| Sadida | Victim | 10 | 2 | 6 | Sacrifice | 4 AP | 1 to 1 | 30 neutral dmg, -50 neutral dmg to self | - | Charge in |
| Xelor | Dial | 50 | 1 | 4 | Mirwar | 2 AP | Self | +3 damage reflect | - | Charge in |

---

## Summon Intelligence

Summons are not very bright to choose which tiles to step on or which targets to choose, nor is it random. The summons follow a very special intelligence as follows:

- Summons will always choose to walk towards the closest hostile.
- In case of multiple closest hostiles, the summon will choose the one which has highest initiative according to the turn order. (The initiative of the summon doesn't matter.)
- In case the summon is deciding between walking towards a character and its summon, it will choose the summoner before the summon. (However it will choose the summon before any other character or summon that makes their turn later.)
- When the summon has decided which target to walk towards according to the above, the path is chosen by using following prioritizing scheme:

1. Southeast
2. Southwest
3. Northwest
4. Northeast

Thus, if there are multiple ways to the target, the summon will always take one which starts with southeast if possible. If no shortest path begin with southeast, it will try southwest and so on. Assuming it found a shortest path starting with southwest it will from there again look for the shortest path starting with southeast and so forth.

This prioritizing scheme has the effect that the behavior of the summons are different depending on where you start. For example if you start at southwest the summons will never move past the opponent rather it will block which is one important feature of the summon. (If the enemy walk sideways the summon also will.) On the other hand if your start position is northwest the summons will go straight forward against the enemy and then attack on the side without much blocking.

---

**Notes**
- "Charge in" behavior means this summon will go towards the nearest enemy.
- "Hit and Return" means that the summon try escape after delivering spell. The summon escape towards its creator.
- The Mirwar spell is dispellable.
- Summons disappears immediately when their creator is killed.
- You can cast any spell on a summon that you could cast on any other character.
- In addition following spells can only be cast on summons: Bear's Cry, Carapace, High-energy Shot, Whip and Dollical Sacrifice.
- Summons do not see invisible characters.
- Summons do not jump down cliffs with altitude of 2 or higher, nor can they climb them.
- Cards do not work on summons
- summons also will not step on lethal traps
