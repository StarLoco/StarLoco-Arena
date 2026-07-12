/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
import com.ankamagames.dofusarena.client.alea.highlightingCells.RangeAndEffectDisplayer;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;

public class FighterCardUseDisplayZone
extends RangeAndEffectDisplayer {
    private static final String ZONE_EFFECT_NAME = "FighterCardUseZoneEffect";
    private static final String RANGE_NAME = "FighterCardUseRange";
    private static final String RANGE_WITH_CONSTRAINT_NAME = "FighterCardUseRangeWithConstraint";
    private AbstractFighterCard m_fighterCard;

    public FighterCardUseDisplayZone() {
        super(RANGE_NAME, DofusArenaClientConstants.RANGE_COLOR, ZONE_EFFECT_NAME, DofusArenaClientConstants.ZONE_EFFECT_COLOR, RANGE_WITH_CONSTRAINT_NAME, DofusArenaClientConstants.RANGE_COLOR_WITH_CONSTRAINTS);
    }

    public void selectCardUseRange(AbstractFighterCard fighterCard, Fighter fighter, DofusArenaWorldScene scene) {
        this.m_fighterCard = fighterCard;
        this.selectRange(fighter, scene);
        this.m_fighterCard = null;
    }

    protected RangeAndEffectDisplayer.RangeValidity checkValidity(WorldElement element) {
        switch (this.m_fight.getCardUseValidity(this.m_fighter, this.m_fighterCard, element.getCoordinates())) {
            case OK: {
                return RangeAndEffectDisplayer.RangeValidity.OK;
            }
        }
        return RangeAndEffectDisplayer.RangeValidity.INVALID;
    }
}

