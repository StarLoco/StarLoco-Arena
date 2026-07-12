/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.mobile;

import com.ankamagames.baseImpl.graphics.alea.mobile.StyleMobile;
import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.PathMovementStyle;
import com.ankamagames.framework.kernel.core.maths.Direction8;

public class ThrowMovementStyle
implements PathMovementStyle {
    public static final String NAME = "Throw";
    private static int THROW_CELL_SPEED = 60;
    private int m_distance = 0;
    private StyleMobile m_movementActor;

    public void setMobile(StyleMobile actor) {
        this.m_movementActor = actor;
    }

    public int getCellSpeed() {
        return this.m_distance > 1 ? THROW_CELL_SPEED : 300;
    }

    public int getAirImpulsion() {
        return this.m_distance + 1;
    }

    public void onStandingOnLastCell() {
        this.m_movementActor.setMovementStyle(MovementStyleManager.WALK_STYLE);
    }

    public void onMovingOnAir(double cellPositionPercent) {
    }

    public void onMovingOnGround(int remainPathLength) {
    }

    public void onWaiting() {
    }

    public void onDirectionChanged(Direction8 newDirection) {
    }

    public boolean createPathOnSetPosition() {
        return true;
    }

    public boolean isAirImpulsionNeeded(int dz) {
        return true;
    }

    public void setDistance(int distance) {
        this.m_distance = distance;
    }
}

