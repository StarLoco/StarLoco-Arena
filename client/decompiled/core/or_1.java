/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from OR
 */
public abstract class or_1 {
    protected static final Logger a = Logger.getLogger(or_1.class);
    protected final avE bCB;
    protected final qq_1 bCC;
    private boolean bCD = false;
    protected final float bCE;
    private int bCF = 0;
    private int bCG = 0;
    private boolean aPS = false;
    private long bCH = -1L;
    private boolean bCI = false;
    private boolean bCJ = false;
    private boolean bCK = false;
    private float bCL = 0.0f;
    private float bCM = -1.0f;
    private static final agv_0 bCN = new agv_0();

    public static or_1 a(avE avE2, qq_1 qq_12, float f, boolean bl2, boolean bl3, float f2) {
        if (bl2) {
            return new tf_1(avE2, qq_12, f, bl3, f2);
        }
        return new yj_2(avE2, qq_12, f, bl3, f2);
    }

    public or_1(avE avE2, qq_1 qq_12, float f, boolean bl2, float f2) {
        this.bCB = avE2;
        this.bCC = qq_12;
        this.bCE = f;
        this.bCI = false;
        this.bCJ = true;
        this.bCK = bl2;
        this.bCL = f2;
    }

    public void aD(int n2, int n3) {
        this.bCF = n2 * 1000;
        this.bCG = n3 * 1000;
        long l2 = System.currentTimeMillis();
        this.bCH = l2 + (long)ej_0.am(this.bCG - this.bCF) + (long)this.bCF;
    }

    public avE abF() {
        return this.bCB;
    }

    public void a(agv_0 agv_02, float f, boolean bl2, int n2) {
        if (bl2) {
            this.aPS = bl2;
        } else if (!this.aPS) {
            return;
        }
        if (this.bCM == -1.0f) {
            this.bCM = this.bCB.getGain();
        }
        bCN.d(this.bCC.zR(), this.bCC.zS(), 0.0f);
        agv_0 agv_03 = this.bCC.zT() ? bCN : bCN.n(agv_02);
        float f2 = agv_03.aSz();
        if (this.bCK && f2 > this.bCE) {
            this.ca(true);
            return;
        }
        this.bCB.mz(this.bCC.zU());
        if (f2 <= this.bCE) {
            this.bCI = false;
        } else if (f2 > this.bCE) {
            if (!this.bCI) {
                this.bCI = true;
                if (this.bCB.aIW()) {
                    this.bCB.ev(true);
                    this.bCB.j(0.0f, this.bCL);
                }
            }
            if (this.bCJ) {
                return;
            }
        }
        long l2 = System.currentTimeMillis();
        if (this.bCH == -1L) {
            if (this.bCJ || !this.bCB.aIX()) {
                try {
                    if (this.bCJ) {
                        if (!this.bCB.aIQ()) {
                            this.ca(true);
                            return;
                        }
                        this.bCB.setGain(0.0f);
                        this.bCB.j(this.bCM, this.bCL);
                        this.bCJ = false;
                    }
                    this.bCB.play();
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
            this.e(agv_03);
            switch (this.bCB.dW(l2)) {
                case 1: {
                    this.ca(true);
                    break;
                }
                case 3: {
                    this.bCJ = true;
                }
            }
        } else if (this.bCH < l2) {
            if (this.bCJ || !this.bCB.aIX()) {
                try {
                    if (this.bCJ) {
                        if (!this.bCB.aIQ()) {
                            this.ca(true);
                            return;
                        }
                        this.bCB.setGain(0.0f);
                        this.bCB.j(this.bCM, this.bCL);
                        this.bCJ = false;
                    }
                    this.bCB.play();
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
            this.e(agv_03);
            switch (this.bCB.dW(l2)) {
                case 1: {
                    this.bCH = l2 + (long)ej_0.am(this.bCG - this.bCF) + (long)this.bCF;
                    break;
                }
                case 3: {
                    this.bCJ = true;
                }
            }
        }
    }

    public boolean abG() {
        return this.bCD;
    }

    public void release() {
        this.bCD = true;
    }

    public void ak(float f) {
        this.bCB.ak(f);
    }

    public void al(float f) {
        this.bCB.al(f);
    }

    public void abH() {
        this.bCB.abH();
    }

    public void abI() {
        this.bCB.abI();
    }

    public void aj(float f) {
        this.bCB.aj(f);
    }

    public void setMaxGain(float f) {
        this.bCB.setMaxGain(f);
    }

    public void bZ(boolean bl2) {
        this.bCB.setMute(bl2);
    }

    public void ca(boolean bl2) {
        this.bCD = bl2;
    }

    public abstract void e(agv_0 var1);
}

