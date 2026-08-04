/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atd
 */
public abstract class atd_0
extends aur_0 {
    private ClassLoader cSO;
    private gn_2 cSP;

    protected boolean aFU() {
        return this.cSP != null;
    }

    public void H(boolean bl2) {
        this.aFY().H(bl2);
        this.l("The reverseloader attribute is DEPRECATED. It will be removed", 1);
    }

    public bk_2 jC() {
        return this.aFY().jC();
    }

    public boolean jD() {
        return this.aFY().jD();
    }

    public String aFV() {
        return this.aFY().jB();
    }

    public String aFW() {
        return this.aFY().jB();
    }

    public void e(bk_2 bk_22) {
        this.aFY().e(bk_22);
    }

    public bk_2 jz() {
        return this.aFY().jz();
    }

    public void d(awq_0 awq_02) {
        this.aFY().b(awq_02);
    }

    public void c(awq_0 awq_02) {
        this.aFY().c(awq_02);
    }

    protected ClassLoader aFX() {
        if (this.aHH() != null && this.cSP == null) {
            return this.aHH();
        }
        if (this.cSO == null) {
            this.cSO = this.aFY().getClassLoader();
            ((ny_1)this.cSO).ba("org.apache.tools.ant");
        }
        return this.cSO;
    }

    public void init() {
        super.init();
    }

    private gn_2 aFY() {
        if (this.cSP == null) {
            this.cSP = awK.a(this);
        }
        return this.cSP;
    }
}

