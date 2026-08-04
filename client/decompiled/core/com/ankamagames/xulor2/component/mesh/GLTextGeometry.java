/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.xulor2.component.mesh;

import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import java.awt.Color;
import java.util.ArrayList;
import javax.media.opengl.GL;

public final class GLTextGeometry
extends Geometry {
    public static final boolean beT = true;
    private static final nm_0 beU = nm_0.sl();
    private long beV = 0L;
    private static final int beW = 400;
    private ch_2 beX;
    private long beY = 0L;
    private boolean beZ = false;
    private boolean bfa = false;
    private boolean bfb = false;
    private vP AC = null;
    public static long bfc = 0L;
    private static final float[] bfd = new float[16];

    public void setTextBuilder(ch_2 ch_22) {
        this.beX = ch_22;
    }

    public void a(aij_1 aij_12) {
        assert (false) : "Currently not implemented";
    }

    public void b(acf acf2) {
        assert (false) : "Currently not implemented";
    }

    public void a(float f) {
    }

    public void setColor(float f, float f2, float f3, float f4) {
        assert (false) : "Currently not implemented";
    }

    public boolean Tn() {
        return this.bfa;
    }

    public void setBrightenColor(boolean bl2) {
        this.bfa = bl2;
    }

    public boolean To() {
        return this.bfb;
    }

    public void setDarkenColor(boolean bl2) {
        this.bfb = bl2;
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void bM(long l2) {
        this.beV = l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(db_2 db_22) {
        int n2;
        assert (db_22.vg() == arX.cQT);
        GL gL = (GL)db_22.LV();
        vo_1 vo_12 = vo_1.aik();
        ArrayList arrayList = this.beX.Jg();
        int n3 = arrayList.size();
        int n4 = this.beX.getOrientedHeight();
        int n5 = this.beX.Js();
        agV agV2 = add_1.aOG().aON();
        int n6 = (int)agV2.adF();
        int n7 = (int)agV2.adG();
        long l2 = this.beX.JB();
        int n8 = n2 = l2 > 0L && this.beV != -1L ? this.bN(l2) : -1;
        if (n2 != -1 && n2 >= this.beX.IW().getTextBuilder().Jd() && this.beV != -1L) {
            this.beX.IW().afh();
            this.beV = -1L;
        }
        int n9 = 0;
        block4: for (int j = this.beX.Jj(); j < n3; ++j) {
            aef_2 aef_22 = (aef_2)arrayList.get(j);
            float f = 0.0f;
            float f2 = aef_22.Dc() > 0 ? (float)(this.beX.getSize().width - aef_22.getWidth()) / (float)aef_22.Dc() : 0.0f;
            int n10 = aef_22.getY() - n5 + n4;
            if (n10 + aef_22.getHeight() <= 0) {
                vo_12.reset();
                vo_12.n(db_22);
                return;
            }
            ArrayList arrayList2 = aef_22.aQO();
            int n11 = 0;
            while (n11 < arrayList2.size()) {
                aFH aFH2 = (aFH)arrayList2.get(n11);
                switch (aFH2.aRY()) {
                    case NO: {
                        aFH aFH3 = (aNS)aFH2;
                        akq_1 akq_12 = ((aNS)aFH3).getPixmap();
                        if (akq_12 == null || akq_12.jI() == null) break;
                        int n12 = Math.round((float)(aef_22.getX() + aFH3.getX()) + f);
                        int n13 = ((aNS)aFH3).getWidth();
                        int n14 = ((aNS)aFH3).getImageHeight();
                        akq_12.jI().f(db_22);
                        if (this.AC != null) {
                            gL.glColor4f(this.AC.Cp(), this.AC.Cq(), this.AC.Cr(), this.AC.getAlpha());
                        } else {
                            gL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        }
                        vo_12.cu(true);
                        vo_12.n(db_22);
                        gL.glBegin(7);
                        gL.glTexCoord2f(akq_12.bsB, akq_12.bsD);
                        gL.glVertex2i(n12, n10);
                        gL.glTexCoord2f(akq_12.bsC, akq_12.bsD);
                        gL.glVertex2i(n12 + n13, n10);
                        gL.glTexCoord2f(akq_12.bsC, akq_12.bsA);
                        gL.glVertex2i(n12 + n13, n10 + n14);
                        gL.glTexCoord2f(akq_12.bsB, akq_12.bsA);
                        gL.glVertex2i(n12, n10 + n14);
                        gL.glEnd();
                        if (n9 + 1 > n2 && n2 != -1) break block4;
                        ++n9;
                        break;
                    }
                    case NN: {
                        aFH aFH3 = (wC)aFH2;
                        boolean bl2 = false;
                        if (((wC)aFH3).getText().length() + n9 > n2 && n2 != -1) {
                            bl2 = true;
                        }
                        af_1 af_12 = ((wC)aFH3).Da();
                        Color color = ((wC)aFH3).getColor();
                        int n14 = ((wC)aFH3).isUnderline();
                        boolean bl3 = ((wC)aFH3).Dd();
                        if (color == null) {
                            color = this.beX.IX();
                        }
                        if (af_12 == null) {
                            af_12 = this.beX.mH();
                        }
                        if (af_12 != null && color != null && aFH3.getWidth() != 0 && aFH3.getHeight() != 0) {
                            int n15;
                            int n16 = Math.round((float)(aef_22.getX() + aFH3.getX()) + f);
                            f += (float)((wC)aFH3).Dc() * f2;
                            if (af_12.aD() || this.bfa) {
                                color = color.brighter().brighter();
                            }
                            if (this.bfb) {
                                color = color.darker();
                            }
                            float f3 = (float)color.getAlpha() / 255.0f;
                            float f4 = (float)color.getRed() / 255.0f;
                            float f5 = (float)color.getGreen() / 255.0f;
                            float f6 = (float)color.getBlue() / 255.0f;
                            if (this.AC != null) {
                                f4 *= this.AC.Cp();
                                f5 *= this.AC.Cq();
                                f6 *= this.AC.Cr();
                                f3 *= this.AC.getAlpha();
                            }
                            af_12.begin3DRendering();
                            af_12.setColor(f4, f5, f6, f3);
                            af_12.a(((wC)aFH3).CY(), n16, bl2 ? n2 - n9 : ((wC)aFH3).getText().length(), n10 + aef_22.aQJ(), 1.0f, f2);
                            af_12.end3DRendering();
                            if (n14 != 0) {
                                vo_12.cu(false);
                                vo_12.n(db_22);
                                gL.glLineWidth(1.0f);
                                gL.glColor4f(f4, f5, f6, f3);
                                gL.glBegin(1);
                                gL.glVertex2i(n16, n10);
                                n15 = Math.round((float)aFH3.getWidth() + (float)((wC)aFH3).Dc() * f2);
                                gL.glVertex2i(n16 + n15, n10);
                                gL.glEnd();
                            }
                            if (bl3) {
                                vo_12.cu(false);
                                vo_12.n(db_22);
                                gL.glLineWidth(1.0f);
                                gL.glColor4f(f4, f5, f6, f3);
                                gL.glBegin(1);
                                gL.glVertex2i(n16, n10 + aef_22.getHeight() / 2);
                                n15 = Math.round((float)aFH3.getWidth() + (float)((wC)aFH3).Dc() * f2);
                                gL.glVertex2i(n16 + n15, n10 + aef_22.getHeight() / 2);
                                gL.glEnd();
                            }
                        }
                        n9 += ((wC)aFH3).getText().length();
                        if (bl2) break block4;
                    }
                }
                ++n11;
            }
            EM eM = aef_22.aQI();
            if (eM == null) continue;
            int n17 = aef_22.getX() + eM.getX();
            int n18 = eM.getWidth();
            if (eM.OA()) {
                if (System.currentTimeMillis() - this.beY >= 400L) {
                    this.beZ = !this.beZ;
                    this.beY = System.currentTimeMillis();
                }
            } else {
                this.beZ = true;
            }
            if (!this.beZ) continue;
            vo_12.cu(false);
            vo_12.a(air.cyg, air.cxZ);
            vo_12.n(db_22);
            gL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            gL.glBegin(7);
            gL.glVertex2i(n17, n10);
            gL.glVertex2i(n17 + n18, n10);
            gL.glVertex2i(n17 + n18, n10 + aef_22.getHeight());
            gL.glVertex2i(n17, n10 + aef_22.getHeight());
            gL.glEnd();
        }
        vo_12.reset();
        vo_12.n(db_22);
    }

    private int bN(long l2) {
        long l3 = System.currentTimeMillis();
        if (this.beV == 0L) {
            this.beV = l3;
        }
        long l4 = l3 - this.beV;
        return (int)(l4 / l2);
    }

    public void Tp() {
        this.beV = 0L;
    }

    protected void af() {
    }

    protected void ag() {
        this.delete();
        this.beX = null;
        this.AC = null;
        this.beV = 0L;
    }
}

