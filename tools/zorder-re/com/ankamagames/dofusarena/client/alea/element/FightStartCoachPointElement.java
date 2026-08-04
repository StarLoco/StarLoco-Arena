/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.element;

import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;

public class FightStartCoachPointElement
extends BasicElement {
    public FightStartCoachPointElement(int elementId) {
        super(elementId);
        this.setType(1001);
    }

    public static byte getTeamId(WorldElement element) {
        return element.getParams()[1];
    }
}

