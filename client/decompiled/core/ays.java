/*
 * Decompiled with CFR 0.152.
 */
public class ays
extends ZT {
    avx_0 dms;

    private ays() {
        throw new UnsupportedOperationException();
    }

    protected ays(avx_0 avx_02) {
        this.dms = avx_02;
    }

    public ays NO() {
        throw new UnsupportedOperationException();
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(this.dms);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(this.dms);
        }
        super.aK();
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

