/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Hp
 */
public abstract class hp_2 {
    private int[] beh;

    public static hp_2 aC(short s) {
        if (s == alj.cEY.tI()) {
            return new th_0();
        }
        if (s == alj.cEZ.tI()) {
            return new po_2();
        }
        return null;
    }

    public int[] SR() {
        return this.beh;
    }

    public void r(int[] nArray) {
        this.beh = nArray;
    }

    public abstract short getType();

    public abstract void ut();
}

