/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client.core.action;

import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
import com.ankamagames.dofusarena.client.core.action.AbstractFightCastAction;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
import com.ankamagames.dofusarena.client.core.game.spell.Spell;
import org.apache.log4j.Logger;

public class SpellAction
extends AbstractFightCastAction {
    protected static Logger m_logger = Logger.getLogger(SpellAction.class);
    private final Spell m_spell;
    private final boolean m_display;

    public SpellAction(int uniqueId, int actionType, int actionId, Spell spell, boolean criticalHit, boolean criticalMiss, long casterId, int x, int y, short z, boolean display) {
        super(uniqueId, actionType, actionId, criticalHit, criticalMiss, casterId, x, y, z);
        this.m_spell = spell;
        this.m_display = display;
        this.setScriptFileId(this.m_spell.getScriptId());
    }

    public void run() {
        Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(this.getInstigatorId());
        if (fighter != null) {
            m_fightLogger.info(DofusArenaTranslator.getInstance().getString("fight.spellCast", fighter.getName(), this.m_spell.getName()));
        }
        if (this.m_display) {
            super.run();
        } else {
            this.fireActionFinishedEvent();
        }
    }
}

