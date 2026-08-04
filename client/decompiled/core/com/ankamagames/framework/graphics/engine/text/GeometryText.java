/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.text;

import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import java.util.ArrayList;

public abstract class GeometryText
extends Geometry {
    protected ma_1 Jj;
    protected vP aZ;
    protected ArrayList c;
    protected int Jk;
    protected agu_0 Jl = new agu_0(0.0f, 0.0f, 0.0f, 1.0f);
    protected float Gv = 0.0f;
    protected float Gw = 0.0f;
    protected float eV = 1.0f;
    protected float eW = 1.0f;
    protected float Gx = 1.0f;

    public GeometryText() {
        this.aZ = new vP(vP.atM);
    }

    public void a(aij_1 aij_12) {
        assert (false) : "Currently not implemented";
    }

    public void b(acf acf2) {
        assert (false) : "Currently not implemented";
    }

    public abstract int g(String var1);

    public abstract int h(String var1);

    public abstract adz_1 aV(String var1);

    public float getScale() {
        return this.Gx;
    }

    public void setScale(float f) {
        this.Gx = f;
    }

    public final void c(float f, float f2) {
        this.eV = f;
        this.eW = f2;
    }

    public final float qZ() {
        return this.eV;
    }

    public final float ra() {
        return this.eW;
    }

    public void a(ma_1 ma_12) {
        this.Jj = ma_12;
    }

    public final void setColor(int n2) {
        this.aZ.set(n2);
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        this.aZ.h(f, f2, f3, f4);
    }

    public final vP getColor() {
        return this.aZ;
    }

    public final void e(ArrayList arrayList) {
        this.c = arrayList;
    }

    public final void cf(int n2) {
        this.Jk = n2;
    }

    public final void a(agu_0 agu_02) {
        this.Jl.a(agu_02);
    }

    public final float getOffsetX() {
        return this.Gv;
    }

    public final void setOffsetX(float f) {
        this.Gv = f;
    }

    public final float getOffsetY() {
        return this.Gw;
    }

    public final void setOffsetY(float f) {
        this.Gw = f;
    }

    public final void i(float f, float f2) {
        this.Gv = f;
        this.Gw = f2;
    }
}

