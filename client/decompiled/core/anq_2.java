/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anq
 */
final class anq_2
implements apx {
    private final Object[] cIM;
    private int pos = 0;

    public anq_2(Object[] objectArray) {
        this.cIM = objectArray;
    }

    public final boolean a(Object object) {
        this.cIM[this.pos++] = object;
        return true;
    }
}

