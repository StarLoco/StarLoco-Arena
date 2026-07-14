# Dodge

At any time when a character stands adjacent to at least one enemy and tries move away from that tile, a dodge roll is made to see if it succeeds. If it does not the turn is immediately lost.

To understand dodge or block, it is crucial to think in terms of normal everyday 6-sided dies. Normal dodge chance is 3/6 when two characters are adjacent to each other, and neither character has any block or dodge modifiers. Any additional opponent past the first makes things 1/6 tougher for you, so your chance will be 2/6 for two opponents around you, and 1/6 for three opponents around you.

This translates into following percentages:

- 50% for one opponent adjacent to your character
- 33% for two opponents adjacent to your character
- 16% for three opponents adjacent to your character.

Dodge is essentially the opposite of [Block](../mechanics/Block.md).

The above is enough for the state of affairs for Beta 13. Everything below describes the dodge / block mechanism of earlier Betas which may, or may not be here when the skill system is reintroduced.

Bonus of +1 dodge gives better chances, making it 4/6. Bonus of +2 dodge gives 5/6, bonus of +3 dodge gives 6/6. Greater dodge bonuses give no additional benefits but can be useful if the opponent has block bonuses or if more than one opponent is adjacent to your character.

Any additional opponent past the first who has no modifiers makes things 1/6 tougher for you, so your chance will be 2/6 for two opponents around you, and 1/6 for three opponents around you.

On top of that, every +1 block on your opponent will make it 1/6 harder for you to move, +2 block on your opponent 2/6 etc.

**Example**

A sacrier with +3 dodge will always have a 100% dodge versus any single opponent who does not have a block modifier. Base chance is 3/6, and his +3 dodge makes it 6/6. If the opponent has 1 block, he will have 5/6 (which is 83%).

**Example 2**

An iop has no dodge modifiers. If he is facing a gobball, who has no dodge bonuses, he will have 50% chance to dodge, or 3/6. If there are two gobballs, his chance will be 2/6 (or 33%). If he is facing 1 gobball and one character with +1 block, then the chance will only be 1/6 (or 16%).

**Example 3**

An eniripsa has +1 block and +1 dodge. If she is facing a gobball, who has no dodge bonuses, she will have 66% chance to dodge, or 4/6. If that gobball wants to move away, its chance will be 2/6 (or 33%). If there are two gobballs, the eniripsa's chance will be 3/6 (or 50%). To calculate the chances, just add up block and dodge to the base 3/6 chance.

**Example 4**

An ecaflip has no block or dodge modifiers. He is surrounded by two eniripsas. One of the eniripsas has +2 to block. The chances for the ecaflip to move is 0/6, or 0%. His chance would be 2/6 if the eniripsas had no modifiers. If one eniripsa only had +1 to block, his chance would be 1/6. Since the eniripsa has +2 to block, his chance is 0/6, which is 0%.
