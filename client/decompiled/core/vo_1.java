/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from Vo
 */
public final class vo_1 {
    private static vo_1 bSn = new vo_1();
    private final int[] bSo;
    private final short[] bSp;
    private final float[] bSq;
    private final boolean[] bSr;
    private final int[] bSs;
    private final short[] bSt;
    private final float[] bSu;
    private final boolean[] bSv;
    private boolean bSw;

    public static vo_1 aik() {
        return bSn;
    }

    public void ail() {
        this.cr(true);
        this.a(air.cyh, air.cyh);
        this.at(1.0f);
        this.cs(false);
        this.au(1.0f);
        this.ct(false);
        this.b(1.0f);
        this.is(1);
        this.bm((short)0);
        this.a(jq_0.bmI);
        this.cv(false);
        this.w(0, 0, 0, 0);
        this.cx(false);
        this.ir(0);
        this.cu(true);
        this.b(qh.adx);
    }

    private void g(int n2, boolean bl2) {
        this.bb(n2, bl2 ? 1 : 0);
    }

    private void bb(int n2, int n3) {
        if (this.bSs[n2] == n3) {
            return;
        }
        if (this.bSw) {
            this.bSr[n2] = n3 == this.bSo[n2];
        }
        this.bSs[n2] = n3;
        this.bSv[n2] = false;
    }

    private void j(int n2, short s) {
        if (this.bSt[n2] == s) {
            return;
        }
        if (this.bSw) {
            this.bSr[n2] = s == this.bSp[n2];
        }
        this.bSt[n2] = s;
        this.bSv[n2] = false;
    }

    private void i(int n2, float f) {
        if (this.bSu[n2] == f) {
            return;
        }
        if (this.bSw) {
            this.bSr[n2] = f == this.bSq[n2];
        }
        this.bSu[n2] = f;
        this.bSv[n2] = false;
    }

    public final void cr(boolean bl2) {
        this.g(gf_1.bbM.ordinal(), bl2);
    }

    public final void a(air air2, air air3) {
        this.bb(gf_1.bbN.ordinal(), air2.vf());
        this.bb(gf_1.bbO.ordinal(), air3.vf());
    }

    public final void cs(boolean bl2) {
        this.g(gf_1.bbQ.ordinal(), bl2);
    }

    public final void ct(boolean bl2) {
        this.g(gf_1.bbT.ordinal(), bl2);
    }

    public final void b(qh qh2) {
        this.bb(gf_1.bcf.ordinal(), qh2.vf());
    }

    public final void cu(boolean bl2) {
        this.bb(gf_1.bcg.ordinal(), bl2 ? 1 : 0);
    }

    public final void cv(boolean bl2) {
        this.g(gf_1.bbY.ordinal(), bl2);
    }

    public final void cw(boolean bl2) {
        this.g(gf_1.bbU.ordinal(), bl2);
    }

    public final void cx(boolean bl2) {
        this.g(gf_1.bcd.ordinal(), bl2);
    }

    public final void a(jq_0 jq_02) {
        this.bb(gf_1.bbX.ordinal(), jq_02.vf());
    }

    public final void w(int n2, int n3, int n4, int n5) {
        this.bb(gf_1.bbZ.ordinal(), n2);
        this.bb(gf_1.bca.ordinal(), n3);
        this.bb(gf_1.bcb.ordinal(), n4);
        this.bb(gf_1.bcc.ordinal(), n5);
    }

    public final void ir(int n2) {
        this.bb(gf_1.bce.ordinal(), n2);
    }

    public final void at(float f) {
        this.i(gf_1.bbP.ordinal(), f);
    }

    public final void au(float f) {
        this.i(gf_1.bbR.ordinal(), f);
    }

    public final void b(float f) {
        this.i(gf_1.bbS.ordinal(), f);
    }

    public final void is(int n2) {
        this.bb(gf_1.bbV.ordinal(), n2);
    }

    public final void bm(short s) {
        this.j(gf_1.bbW.ordinal(), s);
    }

