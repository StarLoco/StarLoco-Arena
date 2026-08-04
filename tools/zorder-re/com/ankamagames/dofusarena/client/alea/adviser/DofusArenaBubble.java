/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.adviser;

import com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.bubble.Bubble;
import java.awt.Font;

public class DofusArenaBubble
extends Bubble {
    private static Font DEFAULT_FONT = new Font("Arial Unicode MS", 0, 12);
    private static int DEFAULT_DURATION = 2500;
    private static int DEFAULT_MAX_WIDTH = 200;
    private static int DEFAULT_MIN_WIDTH = 45;
    private static int DEFAULT_X_OFFSET = -5;
    private static int DEFAULT_Y_OFFSET = 80;

    public DofusArenaBubble(String text) {
        super(DEFAULT_FONT, text);
        int duration = 0;
        if (text != null) {
            duration = text.length() * 50;
        }
        this.setDuration(DEFAULT_DURATION + duration);
        this.setMaxWidth(DEFAULT_MAX_WIDTH);
        this.setMinWidth(DEFAULT_MIN_WIDTH);
        this.setXOffset(DEFAULT_X_OFFSET);
        this.setYOffset(DEFAULT_Y_OFFSET);
    }
}

