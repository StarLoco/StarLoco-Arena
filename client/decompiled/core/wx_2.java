/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wx
 */
public abstract class wx_2
extends gi_2 {
    abc_2 auG = null;

    protected abstract String e(tz_0 var1);

    public void start() {
        String string = this.aqI();
        if (string != null) {
            try {
                int n2 = Integer.parseInt(string);
                if (n2 == 0) {
                    this.auG = new ln_1();
                } else if (n2 > 0) {
                    this.auG = new bj(n2);
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
    }

    public String b(tz_0 tz_02) {
        String string = this.e(tz_02);
        if (this.auG == null) {
            return string;
        }
        return this.auG.q(string);
    }
}

