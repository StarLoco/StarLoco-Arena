/*
 * Decompiled with CFR 0.152.
 */
public abstract class aFH
extends ags_1 {
    private nf_2 dHy = nf_2.NM;
    protected yb_0 dHz;
    private int dHA = 0;
    private int dHB = 0;
    protected BP aCS = null;

    public abstract int CX();

    public nf_2 aRY() {
        return this.dHy;
    }

    protected void a(nf_2 nf_22) {
        this.dHy = nf_22;
    }

    public yb_0 De() {
        return this.dHz;
    }

    public void c(yb_0 yb_02) {
        this.dHz = yb_02;
    }

    public int getStartIndex() {
        return this.dHA;
    }

    public void os(int n2) {
        this.dHA = n2;
    }

    public int getEndIndex() {
        return this.dHB;
    }

    public void setEndIndex(int n2) {
        this.dHB = n2;
    }

    public BP Fi() {
        if (this.aCS == null && this.dHz != null) {
            return this.dHz.Fi();
        }
        return this.aCS;
    }

    public void a(BP bP) {
        this.aCS = bP;
    }

    public abstract int a(af_1 var1, int var2);

    public abstract int b(af_1 var1, int var2);

    public abstract int c(af_1 var1, int var2);
}

