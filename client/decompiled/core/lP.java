/*
 * Decompiled with CFR 0.152.
 */
public class lP
extends aNH
implements ri_0 {
    private static final jq HY = new rl_1();
    private static final jq HZ = new re_1();
    private static final jq Ia = new rh_2();
    private static final jq Ib = new ri_2();
    protected float[] Ic;
    protected int Id;
    protected Du Ie;

    protected lP() {
        this.v(this.cMH);
    }

    protected lP(agv_0 agv_02) {
        super(agv_02);
        this.v(this.cMH);
    }

    protected lP(agv_0 agv_02, float f) {
        super(agv_02, f);
        this.v(this.cMH);
    }

    protected lP(Du du, float f) {
        this.Ie = du;
        this.cMH = f;
        this.v(this.cMH);
    }

    protected lP(Du du) {
        this(du, 3.0f);
        this.OD = true;
        this.v(this.cMH);
    }

    public Du qF() {
        return this.Ie;
    }

    public void a(Du du) {
        this.Ie = du;
    }

    private void v(float f) {
        int n2 = (int)Math.floor(f + 1.0f);
        if (n2 == this.Id) {
            return;
        }
        this.Id = n2;
        this.Ic = new float[this.Id * this.Id * 4];
        float f2 = 1.0f / (this.dZK[0] + this.dZK[1] * this.cMH + this.dZK[2] * this.cMH * this.cMH);
        agw_1 agw_12 = new agw_1(0.0f, 0.0f);
        for (int j = 0; j < this.Id * 2; ++j) {
            for (int i2 = 0; i2 < this.Id * 2; ++i2) {
                int n3 = i2 - this.Id;
                int n4 = j - this.Id;
                agw_12.k(n3, n4);
                float f3 = agw_12.aSy();
                this.Ic[i2 + j * this.Id * 2] = f3 == 0.0f ? 1.0f : (f3 > this.cMH ? 0.0f : Math.max(0.0f, 1.0f - f3 * f2));
            }
        }
    }

    public void u(float f) {
        if (f == this.cMH) {
            return;
        }
        super.u(f);
        this.v(this.cMH);
    }

    private static jq I(int n2, int n3) {
        if (n2 == 0) {
            if (n3 == 0) {
                return HY;
            }
            return Ia;
        }
        if (n3 == 0) {
            return HZ;
        }
        return Ib;
    }

    public void a(ajf_1 ajf_12) {
        agf_0 agf_02 = ajf_12.anU();
        agv_0 agv_02 = this.qG();
        float f = agv_02.getX();
        float f2 = agv_02.getY();
        float f3 = agv_02.id();
        int n2 = (int)Math.floor(f);
        int n3 = (int)Math.floor(f2);
        int n4 = (int)Math.floor(f3);
        int n5 = (int)Math.ceil(this.cMH);
        if (!agf_02.A(n2 - n5, n2 + n5, n3 - n5, n3 + n5)) {
            return;
        }
        float f4 = (float)n2 - f;
        float f5 = (float)n3 - f2;
        int n6 = (int)Math.signum(f4);
        int n7 = (int)Math.signum(f5);
        jq jq2 = lP.I(n6, n7);
        jq2.a(f4, f5, n6, n7);
        int n8 = this.Id * 2;
        for (int j = 0; j < n8; ++j) {
            int n9 = n3 + j - this.Id;
            for (int i2 = 0; i2 < n8; ++i2) {
                int n10 = n2 + i2 - this.Id;
                float f6 = this.Ic[i2 + j * n8];
                float f7 = jq2.a(i2, j, f6, this.Ic, n8);
                ajf_12.a(n10, n9, n4, (f7 *= this.aHh) * this.dZI.Cp(), f7 * this.dZI.Cq(), f7 * this.dZI.Cr(), f7 * this.dZJ.Cp(), f7 * this.dZJ.Cq(), f7 * this.dZJ.Cr());
            }
        }
    }

    public agv_0 qG() {
        if (this.Ie != null) {
            return new agv_0((float)this.Ie.getWorldX(), (float)this.Ie.getWorldY(), 0.0f);
        }
        return super.qG();
    }

    public void c(agv_0 agv_02) {
        if (this.Ie != null) {
            throw new RuntimeException("La source est attach\u00e9e \u00e0 une cible, on ne changera pas la position de la cible \u00e0 travers la source.");
        }
        super.c(agv_02);
    }
}

