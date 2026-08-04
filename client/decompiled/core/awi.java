/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class awi {
    private final ArrayList uA = new ArrayList();
    private final rd_0 dhw;
    private int EN;
    private int EO = Integer.MAX_VALUE;
    private int EP;
    private int EQ;
    private int dhx;
    private int dhy;
    private float bck;
    private akq_1 arn;

    public awi(rd_0 rd_02) {
        this.EN = Integer.MAX_VALUE;
        this.EQ = Integer.MIN_VALUE;
        this.EP = Integer.MIN_VALUE;
        this.dhw = rd_02;
    }

    public rd_0 aJp() {
        return this.dhw;
    }

    public void a(ahp_0 ahp_02) {
        this.uA.add(ahp_02);
    }

    public void b(ahp_0 ahp_02) {
        this.uA.remove(ahp_02);
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public void bn(float f) {
        this.bck = f;
    }

    public vb_1 bZ(int n2, int n3) {
        for (int j = this.uA.size() - 1; j >= 0; --j) {
            vb_1 vb_12;
            ahp_0 ahp_02 = (ahp_0)this.uA.get(j);
            if (!ahp_02.isVisible() || (vb_12 = ahp_02.bZ(n2, n3)) == null) continue;
            return vb_12;
        }
        return null;
    }

    public void aJq() {
        for (int j = this.uA.size() - 1; j >= 0; --j) {
            ahp_0 ahp_02 = (ahp_0)this.uA.get(j);
            if (!ahp_02.isVisible()) continue;
            this.EN = Math.min(this.EN, ahp_02.getMinX());
            this.EO = Math.min(this.EO, ahp_02.getMinY());
            this.EP = Math.max(this.EP, ahp_02.aTS());
            this.EQ = Math.max(this.EQ, ahp_02.aTT());
        }
    }

    public float[] aJr() {
        ps_0 ps_02 = new ps_0();
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            ahp_0 ahp_02 = (ahp_0)this.uA.get(j);
            if (!ahp_02.isVisible()) continue;
            for (int i2 = ahp_02.getMinX() - 1; i2 < ahp_02.aTS() + 1; ++i2) {
                for (int i3 = ahp_02.getMinY() - 1; i3 < ahp_02.aTT() + 1; ++i3) {
                    boolean bl2;
                    vb_1 vb_12 = ahp_02.bZ(i2, i3);
                    boolean bl3 = ahp_02.bZ(i2, i3 + 1) == null && this.bZ(i2, i3 + 1) != null;
                    boolean bl4 = bl2 = ahp_02.bZ(i2 + 1, i3) == null && this.bZ(i2 + 1, i3) != null;
                    if (vb_12 == null) continue;
                    if (bl3) {
                        ps_02.add(vb_12.bSG);
                        ps_02.add(vb_12.bSH);
                        ps_02.add(vb_12.bSM);
                        ps_02.add(vb_12.bSN);
                    }
                    if (!bl2) continue;
                    ps_02.add(vb_12.bSK);
                    ps_02.add(vb_12.bSL);
                    ps_02.add(vb_12.bSM);
                    ps_02.add(vb_12.bSN);
                }
            }
        }
        return ps_02.uD();
    }

    public float[] aJs() {
        float f = 1.0f / this.bck;
        float f2 = f / 2.0f;
        ps_0 ps_02 = new ps_0();
        for (int j = this.EN; j < this.EP + 1; ++j) {
            for (int i2 = this.EO; i2 < this.EQ + 1; ++i2) {
                vb_1 vb_12 = this.bZ(j, i2);
                if (vb_12 == null) continue;
                vb_1 vb_13 = this.bZ(j + 1, i2);
                vb_1 vb_14 = this.bZ(j, i2 + 1);
                vb_1 vb_15 = this.bZ(j, i2 - 1);
                vb_1 vb_16 = this.bZ(j - 1, i2);
                vb_1 vb_17 = this.bZ(j + 1, i2 - 1);
                vb_1 vb_18 = this.bZ(j + 1, i2 + 1);
                vb_1 vb_19 = this.bZ(j - 1, i2 - 1);
                vb_1 vb_110 = this.bZ(j - 1, i2 + 1);
                float f3 = vb_12.bSG + 2.0f * f + (vb_16 != null ? -f : 0.0f) + (vb_14 != null ? -f : 0.0f);
                float f4 = vb_12.bSH + (vb_16 != null ? f2 : 0.0f) + (vb_14 != null ? -f2 : 0.0f);
                float f5 = vb_12.bSK - 2.0f * f - (vb_13 != null ? -f : 0.0f) - (vb_15 != null ? -f : 0.0f);
                float f6 = vb_12.bSL - (vb_13 != null ? f2 : 0.0f) - (vb_15 != null ? -f2 : 0.0f);
                float f7 = vb_12.bSI + (vb_16 != null ? -f : 0.0f) + (vb_15 != null ? f : 0.0f);
                float f8 = vb_12.bSJ - 2.0f * f2 + (vb_16 != null ? f2 : 0.0f) + (vb_15 != null ? f2 : 0.0f);
                float f9 = vb_12.bSM + (vb_13 != null ? f : 0.0f) + (vb_14 != null ? -f : 0.0f);
                float f10 = vb_12.bSN + 2.0f * f2 - (vb_13 != null ? f2 : 0.0f) - (vb_14 != null ? f2 : 0.0f);
                if (vb_15 == null) {
                    if (vb_17 != null && vb_13 != null) {
                        f5 = vb_12.bSK;
                        f6 = vb_12.bSL - 2.0f * f2;
                    }
                    if (vb_19 != null && vb_16 != null) {
                        f7 = vb_12.bSI - 2.0f * f;
                        f8 = vb_12.bSJ;
                    }
                }
                if (vb_14 == null) {
                    if (vb_110 != null && vb_16 != null) {
                        f3 = vb_12.bSG;
                        f4 = vb_12.bSH + 2.0f * f2;
                    }
                    if (vb_18 != null && vb_13 != null) {
                        f9 = vb_12.bSM + 2.0f * f;
                        f10 = vb_12.bSN;
                    }
                }
                if (vb_16 == null) {
                    if (vb_19 != null && vb_15 != null) {
                        f7 = vb_12.bSI + 2.0f * f;
                        f8 = vb_12.bSJ;
                    }
                    if (vb_110 != null && vb_14 != null) {
                        f3 = vb_12.bSG;
                        f4 = vb_12.bSH - 2.0f * f2;
                    }
                }
                if (vb_13 == null) {
                    if (vb_17 != null && vb_15 != null) {
                        f5 = vb_12.bSK;
                        f6 = vb_12.bSL + 2.0f * f2;
                    }
                    if (vb_18 != null && vb_14 != null) {
                        f9 = vb_12.bSM - 2.0f * f;
                        f10 = vb_12.bSN;
                    }
                }
                if (vb_14 == null) {
                    ps_02.add(f3);
                    ps_02.add(f4);
                    ps_02.add(f9);
                    ps_02.add(f10);
                }
                if (vb_15 == null) {
                    ps_02.add(f7);
                    ps_02.add(f8);
                    ps_02.add(f5);
                    ps_02.add(f6);
                }
                if (vb_13 == null) {
                    ps_02.add(f5);
                    ps_02.add(f6);
                    ps_02.add(f9);
                    ps_02.add(f10);
                }
                if (vb_16 != null) continue;
                ps_02.add(f7);
                ps_02.add(f8);
                ps_02.add(f3);
                ps_02.add(f4);
            }
        }
        return ps_02.uD();
    }

    public vP getColor() {
        return this.dhw.Sh();
    }

    public int getLineWidth() {
        return this.dhw.Sj();
    }

    public akq_1 getPixmap() {
        return this.arn;
    }

    public void setPixmap(akq_1 akq_12) {
        this.arn = akq_12;
    }

    public int aJt() {
        return this.dhx;
    }

    public int aJu() {
        return this.dhy;
    }

    public void cleanUp() {
        for (int j = this.uA.size() - 1; j >= 0; --j) {
            ahp_0 ahp_02 = (ahp_0)this.uA.get(j);
            if (ahp_02 == null || ahp_02.getPixmap() == null || ahp_02.getPixmap().jI() == null) continue;
            ahp_02.cleanUp();
        }
    }
}

