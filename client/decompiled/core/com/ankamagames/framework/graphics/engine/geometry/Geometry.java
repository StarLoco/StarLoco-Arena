/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.geometry;

public abstract class Geometry
extends ams_2 {
    protected air dA = air.cyd;
    protected air dB = air.cye;

    public void a(air air2, air air3) {
        this.dA = air2;
        this.dB = air3;
    }

    public final air bW() {
        return this.dA;
    }

    public final air bX() {
        return this.dB;
    }

    public abstract void setColor(float var1, float var2, float var3, float var4);

    public void a(aPb aPb2) {
    }

    public abstract void a(float var1);

    public abstract void a(db_2 var1);

    protected void delete() {
        super.delete();
        this.dA = air.cyd;
        this.dB = air.cye;
    }
}

