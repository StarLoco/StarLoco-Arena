/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.element;

import com.ankamagames.baseImpl.graphics.alea.CustomElementFactory;
import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalParametrizedWorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.alea.element.BonusElement;
import com.ankamagames.dofusarena.client.alea.element.FightStartCoachPointElement;
import com.ankamagames.dofusarena.client.alea.element.FightStartPointElement;

public class DofusArenaCustomElementFactory
implements CustomElementFactory {
    public static final int ELEMENT_TYPE_FIGHT_START_POINT = 1000;
    public static final int ELEMENT_TYPE_COACH_POINT = 1001;
    public static final int ELEMENT_TYPE_BONUS = 1002;
    private static DofusArenaCustomElementFactory m_instance = new DofusArenaCustomElementFactory();

    private DofusArenaCustomElementFactory() {
    }

    public static DofusArenaCustomElementFactory getInstance() {
        return m_instance;
    }

    public BasicElement createElement(int elementId, int elementType) {
        BasicElement element = null;
        switch (elementType) {
            case 1000: {
                element = new FightStartPointElement(elementId);
                break;
            }
            case 1001: {
                element = new FightStartCoachPointElement(elementId);
                break;
            }
            case 1002: {
                element = new BonusElement(elementId);
            }
        }
        return element;
    }

    public WorldElement createWorldElement(BasicElement element, int paramsCount, byte[] params, int state, int groupId) {
        int elementType = element.getType();
        GraphicalParametrizedWorldElement worldElement = null;
        switch (elementType) {
            case 1000: {
                break;
            }
            case 1001: {
                break;
            }
            case 1002: {
                worldElement = new GraphicalParametrizedWorldElement(element.getId(), paramsCount, params, state, groupId);
            }
        }
        return worldElement;
    }
}

