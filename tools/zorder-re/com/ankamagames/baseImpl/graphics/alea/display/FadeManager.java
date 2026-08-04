/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
import com.ankamagames.graphics.isometric.IsoWorldScene;
import com.ankamagames.graphics.isometric.RenderProcessHandler;
import gnu.trove.TLongArrayList;
import gnu.trove.TLongIntHashMap;
import gnu.trove.TLongIntIterator;

public class FadeManager
implements RenderProcessHandler {
    private static final int GROUP_FADE_MAX = 25;
    private static final int OUTDOOR_FADE_MAX = 10;
    private static final float TEINT_FACTOR = 0.03f;
    private static final FadeManager m_instance = new FadeManager();
    private TLongIntHashMap m_groupAlphaFading = new TLongIntHashMap();
    private int m_outdoorBlackFading = 10;
    private int m_outdoorAlphaFading = 10;
    private TLongArrayList m_maskedGroupLayers = new TLongArrayList();
    private boolean m_outdoorVisible = true;

    public static FadeManager getInstance() {
        return m_instance;
    }

    public void apply(DisplayedElement displayedElement, float brightnessValue, float[] teint) {
        float blackFade;
        float alphaFade;
        int groupInstanceId = displayedElement.getWorldElement().getGroupInstanceId();
        int groupLayer = displayedElement.getLevel();
        long hashLayer = (long)groupLayer & 0xFFFFFFFFL;
        long hashGroup = (long)groupInstanceId & 0xFFFFFFFFL;
        long hashIndex = hashLayer << 32 | hashGroup;
        if (groupInstanceId > 0) {
            if (!this.m_groupAlphaFading.contains(hashIndex)) {
                this.m_groupAlphaFading.put(hashIndex, 25);
            }
            alphaFade = (float)this.m_groupAlphaFading.get(hashIndex) / 25.0f;
            blackFade = alphaFade / 2.0f;
        } else {
            alphaFade = (float)this.m_outdoorAlphaFading / 10.0f;
            blackFade = (float)this.m_outdoorBlackFading / 10.0f * brightnessValue;
        }
        if (teint == null) {
            displayedElement.getMesh().setColor(blackFade, blackFade, blackFade, alphaFade);
        } else {
            displayedElement.getMesh().setColor(blackFade + teint[0], blackFade + teint[1], blackFade + teint[2], alphaFade);
        }
        displayedElement.setVisible(alphaFade > 0.0f);
    }

    public void addMaskedLayers(int layer, int groupInstance) {
        if (layer == -1) {
            this.m_outdoorVisible = false;
            return;
        }
        long hashLayer = (long)layer & 0xFFFFFFFFL;
        long hashGroup = (long)groupInstance & 0xFFFFFFFFL;
        long hashIndex = hashLayer << 32 | hashGroup;
        this.m_maskedGroupLayers.add(hashIndex);
    }

    public void clearMaskedLayers() {
        this.m_maskedGroupLayers.clear();
        this.m_outdoorVisible = true;
    }

    public void setOutdoorVisible(boolean outdoorVisible) {
        this.m_outdoorVisible = outdoorVisible;
    }

    public void process(IsoWorldScene scene, long realTime, int frameCount) {
        TLongIntIterator it = this.m_groupAlphaFading.iterator();
        while (it.hasNext()) {
            it.advance();
            if (this.m_maskedGroupLayers.contains(it.key())) {
                if (it.value() <= 0) continue;
                this.m_groupAlphaFading.adjustValue(it.key(), -1);
                continue;
            }
            if (it.value() >= 25) continue;
            this.m_groupAlphaFading.adjustValue(it.key(), 1);
        }
        if (this.m_outdoorVisible) {
            if (this.m_outdoorAlphaFading < 10) {
                ++this.m_outdoorAlphaFading;
            } else if (this.m_outdoorBlackFading < 10) {
                ++this.m_outdoorBlackFading;
            }
        } else if (this.m_outdoorBlackFading > 0) {
            --this.m_outdoorBlackFading;
        } else if (this.m_outdoorAlphaFading > 0) {
            --this.m_outdoorAlphaFading;
        }
    }

    public void prepareBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {
    }
}

