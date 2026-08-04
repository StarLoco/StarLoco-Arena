/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import javax.media.opengl.GL;

/*
 * Renamed from aDO
 */
public final class ado_0
extends wz_1 {
    private jg_0 dyY;
    private el_0[][] dyZ;
    private aua_0[] dza = new aua_0[dzd];
    private int dzb = 66;
    private boolean dzc;
    ef_1 tl;
    private static final int dzd = aee_2.values().length;
    private static final int TYPE_MASK = -268435456;
    private static final int dze = 28;
    private static final ado_0 dzf = new ado_0();

    private ado_0() {
        int n2;
        for (n2 = 0; n2 < this.dza.length; ++n2) {
            this.dza[n2] = new aua_0(this.dzb);
        }
        this.dyZ = new el_0[dzd][];
        for (n2 = 0; n2 < this.dyZ.length; ++n2) {
            this.dyZ[n2] = new el_0[this.dzb];
        }
        for (n2 = 0; n2 < dzd; ++n2) {
            aee_2 aee_22 = aee_2.kh(n2);
            for (int j = 0; j < this.dzb; ++j) {
                this.dyZ[n2][j] = aee_2.a(aee_22);
            }
        }
        this.dyY = new jg_0(this.dzb);
        n2 = 16384;
        this.aA = new VertexBufferPCT(16384);
        this.az = new ams_1(16384);
        for (int j = 0; j < 16384; ++j) {
            this.az.add(j);
        }
        this.dG(true);
    }

    public static ado_0 aPH() {
        return dzf;
    }

    protected boolean CR() {
        return false;
    }

    protected boolean CS() {
        return false;
    }

    protected void a(akk_2 akk_22) {
    }

    protected void a(akk_2 akk_22, float f) {
    }

    public void clear() {
    }

    public final void a(float f, float f2, float f3, float f4, float f5, float f6) {
        int n2 = ej_0.am(dzd);
        this.a(n2, f, f2, f3, f4, f5, f6);
    }

    public final void a(int n2, float f, float f2, float f3, float f4, float f5, float f6) {
        assert (n2 < dzd);
        if (this.dyY.size() >= this.dzb) {
            return;
        }
        int n3 = n2 << 28;
        int n4 = this.dza[n2].aHc();
        this.dyY.add(n3 + n4 + 1);
        this.dyZ[n2][n4].a(f, f2, f3, f4, f5, f6);
    }

    public final void setTexture(ef_1 ef_12) {
        this.tl = ef_12;
        if (this.tl != null) {
            this.tl.HE();
        }
    }

    public final void a(float f) {
        int n2;
        int n3;
        f /= 1000.0f;
        int n4 = this.dyY.size();
        int n5 = 0;
        while (n5 < n4) {
            n3 = this.dyY.bu(n5);
            n2 = (n3 & 0xF0000000) >>> 28;
            el_0 el_02 = this.dyZ[n2][(n3 &= 0xFFFFFFF) - 1];
            if (el_02.isDead()) {
                el_02.reset();
                this.dza[n2].mm(n3 - 1);
                this.dyY.bv(n5);
                --n4;
                continue;
            }
            el_02.a(f);
            ++n5;
        }
        n5 = 0;
        n3 = 0;
        n2 = 0;
        int n6 = 0;
        this.aA.clear();
        block1: for (int j = 0; j < n4; ++j) {
            int n7 = this.dyY.bu(j);
            int n8 = (n7 & 0xF0000000) >>> 28;
            el_0 el_03 = this.dyZ[n8][(n7 &= 0xFFFFFFF) - 1];
            for (int i2 = 0; i2 < el_03.auT; ++i2) {
                lu_0 lu_02 = el_03.aQk[i2];
                if (lu_02.isDead()) continue;
                if (n5 >= 32768) continue block1;
                float f2 = (lu_02.Hk - lu_02.Hl) * 43.0f;
                float f3 = -(lu_02.Hk + lu_02.Hl) * 21.5f + lu_02.Hm * 10.0f;
                ado_0.auO[n5++] = f2 - lu_02.bsE;
                ado_0.auO[n5++] = f3 - lu_02.bsF;
                ado_0.auO[n5++] = f2 - lu_02.bsE;
                ado_0.auO[n5++] = f3 + lu_02.bsF;
                ado_0.auO[n5++] = f2 + lu_02.bsE;
                ado_0.auO[n5++] = f3 + lu_02.bsF;
                ado_0.auO[n5++] = f2 + lu_02.bsE;
                ado_0.auO[n5++] = f3 - lu_02.bsF;
                ado_0.auP[n3++] = lu_02.IQ;
                ado_0.auP[n3++] = lu_02.IR;
                ado_0.auP[n3++] = lu_02.IS;
                ado_0.auP[n3++] = lu_02.IT;
                ado_0.auP[n3++] = lu_02.IQ;
                ado_0.auP[n3++] = lu_02.IR;
                ado_0.auP[n3++] = lu_02.IS;
                ado_0.auP[n3++] = lu_02.IT;
                ado_0.auP[n3++] = lu_02.IQ;
                ado_0.auP[n3++] = lu_02.IR;
                ado_0.auP[n3++] = lu_02.IS;
                ado_0.auP[n3++] = lu_02.IT;
                ado_0.auP[n3++] = lu_02.IQ;
                ado_0.auP[n3++] = lu_02.IR;
                ado_0.auP[n3++] = lu_02.IS;
                ado_0.auP[n3++] = lu_02.IT;
                ado_0.auQ[n2++] = lu_02.bsB;
                ado_0.auQ[n2++] = lu_02.bsD;
                ado_0.auQ[n2++] = lu_02.bsB;
                ado_0.auQ[n2++] = lu_02.bsA;
                ado_0.auQ[n2++] = lu_02.bsC;
                ado_0.auQ[n2++] = lu_02.bsA;
                ado_0.auQ[n2++] = lu_02.bsC;
                ado_0.auQ[n2++] = lu_02.bsD;
                ++n6;
            }
        }
        this.aA.g(auO);
        this.aA.h(auP);
        this.aA.j(auQ);
        this.aA.dz(n6 * 4);
        this.dzc = n6 > 0;
    }

    public final void a(db_2 db_22) {
        if (!this.dzc) {
            return;
        }
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        vo_1.aik().a(air.cyd, air.cya);
        if (this.tl != null) {
            this.tl.f(db_22);
        } else {
            vo_1.aik().cu(false);
        }
        vo_1.aik().n(db_22);
        qp_22.adV.nO(13);
        gL.glVertexPointer(2, 5126, 0, this.aA.ys());
        gL.glColorPointer(4, 5126, 0, this.aA.yt());
        gL.glTexCoordPointer(2, 5126, 0, this.aA.yu());
        gL.glDrawElements(7, this.aA.fq(), 5123, this.az.aWZ());
        this.dzc = false;
    }
}

