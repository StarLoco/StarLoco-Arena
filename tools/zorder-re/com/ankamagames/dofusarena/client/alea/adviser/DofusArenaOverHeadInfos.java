/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.adviser;

import com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.overHeadInfos.OverHeadInfos;
import java.awt.Font;

public class DofusArenaOverHeadInfos
extends OverHeadInfos {
    private static Font DEFAULT_FONT = new Font("Arial Unicode MS", 0, 12);
    private static int DEFAULT_DURATION = 2500;
    private static int DEFAULT_MAX_WIDTH = 200;
    private static int DEFAULT_Y_OFFSET = 90;

    public DofusArenaOverHeadInfos(String text) {
        super(DEFAULT_FONT, text, DEFAULT_DURATION);
    }

    protected void init() {
        super.init();
        this.setMaxWidth(DEFAULT_MAX_WIDTH);
        this.setYOffset(DEFAULT_Y_OFFSET);
        this.setBackgroundColor(0.0f, 0.0f, 0.0f, 0.8f);
        this.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

