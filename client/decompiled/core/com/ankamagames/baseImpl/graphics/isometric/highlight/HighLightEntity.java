/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.baseImpl.graphics.isometric.highlight;

import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import org.apache.log4j.Logger;

public class HighLightEntity
extends Entity3D {
    private static final Logger a = Logger.getLogger(HighLightEntity.class);
    public boolean aRc = false;
    private static final int qL = HighLightEntity.L(HighLightEntity.class);

    public void clear() {
        super.clear();
        this.aRc = false;
    }

    public static int it() {
        return qL;
    }
}

