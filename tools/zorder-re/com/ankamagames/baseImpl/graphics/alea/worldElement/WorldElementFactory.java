/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.worldElement;

import com.ankamagames.baseImpl.graphics.alea.CustomElementFactory;
import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.CustomWorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;

public class WorldElementFactory {
    private static CustomElementFactory m_customFactory = null;

    public static void setCustomElementFactory(CustomElementFactory factory) {
        m_customFactory = factory;
    }

    public static WorldElement create(BasicElement element, int paramsCount, byte[] params, int state, int groupId) {
        WorldElement worldElement = null;
        switch (element.getType()) {
            case 2: {
                worldElement = new GraphicalWorldElement(element.getId(), paramsCount, params, state, groupId);
                break;
            }
            default: {
                if (m_customFactory != null) {
                    worldElement = m_customFactory.createWorldElement(element, paramsCount, params, state, groupId);
                }
                if (worldElement != null) break;
                worldElement = new CustomWorldElement(element.getId(), paramsCount, params, state, groupId);
            }
        }
        return worldElement;
    }
}

