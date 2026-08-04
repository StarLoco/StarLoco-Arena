/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from gn
 */
public class gn_2 {
    private final aat_0 sJ;
    private bk_2 sK;
    private String sL;
    private String className;
    private String sM;
    private boolean sN = false;

    gn_2(aat_0 aat_02) {
        this.sJ = aat_02;
    }

    public void e(bk_2 bk_22) {
        if (this.sK == null) {
            this.sK = bk_22;
        } else {
            this.sK.b(bk_22);
        }
    }

    public bk_2 jz() {
        if (this.sK == null) {
            this.sK = new bk_2(this.sJ.TP());
        }
        return this.sK.dB();
    }

    public void setClassname(String string) {
        this.className = string;
    }

    public void b(awq_0 awq_02) {
        this.sL = awq_02.aJC();
        this.jz().a(awq_02);
    }

    public void H(boolean bl2) {
        this.sN = bl2;
    }

    public void c(awq_0 awq_02) {
        this.sM = awq_02.aJC();
    }

    public ClassLoader getClassLoader() {
        return awK.a(this.jA(), this.sK, this.jB(), this.sN, this.sM != null || awK.R(this.jA()));
    }

    private UI jA() {
        return this.sJ.TP();
    }

    public String jB() {
        return this.sM == null && this.sL != null ? "ant.loader." + this.sL : this.sM;
    }

    public Object newInstance() {
        return awK.a(this.className, this.getClassLoader());
    }

    public bk_2 jC() {
        return this.sK;
    }

    public boolean jD() {
        return this.sN;
    }
}

