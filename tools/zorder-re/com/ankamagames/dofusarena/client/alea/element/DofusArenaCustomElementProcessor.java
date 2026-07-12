/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.element;

import com.ankamagames.baseImpl.graphics.alea.CustomElementProcessor;
import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DofusArenaCustomElementProcessor
implements CustomElementProcessor {
    private static DofusArenaCustomElementProcessor m_instance = new DofusArenaCustomElementProcessor();

    private DofusArenaCustomElementProcessor() {
    }

    public static DofusArenaCustomElementProcessor getInstance() {
        return m_instance;
    }

    @Override
    public void onReadCell(WorldCell cell, WorldElement worldElement, ArrayList<WorldElement>[] cellData) {
        switch (worldElement.getElement().getType()) {
            case 1000: {
                break;
            }
            case 1001: {
                break;
            }
            case 1002: {
                cell.addVisualElement((GraphicalWorldElement)worldElement);
            }
        }
    }
}

