/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.geometry;

import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import java.util.Arrays;

public abstract class GeometrySprite
extends GeometryMesh {
    private static final float[] bUH = new float[16];
    private int fb;
    private int fc;
    private float bsA;
    private float bsB;
    private boolean bUI;
    public static final ams_1 bUJ;
    private static final float[] bUK;

    public GeometrySprite() {
        this.aA = new VertexBufferPCT(4);
        this.az = bUJ;
        this.aB = jB.AY;
        this.e(4);
        Arrays.fill(this.aE, 1.0f);
        this.bUI = true;
    }

    public final void k(float f, float f2, float f3, float f4) {
        this.a(f, f2, f3, f4, xd_1.azj);
    }

    public final void a(float f, float f2, float f3, float f4, xd_1 xd_12) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        switch (xd_12) {
            case azj: {
                this.aA.a(un.apO.ordinal(), f2, f);
                this.aA.a(un.apN.ordinal(), f2, f3);
                this.aA.a(un.apQ.ordinal(), f4, f);
                this.aA.a(un.apP.ordinal(), f4, f3);
                break;
            }
            case azk: {
                this.aA.a(un.apO.ordinal(), f2, f3);
                this.aA.a(un.apN.ordinal(), f4, f3);
                this.aA.a(un.apQ.ordinal(), f2, f);
                this.aA.a(un.apP.ordinal(), f4, f);
                break;
            }
            case azl: {
                this.aA.a(un.apO.ordinal(), f4, f3);
                this.aA.a(un.apN.ordinal(), f4, f);
                this.aA.a(un.apQ.ordinal(), f2, f3);
                this.aA.a(un.apP.ordinal(), f2, f);
                break;
            }
            case azm: {
                this.aA.a(un.apO.ordinal(), f4, f);
                this.aA.a(un.apN.ordinal(), f2, f);
                this.aA.a(un.apQ.ordinal(), f4, f3);
                this.aA.a(un.apP.ordinal(), f2, f3);
            }
        }
    }

    public final void x(float f, float f2) {
        this.bsA = f;
        this.bsB = f2;
        this.Ym();
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        for (int j = 0; j < 4; ++j) {
            this.aE[j * 4] = f;
            this.aE[j * 4 + 1] = f2;
            this.aE[j * 4 + 2] = f3;
            this.aE[j * 4 + 3] = f4;
        }
        GeometrySprite.bUH[8] = GeometrySprite.bUH[12] = f;
        GeometrySprite.bUH[4] = GeometrySprite.bUH[12];
        GeometrySprite.bUH[0] = GeometrySprite.bUH[12];
        GeometrySprite.bUH[9] = GeometrySprite.bUH[13] = f2;
        GeometrySprite.bUH[5] = GeometrySprite.bUH[13];
        GeometrySprite.bUH[1] = GeometrySprite.bUH[13];
        GeometrySprite.bUH[10] = GeometrySprite.bUH[14] = f3;
        GeometrySprite.bUH[6] = GeometrySprite.bUH[14];
        GeometrySprite.bUH[2] = GeometrySprite.bUH[14];
        GeometrySprite.bUH[11] = GeometrySprite.bUH[15] = f4;
        GeometrySprite.bUH[7] = GeometrySprite.bUH[15];
        GeometrySprite.bUH[3] = GeometrySprite.bUH[15];
        this.aA.i(bUH);
        this.bUI = true;
    }

    public final void a(un un2, float f, float f2, float f3, float f4) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        int n2 = un2.ordinal();
        this.aE[n2 * 4] = f;
        this.aE[n2 * 4 + 1] = f2;
        this.aE[n2 * 4 + 2] = f3;
        this.aE[n2 * 4 + 3] = f4;
        this.aA.a(un2.ordinal(), f, f2, f3, f4);
        this.bUI = false;
    }

    public final void a(aPb aPb2) {
        if (!this.bUI) {
            this.a(un.apO, aPb2);
            this.a(un.apN, aPb2);
            this.a(un.apQ, aPb2);
            this.a(un.apP, aPb2);
        } else {
            this.b(aPb2);
        }
    }

    private void b(aPb aPb2) {
        float[] fArray = aPb2.aYK();
        float[] fArray2 = aPb2.aYL();
        float f = this.aE[0] * fArray[0] + fArray2[0];
        float f2 = this.aE[1] * fArray[1] + fArray2[1];
        float f3 = this.aE[2] * fArray[2] + fArray2[2];
        float f4 = this.aE[3] * fArray[3] + fArray2[3];
        GeometrySprite.bUH[8] = GeometrySprite.bUH[12] = f;
        GeometrySprite.bUH[4] = GeometrySprite.bUH[12];
        GeometrySprite.bUH[0] = GeometrySprite.bUH[12];
        GeometrySprite.bUH[9] = GeometrySprite.bUH[13] = f2;
        GeometrySprite.bUH[5] = GeometrySprite.bUH[13];
        GeometrySprite.bUH[1] = GeometrySprite.bUH[13];
        GeometrySprite.bUH[10] = GeometrySprite.bUH[14] = f3;
        GeometrySprite.bUH[6] = GeometrySprite.bUH[14];
        GeometrySprite.bUH[2] = GeometrySprite.bUH[14];
        GeometrySprite.bUH[11] = GeometrySprite.bUH[15] = f4;
        GeometrySprite.bUH[7] = GeometrySprite.bUH[15];
        GeometrySprite.bUH[3] = GeometrySprite.bUH[15];
        this.aA.l(bUH);
    }

    public void a(un un2, aPb aPb2) {
        int n2 = un2.ordinal();
        float[] fArray = aPb2.aYK();
        float[] fArray2 = aPb2.aYL();
        float f = this.aE[n2 * 4] * fArray[0] + fArray2[0];
        float f2 = this.aE[n2 * 4 + 1] * fArray[1] + fArray2[1];
        float f3 = this.aE[n2 * 4 + 2] * fArray[2] + fArray2[2];
        float f4 = this.aE[n2 * 4 + 3] * fArray[3] + fArray2[3];
        this.aA.a(n2, f, f2, f3, f4);
    }

    public void setSize(int n2, int n3) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        this.fb = n2;
        this.fc = n3;
        this.Ym();
    }

    public final float HC() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bsB + (float)this.fb * 0.5f;
    }

    public final float HD() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bsA - (float)this.fc * 0.5f;
    }

    public final float Hw() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bsB;
    }

    public final float Hx() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bsB + (float)this.fb;
    }

    public final float Hy() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bsA;
    }

    public final float Hz() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bsA - (float)this.fc;
    }

    public final float HA() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return (float)this.fb * 0.5f;
    }

    public final float HB() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return (float)this.fc * 0.5f;
    }

    public final int getWidth() {
        return this.fb;
    }

    public final int getHeight() {
        return this.fc;
    }

    public abstract void a(db_2 var1);

    protected void delete() {
        this.az = null;
        super.delete();
    }

    public void af() {
        Arrays.fill(this.aE, 1.0f);
        this.bUI = true;
    }

    public void ag() {
    }

    private void Ym() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        float f = this.bsA - (float)this.fc;
        float f2 = this.bsB + (float)this.fb;
        GeometrySprite.bUK[0] = this.bsB;
        GeometrySprite.bUK[1] = f;
        GeometrySprite.bUK[2] = this.bsB;
        GeometrySprite.bUK[3] = this.bsA;
        GeometrySprite.bUK[4] = f2;
        GeometrySprite.bUK[5] = f;
        GeometrySprite.bUK[6] = f2;
        GeometrySprite.bUK[7] = this.bsA;
        this.aA.f(un.apN.ordinal(), bUK);
    }

    static {
        bUK = new float[8];
        bUJ = new ams_1(4);
        bUJ.cn(0, 0);
        bUJ.cn(1, 1);
        bUJ.cn(2, 3);
        bUJ.cn(3, 2);
    }
}

