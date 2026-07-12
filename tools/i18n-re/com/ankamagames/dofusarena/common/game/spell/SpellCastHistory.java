/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.common.game.spell;

import com.ankamagames.dofusarena.common.game.fight.SpellCastValidity;
import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
import com.ankamagames.framework.ai.targetfinder.Target;
import gnu.trove.HashFunctions;
import java.util.HashMap;

public class SpellCastHistory {
    private final HashMap<AbstractSpell, Integer> m_spellsCasted = new HashMap();
    private final HashMap<AbstractSpell, Integer> m_spellsCastedThisTurn = new HashMap();
    private final HashMap<Long, Integer> m_spellsCastedThisTurnOnTarget = new HashMap();

    public void reset() {
        this.m_spellsCasted.clear();
        this.m_spellsCastedThisTurn.clear();
        this.m_spellsCastedThisTurnOnTarget.clear();
    }

    public void onNewTurn() {
        this.m_spellsCastedThisTurn.clear();
        this.m_spellsCastedThisTurnOnTarget.clear();
    }

    public void storeSpellCast(AbstractSpell spell, int currentTableTurn, Target target) {
        if (spell.getMinCastInterval() > 0) {
            this.m_spellsCasted.put(spell, currentTableTurn);
        }
        if (spell.getCastMaxPerTurn() > 0) {
            Integer castsCount = this.m_spellsCastedThisTurn.get(spell);
            if (castsCount == null) {
                this.m_spellsCastedThisTurn.put(spell, 1);
            } else {
                this.m_spellsCastedThisTurn.put(spell, castsCount + 1);
            }
        }
        if (target != null && spell.getCastMaxPerTarget() > 0) {
            long hash = this.getSpellOnTargetHashCode(spell, target);
            Integer castsCount = this.m_spellsCastedThisTurnOnTarget.get(hash);
            if (castsCount == null) {
                this.m_spellsCastedThisTurnOnTarget.put(hash, 1);
            } else {
                this.m_spellsCastedThisTurnOnTarget.put(hash, castsCount + 1);
            }
        }
    }

    public SpellCastValidity canCastSpell(AbstractSpell spell, int currentTableTurn) {
        return this.canCastSpell(spell, currentTableTurn, null);
    }

    public SpellCastValidity canCastSpell(AbstractSpell spell, int currentTableTurn, Target target) {
        long hash;
        Integer castsCount;
        Integer castsCount2;
        Integer lastCastTime;
        if (spell.getMinCastInterval() > 0 && (lastCastTime = this.m_spellsCasted.get(spell)) != null) {
            if (spell.getMinCastInterval() == 63 || currentTableTurn - lastCastTime < spell.getMinCastInterval()) {
                return SpellCastValidity.LAST_CAST_TOO_RECENT;
            }
            this.m_spellsCasted.remove(spell);
        }
        if (spell.getCastMaxPerTurn() > 0 && (castsCount2 = this.m_spellsCastedThisTurn.get(spell)) != null && castsCount2 >= spell.getCastMaxPerTurn()) {
            return SpellCastValidity.TOO_MUCH_CASTS_THIS_TURN;
        }
        if (target != null && spell.getCastMaxPerTarget() > 0 && (castsCount = this.m_spellsCastedThisTurnOnTarget.get(hash = this.getSpellOnTargetHashCode(spell, target))) != null && castsCount >= spell.getCastMaxPerTarget()) {
            return SpellCastValidity.TOO_MUCH_CASTS_ON_THIS_TARGET;
        }
        return SpellCastValidity.OK;
    }

    private long getSpellOnTargetHashCode(AbstractSpell spell, Target target) {
        return (long)spell.getId() << 32 | (long)HashFunctions.hash(target);
    }
}

