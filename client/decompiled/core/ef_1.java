/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ef
 */
public abstract class ef_1
extends aon_2 {
    protected long aPQ;
    protected String eA;
    protected boolean aPR;
    protected boolean aPS;
    protected qh aPT;
    protected boolean aPU;
    protected boolean aPV;
    protected boolean aPW;
    protected int aPX;
    private static final pw aPY = pw.acb;

    public ef_1(long l2, String string, boolean bl2) {
        this.b(l2, string, bl2);
        this.aPV = false;
    }

    public ef_1(long l2, aon_2 aon_22, boolean bl2) {
        super(aon_22);
        this.b(l2, null, bl2);
        this.aPV = false;
    }

    public ef_1(long l2, int n2, int n3, boolean bl2) {
        super(aPY, new kf_0(n2, n3, 32, null, null));
        this.b(l2, null, false);
        this.aPV = true;
        this.aPW = bl2;
    }

    public final long MI() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.aPQ;
    }

    public final String getFileName() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.eA;
    }

    public final boolean load(String string) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.iN(string.concat(this.eA));
    }

    public final boolean bh(boolean bl2) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        if (bl2) {
            return this.iN(this.eA);
        }
        return this.iO(this.eA);
    }

    public final boolean MJ() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.aPR;
    }

    public final void bi(boolean bl2) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        this.aPR = bl2;
    }

    public void a(int n2, kf_0 kf_02) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        super.a(n2, kf_02);
        this.aPS = false;
    }

    public final boolean is() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.aPS && !this.tY;
    }

    public final void fu() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        for (int j = 0; j < this.aCH(); ++j) {
            kf_0 kf_02 = this.lB(j);
            if (kf_02 == cKO) continue;
            kf_02.setData(null);
        }
    }

    public final qh MK() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.aPT;
    }

    public final void a(qh qh2) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        this.aPT = qh2;
    }

    public final boolean ML() {
        return this.aPV;
    }

    public abstract boolean e(db_2 var1);

    public abstract void f(db_2 var1);

    public abstract void g(db_2 var1);

    public abstract void h(db_2 var1);

    public abstract void i(db_2 var1);

    public abstract boolean isCompressed();

    public abstract boolean j(db_2 var1);

    public float MM() {
        float f = (float)(ej_0.aq(this.lC(0).getX()) * ej_0.aq(this.lC(0).getY()) * 4) / 1024.0f;
        if (this.aPW) {
            f *= 2.0f;
        }
        if (this.isCompressed()) {
            f /= 4.0f;
        }
        return f;
    }

    public int MN() {
        int n2 = 0;
        if (this.aPX == 0) {
            n2 = 32;
        } else if (this.aPX < 256) {
            n2 = (int)((double)n2 + (32.0 - 4.0 * Math.log(this.aPX)));
        }
        n2 = this.avc() < -1000 ? (n2 += 34) : (n2 += -this.avc() * 34 / 1000);
        float f = this.MM();
        n2 = f >= 1024.0f ? (n2 += 34) : (int)((float)n2 + f * 34.0f / 1024.0f);
        return n2;
    }

    private void b(long l2, String string, boolean bl2) {
        this.aPQ = l2;
        this.eA = string;
        this.aPR = false;
        this.aPT = qh.adA;
        this.aPU = bl2;
    }
}

