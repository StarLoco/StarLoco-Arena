/*
 * Decompiled with CFR 0.152.
 */
public class iq
extends tj_0 {
    private int yf;
    private int yg;
    private int yh;
    private int yi;
    final /* synthetic */ ana_0 yj;

    public iq(ana_0 ana_02, int n2, int n3, int n4) {
        this.yj = ana_02;
        super(true, false, ana_02, 0, n4, ys.aCr);
        this.yf = ana_02.cwV;
        this.yh = ana_02.cwW;
        this.yg = n2;
        this.yi = n3;
    }

    public boolean aS(int n2) {
        super.aS(n2);
        if (this.amA != null) {
            int n3 = (int)this.amA.b(this.yf, this.yg, this.IP, this.wg);
            int n4 = (int)this.amA.b(this.yh, this.yi, this.IP, this.wg);
            this.yj.setDeltaX(n3);
            this.yj.setDeltaY(n4);
        }
        return true;
    }

    public void ly() {
        super.ly();
    }
}

