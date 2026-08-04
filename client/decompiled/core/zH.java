/*
 * Decompiled with CFR 0.152.
 */
public class zH
extends tj_0 {
    final /* synthetic */ afQ aFM;

    public zH(afQ afQ2, float f, float f2, afQ afQ3, int n2, int n3, ys ys2) {
        this.aFM = afQ2;
        super(Float.valueOf(f), Float.valueOf(f2), afQ3, n2, n3, ys2);
    }

    public boolean aS(int n2) {
        float f;
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null && (f = this.amA.b(((Float)this.eoN).floatValue(), ((Float)this.eoO).floatValue(), this.IP, this.wg)) <= afQ.a(this.aFM)) {
            afQ.a(this.aFM, f);
        }
        return true;
    }
}

