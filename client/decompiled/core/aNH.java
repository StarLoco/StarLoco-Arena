/*
 * Decompiled with CFR 0.152.
 */
public abstract class aNH {
    private static int dZH = 1;
    protected int aW = 0;
    protected ji_1 dZI = new ji_1(ji_1.bmb);
    protected ji_1 dZJ = new ji_1(ji_1.bmc);
    protected float cMH = 3.0f;
    protected final float[] dZK = new float[]{0.0f, 1.0f, 0.0f};
    protected agv_0 dVw;
    protected boolean dZL;
    protected float aHh = 1.0f;
    protected boolean OD = true;
    protected boolean dZM;
    protected long dZN = 0L;
    protected long dZO = 0L;
    private akt_2 dZP;

    protected aNH() {
        this.aW = dZH++;
        this.dVw = new agv_0(0.0f, 0.0f, 0.0f);
    }

    public aNH(agv_0 agv_02) {
        this(agv_02, 3.0f);
    }

    public aNH(agv_0 agv_02, float f) {
        this();
        this.dVw = agv_02;
        this.cMH = f;
    }

    public int getId() {
        return this.aW;
    }

    public void q(float f, float f2, float f3) {
        this.dZK[0] = f;
        this.dZK[1] = f2;
        this.dZK[2] = f3;
    }

    public float Ct() {
        return this.aHh;
    }

    public void V(float f) {
        this.aHh = f;
    }

    public boolean aXA() {
        return this.dZL;
    }

    public void fp(boolean bl2) {
        this.dZL = bl2;
    }

    public void ay(long l2) {
        if (this.dZI.jU()) {
            this.dZI.ay(l2);
        }
        if (this.dZJ.jU()) {
            this.dZJ.ay(l2);
        }
        if (this.dZO > 0L && this.dZN > 0L && l2 > this.dZN + this.dZO) {
            this.dZO = 0L;
            this.dZN = 0L;
            this.dZM = true;
            if (this.dZP != null) {
                this.dZP.azS();
            }
        }
    }

    public float aXB() {
        return this.dZI.Cp();
    }

    public float aXC() {
        return this.dZI.Cq();
    }

    public float aXD() {
        return this.dZI.Cr();
    }

    public float aXE() {
        return this.dZJ.Cp();
    }

    public float aXF() {
        return this.dZJ.Cq();
    }

    public float aXG() {
        return this.dZJ.Cr();
    }

    public void r(float f, float f2, float f3) {
        this.dZI.d(f, f2, f3);
    }

    public void s(float f, float f2, float f3) {
        this.dZJ.d(f, f2, f3);
    }

    public void t(float f, float f2, float f3) {
        this.dZJ.d(f, f2, f3);
    }

    public void b(float f, float f2, float f3, long l2, long l3) {
        this.dZI.a(f, f2, f3, l2, l3);
    }

    public void c(float f, float f2, float f3, long l2, long l3) {
        this.dZJ.a(f, f2, f3, l2, l3);
    }

    public agv_0 qG() {
        return this.dVw;
    }

    public void c(agv_0 agv_02) {
        this.dVw.j(agv_02);
    }

    public float aXH() {
        return this.cMH;
    }

    public void u(float f) {
        this.cMH = f;
    }

    public ji_1 aXI() {
        return this.dZI;
    }

    public void a(ji_1 ji_12) {
        this.dZI = ji_12;
    }

    public ji_1 aXJ() {
        return this.dZJ;
    }

    public void b(ji_1 ji_12) {
        this.dZJ = ji_12;
    }

    public boolean isEnabled() {
        return this.OD;
    }

    public void setEnabled(boolean bl2) {
        this.OD = bl2;
    }

    public void p(long l2, long l3) {
        if (this.OD) {
            this.dZN = l2;
            this.dZO = l3;
            this.b(0.0f, 0.0f, 0.0f, l2, l3);
            this.c(0.0f, 0.0f, 0.0f, l2, l3);
        } else {
            this.dZM = true;
        }
    }

    public boolean isShutdown() {
        return this.dZM;
    }

    public akt_2 aXK() {
        return this.dZP;
    }

    public void a(akt_2 akt_22) {
        this.dZP = akt_22;
    }
}

