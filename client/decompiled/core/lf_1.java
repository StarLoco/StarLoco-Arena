/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Lf
 */
public class lf_1
extends tj_0 {
    private float bpX;
    private float bpY;
    private float bpZ;
    private float bqa;
    private float bqb;
    private float bqc;
    final /* synthetic */ ana_0 yj;

    public lf_1(ana_0 ana_02, float f, float f2, float f3, float f4, adg_2 adg_22, int n2, int n3, ys ys2) {
        this.yj = ana_02;
        super(true, false, adg_22, n2, n3, ys2);
        this.bpX = f;
        this.bpZ = f3;
        this.bpY = f2;
        this.bqa = f4;
        int n4 = -(ana_0.a(ana_02) - ana_02.cLZ.getContentWidth());
        this.bqb = (float)ana_02.cwV / (float)(n4 == 0 ? 1 : n4);
        int n5 = ana_0.b(ana_02) - ana_02.cLZ.getContentHeight();
        this.bqc = (float)ana_02.cwW / (float)(n5 == 0 ? 1 : n5);
    }

    public boolean aS(int n2) {
        super.aS(n2);
        if (this.amA != null) {
            boolean bl2 = (Boolean)this.eoN;
            if (!bl2) {
                this.bqb = this.bpX;
                this.bqc = this.bpZ;
            }
            float f = bl2 ? this.bqb : this.bpY;
            float f2 = bl2 ? this.bpY : this.bqb;
            float f3 = bl2 ? this.bqc : this.bqa;
            float f4 = bl2 ? this.bqa : this.bqc;
            float f5 = this.amA.b(f, f2, this.IP, this.wg);
            float f6 = this.amA.b(f3, f4, this.IP, this.wg);
            this.yj.setDeltaX((int)((float)(-(ana_0.a(this.yj) - this.yj.cLZ.getContentWidth())) * f5));
            this.yj.setDeltaY((int)((float)(ana_0.b(this.yj) - this.yj.cLZ.getContentHeight()) * f6));
        }
        return true;
    }

    public void ly() {
        super.ly();
        ana_0.a(this.yj, null);
    }
}