    public final void n(db_2 db_22) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        int n14 = gf_1.bcg.ordinal();
        if (!this.bSv[n14]) {
            this.bSv[n14] = true;
            if (this.bSs[n14] == 1) {
                gL.glEnable(3553);
            } else {
                gL.glDisable(3553);
            }
        }
        if (!this.bSv[n13 = gf_1.bcf.ordinal()]) {
            this.bSv[n13] = true;
            gL.glTexEnvi(8960, 8704, this.bSs[n13]);
        }
        if (!this.bSv[n12 = gf_1.bbM.ordinal()]) {
            this.bSv[n12] = true;
            if (this.bSs[n12] == 1) {
                gL.glEnable(3042);
            } else {
                gL.glDisable(3042);
            }
        }
        int n15 = gf_1.bbN.ordinal();
        int n16 = gf_1.bbO.ordinal();
        if (!this.bSv[n15] || !this.bSv[n16]) {
            this.bSv[n15] = true;
            this.bSv[n16] = true;
            gL.glBlendFunc(this.bSs[n15], this.bSs[n16]);
        }
        if (!this.bSv[n11 = gf_1.bbQ.ordinal()]) {
            this.bSv[n11] = true;
            if (this.bSs[n11] == 1) {
                gL.glEnable(2832);
            } else {
                gL.glDisable(2832);
            }
        }
        if (!this.bSv[n10 = gf_1.bbT.ordinal()]) {
            this.bSv[n10] = true;
            if (this.bSs[n10] == 1) {
                gL.glEnable(2848);
            } else {
                gL.glDisable(2848);
            }
        }
        if (!this.bSv[n9 = gf_1.bbP.ordinal()]) {
            this.bSv[n9] = true;
            gL.glActiveTexture(33984);
            gL.glTexEnvf(8960, 8704, 34160.0f);
            gL.glTexEnvf(8960, 34161, 8448.0f);
            gL.glTexEnvf(8960, 34176, 5890.0f);
            gL.glTexEnvf(8960, 34163, this.bSu[n9]);
        }
        if (!this.bSv[n8 = gf_1.bbR.ordinal()]) {
            this.bSv[n8] = true;
            gL.glPointSize(this.bSu[n8]);
        }
        if (!this.bSv[n7 = gf_1.bbS.ordinal()]) {
            this.bSv[n7] = true;
            gL.glLineWidth(this.bSu[n7]);
        }
        if (!this.bSv[n6 = gf_1.bbX.ordinal()]) {
            this.bSv[n6] = true;
            gL.glMatrixMode(this.bSs[n6]);
        }
        if (!this.bSv[n5 = gf_1.bbU.ordinal()]) {
            this.bSv[n5] = true;
            if (this.bSs[n5] == 1) {
                gL.glEnable(2852);
            } else {
                gL.glDisable(2852);
            }
        }
        int n17 = gf_1.bbV.ordinal();
        int n18 = gf_1.bbW.ordinal();
        if (!this.bSv[n17] || !this.bSv[n18]) {
            this.bSv[n17] = true;
            this.bSv[n18] = true;
            gL.glLineStipple(this.bSs[n17], this.bSt[n18]);
        }
        if (!this.bSv[n4 = gf_1.bbY.ordinal()]) {
            this.bSv[n4] = true;
            if (this.bSs[n4] == 1) {
                gL.glEnable(3089);
                if (qp_22.LW()) {
                    qp_22.cO(0);
                }
            } else {
                gL.glDisable(3089);
            }
        }
        int n19 = gf_1.bbZ.ordinal();
        int n20 = gf_1.bca.ordinal();
        int n21 = gf_1.bcb.ordinal();
        int n22 = gf_1.bcc.ordinal();
        if (!(this.bSv[n19] && this.bSv[n20] && this.bSv[n21] && this.bSv[n22])) {
            this.bSv[n19] = true;
            this.bSv[n20] = true;
            this.bSv[n21] = true;
            this.bSv[n22] = true;
            gL.glScissor(this.bSs[n19], this.bSs[n20], this.bSs[n21], this.bSs[n22]);
            if (db_22.LW()) {
                db_22.cO(0);
            }
        }
        if (!this.bSv[n3 = gf_1.bcd.ordinal()]) {
            this.bSv[n3] = true;
            if (this.bSs[n3] == 1) {
                gL.glEnable(2960);
            } else {
                gL.glDisable(2960);
            }
        }
        if (!this.bSv[n2 = gf_1.bce.ordinal()]) {
            this.bSv[n2] = true;
            gL.glStencilMask(this.bSs[n2]);
        }
    }

    public void push() {
        this.bSw = true;
        System.arraycopy(this.bSs, 0, this.bSo, 0, this.bSs.length);
        System.arraycopy(this.bSu, 0, this.bSq, 0, this.bSu.length);
        System.arraycopy(this.bSt, 0, this.bSp, 0, this.bSt.length);
        System.arraycopy(this.bSv, 0, this.bSr, 0, this.bSv.length);
    }

    public void pop() {
        if (this.bSw) {
            System.arraycopy(this.bSo, 0, this.bSs, 0, this.bSs.length);
            System.arraycopy(this.bSq, 0, this.bSu, 0, this.bSu.length);
            System.arraycopy(this.bSp, 0, this.bSt, 0, this.bSt.length);
            System.arraycopy(this.bSr, 0, this.bSv, 0, this.bSv.length);
            this.bSw = false;
        }
    }

    public void reset() {
        for (int j = 0; j < this.bSv.length; ++j) {
            this.bSv[j] = false;
        }
    }

    private vo_1() {
        gf_1[] gf_1Array = gf_1.values();
        int n2 = gf_1Array.length;
        this.bSs = new int[n2];
        this.bSu = new float[n2];
        this.bSt = new short[n2];
        this.bSv = new boolean[n2];
        this.bSo = new int[n2];
        this.bSq = new float[n2];
        this.bSp = new short[n2];
        this.bSr = new boolean[n2];
        for (gf_1 gf_12 : gf_1Array) {
            this.bSs[gf_12.ordinal()] = 0;
            this.bSu[gf_12.ordinal()] = 0.0f;
            this.bSt[gf_12.ordinal()] = 0;
            this.bSv[gf_12.ordinal()] = false;
        }
        this.bSw = false;
    }
}

