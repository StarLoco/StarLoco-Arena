/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aqZ
 */
public class aqz_0
extends tj_0 {
    public aqz_0(Float f, Float f2, yt_1 yt_12, int n2, int n3, ys ys2) {
        super(f, f2, yt_12, n2, n3, ys2);
    }

    public yt_1 JA() {
        return (yt_1)super.getWidget();
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            float f = this.amA.b(((Float)this.eoN).floatValue(), ((Float)this.eoO).floatValue(), this.IP, this.wg);
            this.JA().setZoom(f);
        }
        return true;
    }

    public void ly() {
        this.JA().setZoom(((Float)this.eoO).floatValue());
    }
}

