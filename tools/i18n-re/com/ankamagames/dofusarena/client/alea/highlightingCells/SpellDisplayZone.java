/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
import com.ankamagames.dofusarena.client.alea.highlightingCells.RangeAndEffectDisplayer;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
import com.ankamagames.dofusarena.client.core.game.spell.Spell;

public class SpellDisplayZone
extends RangeAndEffectDisplayer {
    private static final String ZONE_EFFECT_NAME = "SpellZoneEffect";
    private static final String RANGE_NAME = "SpellRange";
    private static final String RANGE_WITH_CONSTRAINT_NAME = "SpellRangeWithConstraint";
    private Spell m_selectedSpell;

    public SpellDisplayZone() {
        super(RANGE_NAME, DofusArenaClientConstants.RANGE_COLOR, ZONE_EFFECT_NAME, DofusArenaClientConstants.ZONE_EFFECT_COLOR, RANGE_WITH_CONSTRAINT_NAME, DofusArenaClientConstants.RANGE_COLOR_WITH_CONSTRAINTS);
    }

    public void selectSpellRange(Spell selectedSpell, Fighter fighter, DofusArenaWorldScene scene) {
        this.m_selectedSpell = selectedSpell;
        this.selectRange(fighter, scene);
        this.m_selectedSpell = null;
    }

    protected void selectRange(Fighter fighter, DofusArenaWorldScene scene) {
        super.selectRange(fighter, scene);
    }

    protected RangeAndEffectDisplayer.RangeValidity checkValidity(WorldElement element) {
        switch (this.m_fight.getSpellCastValidity(this.m_fighter, this.m_selectedSpell, element.getCoordinates())) {
            case OK: {
                return RangeAndEffectDisplayer.RangeValidity.OK;
            }
            case OK_BUT_NO_EFFECT_ON_TARGET: {
                return RangeAndEffectDisplayer.RangeValidity.OK_WITH_CONSTRAINTS;
            }
        }
        return RangeAndEffectDisplayer.RangeValidity.INVALID;
    }
}

