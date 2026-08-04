/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Jm
 */
public class jm_2
extends alv {
    public jm_2(int n2, String string, float[] fArray, String string2, boolean bl2) {
        super(n2, string, fArray, string2, bl2);
    }

    public void a(zc_0 zc_02) {
        String string = zc_02.getSourceName();
        if (this.cv(string) == null) {
            this.cu(string);
        }
        super.a(zc_02);
    }

    public void a(zc_0 zc_02, String string) {
        String string2 = zc_02.getSourceName();
        if (this.cv(string2) == null) {
            this.cu(string2);
        }
        super.a(zc_02, string);
    }

    protected void cu(String string) {
        alv alv2 = new alv(-1, "subPipe".concat(string), this.aaV, null, false);
        this.a(string, alv2);
        for (Sz sz : this.zY()) {
            sz.b(alv2, aee_1.dBz);
        }
    }
}

