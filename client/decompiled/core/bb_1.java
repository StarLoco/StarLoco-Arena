/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from BB
 */
class bb_1
extends tj_0 {
    private float aIW;
    private float aIX;
    final /* synthetic */ ahr_2 za;

    public bb_1(ahr_2 ahr_22, float f, float f2, adg_2 adg_22, int n2, int n3, ys ys2) {
        this.za = ahr_22;
        super(null, null, adg_22, n2, n3, ys2);
        this.aIW = f;
        this.aIX = f2;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            ahr_2.a(this.za, this.amA.b(this.aIW, this.aIX, this.IP, this.wg));
            ahr_2.e(this.za).setZoom(ahr_2.d(this.za));
        }
        return true;
    }

    public void ly() {
        ahr_2.a(this.za, this.aIX);
        ahr_2.e(this.za).setZoom(ahr_2.d(this.za));
        super.ly();
    }
}

