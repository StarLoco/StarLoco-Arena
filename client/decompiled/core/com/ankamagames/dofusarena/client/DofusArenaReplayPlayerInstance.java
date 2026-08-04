/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client;

import org.apache.log4j.Logger;

public class DofusArenaReplayPlayerInstance
extends zh_1 {
    private static Logger a = Logger.getLogger(DofusArenaReplayPlayerInstance.class);
    private static DofusArenaReplayPlayerInstance bsg = new DofusArenaReplayPlayerInstance();
    private String eA;

    public static DofusArenaReplayPlayerInstance XY() {
        return bsg;
    }

    public void start() {
        tu_1 tu_12 = new tu_1(this.eA);
        tu_12.zt();
    }

    public void fg(String string) {
        this.eA = string;
    }

    public void cleanUp() {
        super.cleanUp();
    }

    protected void yn() {
        super.yn();
    }

    protected void yo() {
        super.yo();
        add_1.aOG().l("dofusarena", avv_0.class);
    }

    protected void XZ() {
    }
}

