/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aLK
 */
public abstract class alk_2 {
    private final cp_2 dWi = new cp_2();
    private final kl_1 dWj = new kl_1();

    protected final void a(long l2, short s, byte[] byArray) {
        this.dWj.h(l2, s);
        this.dWi.a(l2, byArray);
    }

    public final boolean eI(long l2) {
        return this.dWj.v(l2);
    }

    public final short eJ(long l2) {
        return this.dWj.bU(l2);
    }

    public final byte[] eK(long l2) {
        return (byte[])this.dWi.t(l2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[Definitions for ");
        for (long l2 : this.dWj.eJ()) {
            stringBuilder.append(l2).append(" ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

