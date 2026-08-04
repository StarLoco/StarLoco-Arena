/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.text;

import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.geometry.Geometry;

public abstract class GeometryBackground
extends Geometry {
    protected float eV = 1.0f;
    protected float eW = 1.0f;
    protected short[] eX;
    protected short[] eY;
    protected VertexBufferPCT aA;
    protected ams_1 az;
    protected VertexBufferPCT eZ;
    protected ams_1 fa;
    private int fb;
    private int fc;
    private final vP aZ = new vP(1.0f, 1.0f, 1.0f, 0.7f);
    private float fd = 0.0f;
    private float fe = 0.0f;
    private float ff = 0.0f;
    private float fg = 0.0f;
    private float fh = 1.0f;
    private final vP fi = new vP(0.06f, 0.04f, 0.03f, 0.4f);
    private float[][] fj;
    private float[][] fk;
    private boolean fl;

    public void a(aij_1 aij_12) {
        assert (false) : "Currently not implemented";
    }

    public void b(acf acf2) {
        assert (false) : "Currently not implemented";
    }

    public void a(float f) {
    }

    public final int getWidth() {
        return this.fb;
    }

    public final void setWidth(int n2) {
        if (n2 == this.fb) {
            return;
        }
        this.fb = n2;
        this.fl = true;
    }

    public final int getHeight() {
        return this.fc;
    }

    public final void setHeight(int n2) {
        if (n2 == this.fc) {
            return;
        }
        this.fc = n2;
        this.fl = true;
    }

    public final float getBorderWidth() {
        return this.fh;
    }

    public final void setBorderWidth(float f) {
        if (this.fh == f) {
            return;
        }
        this.fh = f;
        this.fl = true;
    }

    public final vP getColor() {
        return this.aZ;
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        this.aZ.h(f, f2, f3, f4);
        this.fl = true;
    }

    public final vP getBorderColor() {
        return this.fi;
    }

    public final void b(float f, float f2, float f3, float f4) {
        this.fi.h(f, f2, f3, f4);
        this.fl = true;
    }

    public final void c(float f, float f2) {
        this.eV = f;
        this.eW = f2;
        this.fl = true;
    }

    public final void a(float[][] fArray, float[][] fArray2) {
        assert (fArray != null) : "positionOffsets can't be null";
        assert (fArray2 != null) : "sizeMultipliers can't be null";
        assert (fArray.length == fArray2.length) : "positionOffsets and sizeMultipliers must have the same size";
        assert (fArray.length > 0) : "positionOffsets must be greater than zero";
        assert (fArray2.length > 0) : "sizeMultipliers must be greater than zero";
        this.fj = fArray;
        this.fk = fArray2;
        this.fl = true;
    }

    public final void e(short[] sArray) {
        this.eY = sArray;
    }

    public final void f(short[] sArray) {
        this.eX = sArray;
    }

    public final void e(float f, float f2, float f3, float f4) {
        if (this.fd == f && this.fe == f2 && this.ff == f3 && this.fg == f4) {
            return;
        }
        this.fd = f;
        this.fe = f2;
        this.ff = f3;
        this.fg = f4;
        this.fl = true;
    }

    public final float ci() {
        return this.fd;
    }

    public final float cj() {
        return this.fe;
    }

    public final float ck() {
        return this.ff;
    }

    public final float cl() {
        return this.fg;
    }

    protected void clean() {
        this.cn();
        this.fl = true;
    }

    protected void cm() {
        if (!this.fl) {
            return;
        }
        this.fl = false;
        if (this.az == null) {
            this.az = new ams_1(this.eX.length);
        } else if (this.az.aWX() < this.eX.length) {
            this.az.HF();
            this.az = new ams_1(this.eX.length);
        }
        if (this.aA == null) {
            this.aA = new VertexBufferPCT(this.fj.length);
        } else if (this.aA.fp() < this.fj.length) {
            this.aA.HF();
            this.aA = new VertexBufferPCT(this.fj.length);
        }
        if (this.fa == null) {
            this.fa = new ams_1(this.eY.length);
        } else if (this.fa.aWX() < this.eY.length) {
            this.fa.HF();
            this.fa = new ams_1(this.eY.length);
        }
        if (this.eZ == null) {
            this.eZ = new VertexBufferPCT(this.fj.length);
        } else if (this.eZ.fp() < this.fj.length) {
            this.eZ.HF();
            this.eZ = new VertexBufferPCT(this.fj.length);
        }
        float f = (float)this.fb / this.eV;
        float f2 = (float)this.fc / this.eW;
        this.az.c(this.eX, 0, this.eX.length);
        this.fa.c(this.eY, 0, this.eY.length);
        this.aA.clear();
        this.eZ.clear();
        for (int j = 0; j < this.fj.length; ++j) {
            float f3 = this.fj[j][0] + this.fk[j][0] * (f + this.fd + this.fe);
            float f4 = this.fj[j][1] + this.fk[j][1] * (f2 + this.ff + this.fg);
            this.aA.b(j, f3, f4);
            this.aA.a(j, this.aZ.Cp(), this.aZ.Cq(), this.aZ.Cr(), this.aZ.getAlpha());
            this.aA.a(j, 0.0f, 0.0f);
            this.eZ.b(j, f3, f4);
            this.eZ.a(j, this.fi.Cp(), this.fi.Cq(), this.fi.Cr(), this.fi.getAlpha());
            this.eZ.a(j, 0.0f, 0.0f);
        }
        this.aA.dz(this.fj.length);
        this.eZ.dz(this.fj.length);
    }

    protected void delete() {
        this.cn();
    }

    private void cn() {
        if (this.aA != null) {
            this.aA.HF();
            this.aA = null;
        }
        if (this.az != null) {
            this.az.HF();
            this.az = null;
        }
        if (this.eZ != null) {
            this.eZ.HF();
            this.eZ = null;
        }
        if (this.fa != null) {
            this.fa.HF();
            this.fa = null;
        }
    }
}

