/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.opengl.text;

import com.ankamagames.framework.graphics.engine.text.GeometryText;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import javax.media.opengl.GL;

public class GLGeometryText
extends GeometryText {
    private af_1 adY;
    private int[] ceg = new int[4];
    private int aNV = 0;
    private int aNW = 0;
    private float ceh = 0.0f;
    private float cei = 0.0f;
    private static Matrix44 aMI = new Matrix44();
    private static Matrix44 aNN = new Matrix44();

    public int g(String string) {
        return this.adY.g(string);
    }

    public int h(String string) {
        return this.adY.h(string);
    }

    public adz_1 aV(String string) {
        return new adz_1(this.g(string), this.h(string));
    }

    public void a(ma_1 ma_12) {
        if (ma_12 == this.Jj) {
            return;
        }
        if (ma_12.getClass() == abQ.class) {
            abQ abQ2 = (abQ)ma_12;
            if (!alk_1.aAY().b(abQ2)) {
                alk_1.aAY().c(abQ2);
            }
            this.adY = alk_1.aAY().a((abQ)ma_12);
        } else {
            af_1 af_12 = aFM.b(ma_12);
            if (af_12 == null) assert (false) : "Currently not implemented";
            this.adY = af_12;
        }
        super.a(ma_12);
    }

    public void a(float f) {
    }

    public void a(db_2 db_22) {
        if (this.adY == null) {
            return;
        }
        int n2 = this.c.size();
        if (n2 == 0) {
            return;
        }
        wq_1.Dn().a(this);
        this.p(db_22);
        this.adY.setColor(this.aZ.Cp(), this.aZ.Cq(), this.aZ.Cr(), this.aZ.getAlpha());
        if (this.adY instanceof oa_0 || this.adY instanceof aAU) {
            aMI.d(db_22.LT());
            aNN.d(db_22.LU());
            db_22.c(Matrix44.bEn);
            db_22.b(Matrix44.bEn);
        }
        int n3 = (int)((this.Gv + this.Jl.getX()) * this.eV + this.ceh);
        int n4 = (int)((this.Gw + this.Jl.getY()) * this.eW + this.cei + (float)(n2 * this.Jk));
        this.adY.beginRendering(this.aNV, this.aNW);
        for (int j = 0; j < n2; ++j) {
            char[] cArray = (char[])this.c.get(j);
            n4 = (int)((float)n4 - (float)this.Jk * this.Gx);
            this.adY.a(cArray, n3, n4, this.Gx);
        }
        this.adY.endRendering();
        if (this.adY instanceof oa_0 || this.adY instanceof aAU) {
            db_22.b(aMI);
            db_22.c(aNN);
        }
    }

    protected void af() {
    }

    protected void ag() {
    }

    private void p(db_2 db_22) {
        GL gL = (GL)db_22.LV();
        float[] fArray = db_22.LU().Pn();
        float f = fArray[12];
        float f2 = fArray[13];
        gL.glGetIntegerv(2978, this.ceg, 0);
        this.aNV = this.ceg[2] - this.ceg[0];
        this.aNW = this.ceg[3] - this.ceg[1];
        this.ceh = (float)this.aNV / 2.0f + f;
        this.cei = (float)this.aNW / 2.0f + f2;
    }
}

