/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFh
 */
public class afh_1 {
    private static afh_1 dGh = new afh_1();
    private akw_0[] dGi = new akw_0[0];
    private zm_1 dGj = new zm_1();

    public static afh_1 aRG() {
        return dGh;
    }

    public void a(mw_0 mw_02) {
        this.dGj.b(mw_02.YF(), mw_02);
    }

    public byte cn(short s) {
        mw_0 mw_02 = (mw_0)this.dGj.an(s);
        if (mw_02 != null) {
            return mw_02.YG();
        }
        return 0;
    }

    public akw_0[] co(short s) {
        mw_0 mw_02 = (mw_0)this.dGj.an(s);
        if (mw_02 != null) {
            return mw_02.tu();
        }
        return this.dGi;
    }
}

