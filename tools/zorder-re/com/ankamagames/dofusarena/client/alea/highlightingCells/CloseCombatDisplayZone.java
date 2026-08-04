/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
import com.ankamagames.dofusarena.client.alea.highlightingCells.RangeAndEffectDisplayer;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;

public class CloseCombatDisplayZone
extends RangeAndEffectDisplayer {
    private static final String ZONE_EFFECT_NAME = "CloseCombatZoneEffect";
    private static final String RANGE_NAME = "CloseCombatRange";
    private static final String RANGE_WITH_CONSTRAINT = "CloseCombatRangeWithContraint";

    public CloseCombatDisplayZone() {
        super(RANGE_NAME, DofusArenaClientConstants.RANGE_COLOR, ZONE_EFFECT_NAME, DofusArenaClientConstants.ZONE_EFFECT_COLOR, RANGE_WITH_CONSTRAINT, DofusArenaClientConstants.RANGE_COLOR_WITH_CONSTRAINTS);
    }

    public void selectCloseCombatRange(Fighter fighter, DofusArenaWorldScene scene) {
        this.selectRange(fighter, scene);
    }

    protected RangeAndEffectDisplayer.RangeValidity checkValidity(WorldElement element) {
        switch (this.m_fight.getCloseCombatValidity(this.m_fighter, element.getCoordinates())) {
            case OK: {
                return RangeAndEffectDisplayer.RangeValidity.OK;
            }
        }
        return RangeAndEffectDisplayer.RangeValidity.INVALID;
    }
}

