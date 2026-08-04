/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNg
 */
public class ang_0
extends ig_1 {
    private static final acl_0 aU = new ym_0(new aDJ());

    private ang_0() {
    }

    public ang_0(fv_1 fv_12, boolean bl2) {
        super(fv_12, bl2);
    }

    public ang_0 aXp() {
        ang_0 ang_02;
        try {
            ang_02 = (ang_0)aU.adr();
            ang_02.uG = aU;
        }
        catch (Exception exception) {
            ang_02 = new ang_0();
            ang_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un HPLeech : " + exception.getMessage()));
        }
        ang_02.a(this);
        return ang_02;
    }

    public void aG() {
        super.aG();
        this.bWt.set(13);
        if (this.bS != null) {
            switch (this.bS) {
                case bal: {
                    break;
                }
                case bap: {
                    this.bWt.set(12);
                    break;
                }
                case bam: {
                    this.bWt.set(9);
                    break;
                }
                case ban: {
                    this.bWt.set(10);
                    break;
                }
                case bao: {
                    this.bWt.set(11);
                }
            }
        }
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl != null && !this.bWl.PR() && !this.bWl.PT()) {
            if (this.bWl.d(Lr.bqx) > 0) {
                this.bWl.a(Lr.bqx).jZ(this.r);
            }
            if (this.bWl != this.bWm && this.bWl.a(Lr.bqx).atR() > this.bWl.a(Lr.bqx).max()) {
                this.bWl.a(Lr.bqx).set(this.bWl.a(Lr.bqx).max());
            }
        }
        this.TY();
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        super.a(xb_22);
        if (this.bWm != null && this.bWm.b(Lr.bqx) && this.bWl != null && this.bWl.b(Lr.bqx)) {
            this.r = Math.min(this.r, this.bWm.a(Lr.bqx).atR());
        } else {
            this.aoy();
        }
    }

    /* synthetic */ ang_0(aDJ aDJ2) {
        this();
    }
}

