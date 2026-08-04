/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yu
 */
class yu_0
extends tj_0 {
    private boolean cX;
    final /* synthetic */ apc_1 aCu;

    public yu_0(apc_1 apc_12) {
        this.aCu = apc_12;
        this.cX = false;
    }

    public yu_0(apc_1 apc_12, float f, float f2, apc_1 apc_13, int n2, int n3, ys ys2) {
        this.aCu = apc_12;
        super(Float.valueOf(f), Float.valueOf(f2), apc_13, n2, n3, ys2);
        this.cX = false;
    }

    public void d(float f, float f2) {
        if (this.cX) {
            this.wg += 500;
            this.eoO = Float.valueOf(f2);
        }
        this.cX = true;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null && this.eoN instanceof Float && this.eoO instanceof Float) {
            apc_1.a(this.aCu, this.amA.b(((Float)this.eoN).floatValue(), ((Float)this.eoO).floatValue(), this.IP, this.wg));
            apc_1.a(this.aCu);
        }
        return true;
    }

    public void ly() {
        apc_1.a(this.aCu, ((Float)this.eoO).floatValue());
        apc_1.a(this.aCu);
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ProgressBarTween] ").append(this.eoN).append(" -> ").append(this.eoO);
        return stringBuilder.toString();
    }
}

