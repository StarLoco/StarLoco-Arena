/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCm
 */
public abstract class acm_1 {
    public static final int dud = 32;
    public static final byte due = -1;
    public static final byte duf = 7;
    public static final byte dug = -1;
    public int aG;
    public int aH;
    public short wp;

    public final boolean F(int n2, int n3) {
        return n2 >= this.aG && n2 < this.aG + 18 && n3 >= this.aH && n3 < this.aH + 18;
    }

    public abstract int a(int var1, int var2, akd_0[] var3, int var4);

    public abstract int a(int var1, int var2, sl_1[] var3, int var4);

    public void b(acf acf2) {
        this.aG = acf2.readShort() * 18;
        this.aH = acf2.readShort() * 18;
        this.wp = acf2.readShort();
    }

    protected final boolean a(int n2, int n3, akd_0[] akd_0Array) {
        assert (akd_0Array != null);
        assert (akd_0Array.length >= 1) : "cellPathData array must have a size at least equal to one";
        assert (akd_0Array[0] != null) : "cellpathData array seems not to be initialized";
        assert (this.F(n2, n3)) : "The cell (" + n2 + ", " + n3 + ") doesn't belong to the map. Make sure that isInMap(x, y) is true before calling getPathData";
        return true;
    }

    protected final boolean a(int n2, int n3, sl_1[] sl_1Array) {
        assert (sl_1Array != null);
        assert (sl_1Array.length >= 1) : "cellVisibilityData array must have a size at least equal to one";
        assert (sl_1Array[0] != null) : "cellVisibilityData array seems not to be initialized";
        assert (this.F(n2, n3)) : "The cell (" + n2 + ", " + n3 + ") doesn't belong to the map. Make sure that isInMap(x, y) is true before calling getVisibilityData";
        return true;
    }
}

