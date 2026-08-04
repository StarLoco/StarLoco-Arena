/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.text;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.text.GeometryBackground;
import com.ankamagames.framework.graphics.engine.text.GeometryText;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;

public class EntityText
extends Entity {
    protected agu_0 Jl = new agu_0();
    protected String IJ;
    protected int aKW;
    protected int aKX = 0;
    protected int aMx = 0;
    protected int aMy = 0;
    protected int aMz;
    protected ArrayList c = new ArrayList(32);
    protected int aMA;
    protected int aMB = 0;
    protected int aMC = 0;
    private int aMD = 2;
    private GeometryText aME = null;
    private GeometryBackground aMF = null;
    private boolean aMG = false;
    private float aaw = 1.0f;
    private static final Matrix44 aMH = (Matrix44)yW.FL().a(Matrix44.it(), Matrix44.class);
    private static final Matrix44 aMI = (Matrix44)yW.FL().a(Matrix44.it(), Matrix44.class);

    public EntityText() {
        this.aKW = Integer.MAX_VALUE;
        avz avz2 = new avz();
        avz2.OH();
        this.aUM().a(avz2);
    }

    public final void a(GeometryText geometryText) {
        geometryText.HE();
        if (this.aME != null) {
            this.aME.HF();
        }
        this.aME = geometryText;
    }

    public final void a(GeometryBackground geometryBackground) {
        if (geometryBackground != null) {
            geometryBackground.HE();
        }
        if (this.aMF != null) {
            this.aMF.HF();
        }
        this.aMF = geometryBackground;
    }

    public final void n(float f, float f2, float f3, float f4) {
        if (this.aMF == null) {
            return;
        }
        this.aMF.setColor(f, f2, f3, f4);
    }

    public final void c(float f, float f2) {
        if (this.aME != null) {
            this.aME.c(f, f2);
        }
        if (this.aMF != null) {
            this.aMF.c(f, f2);
        }
    }

    public final GeometryText KV() {
        return this.aME;
    }

    public final GeometryBackground KW() {
        return this.aMF;
    }

    public final void a(float f) {
    }

    public final void d(db_2 db_22) {
        if (!this.isVisible()) {
            return;
        }
        if (this.aMG) {
            this.dD(this.IJ);
            this.aMG = false;
        }
        Matrix44 matrix44 = db_22.LU();
        aMH.d(matrix44);
        float[] fArray = matrix44.Pn();
        float f = fArray[0];
        float f2 = fArray[5];
        matrix44.d(0, 1.0f);
        matrix44.d(1, 0.0f);
        matrix44.d(4, 0.0f);
        matrix44.d(5, 1.0f);
        matrix44.d(12, (float)Math.floor(fArray[12]));
        matrix44.d(13, (float)Math.floor(fArray[13]));
        aMI.d(this.aUM().ki());
        float f3 = aMI.Pn()[12];
        float f4 = aMI.Pn()[13];
        aMI.e((float)Math.floor(f3 * f), (float)Math.floor(f4 * f2), -1.0f);
        db_22.b(aMI);
        db_22.c(matrix44);
        this.cws.b(db_22);
        int n2 = (int)((float)this.KX() * (1.0f - this.aaw) / f) / 2;
        int n3 = -((int)((float)this.KY() * (1.0f - this.aaw) / f2)) / 2;
        vo_1.aik().cu(false);
        if (this.aMF != null) {
            this.aMF.setWidth((int)((float)Math.max(this.KX(), this.aKX) * f));
            this.aMF.setHeight((int)((float)Math.max(this.KY(), this.aMx) * f2));
            this.aMF.a(db_22);
            n2 = (int)((float)n2 + (this.aMF.ci() + (float)this.aMB) / f);
            n3 = (int)((float)n3 + (this.aMF.cl() + (float)this.aMC) / f2);
        }
        this.aME.i(n2, n3);
        db_22.c(aMH);
        db_22.b(this.aUM().ki());
        this.aME.e(this.c);
        this.aME.cf(this.aMA);
        this.aME.a(db_22);
        this.aME.setScale(this.aaw);
        this.cwt.b(db_22);
    }

    public final void a(agu_0 agu_02) {
        avz avz2 = (avz)this.aUM().aI(0);
        avz2.e(agu_02);
        this.aUM().b(0, avz2);
        this.Jl.a(agu_02);
        this.aME.a(agu_02);
    }

    public final void aj(int n2, int n3) {
        this.aMB = n2;
        this.aMC = n3;
    }

    public void a(ma_1 ma_12) {
        this.aME.a(ma_12);
        this.aMG = true;
    }

    public final void setText(String string) {
        this.IJ = string;
        this.aMG = true;
    }

    public final String getText() {
        return this.IJ;
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        this.aME.setColor(f, f2, f3, f4);
    }

    public final vP getColor() {
        return this.aME.getColor();
    }

    public final void setMaxWidth(int n2) {
        if (n2 < 0) {
            n2 = Integer.MAX_VALUE;
        }
        this.aKW = n2;
        this.aMG = true;
    }

    public final int KX() {
        return this.aMy;
    }

    public final int KY() {
        return this.aMz;
    }

    public final int getMinWidth() {
        return this.aKX;
    }

    public int getMaxWidth() {
        return this.aKW;
    }

    public final void setMinWidth(int n2) {
        this.aKX = n2;
    }

    public final int KZ() {
        return this.aMx;
    }

    public final void fa(int n2) {
        this.aMx = n2;
    }

    public float getZoom() {
        return this.aaw;
    }

    public void setZoom(float f) {
        this.aaw = f;
    }

    protected void af() {
    }

    protected void ag() {
        this.delete();
    }

    protected void dD(String string) {
        this.c.clear();
        this.aMy = 0;
        this.aMz = 0;
        if (string == null || string.length() == 0) {
            this.aMA = 0;
            return;
        }
        if (this.aKW == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(128);
        StringBuilder stringBuilder2 = new StringBuilder(128);
        char[] cArray = string.toCharArray();
        int n2 = 0;
        while (n2 < cArray.length) {
            String string2;
            StringBuilder stringBuilder3 = new StringBuilder(128);
            adz_1 adz_12 = new adz_1(0, 0);
            boolean bl2 = false;
            boolean bl3 = false;
            while (adz_12.getX() < this.aKW && n2 < cArray.length && !bl2) {
                char c;
                char c2 = cArray[n2++];
                boolean bl4 = true;
                if (n2 < cArray.length && ((c = cArray[n2]) == '.' || c == '?' || c == '!' || c == ':' || c == ';' || c == ',')) {
                    bl4 = false;
                }
                if (c2 == ' ' && bl4) {
                    stringBuilder3.append(stringBuilder2.toString()).append(' ');
                    stringBuilder2 = new StringBuilder(128);
                    bl3 = true;
                } else if (c2 == '\n') {
                    bl2 = true;
                    stringBuilder3.append(stringBuilder2.toString());
                    stringBuilder2 = new StringBuilder(128);
                    bl3 = true;
                } else {
                    stringBuilder2.append(c2);
                }
                if (bl2) continue;
                stringBuilder.append(c2);
                adz_12 = this.aME.aV(stringBuilder.toString());
            }
            if (!bl3) {
                stringBuilder3.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder(128);
            }
            if (n2 >= cArray.length) {
                stringBuilder3.append(stringBuilder2.toString());
            }
            stringBuilder = new StringBuilder(128);
            if (stringBuilder2.length() != 0) {
                stringBuilder.append((CharSequence)stringBuilder2);
            }
            if ((string2 = stringBuilder3.toString()).length() < 0) continue;
            adz_12 = this.aME.aV(string2);
            this.c.add(stringBuilder3.toString().toCharArray());
            this.aMy = Math.max(this.aMy, adz_12.getX());
            this.aMz += adz_12.getY();
        }
        this.aMA = this.aMz / this.c.size() + this.aMD;
        this.aMz = this.aMA * this.c.size();
    }
}

