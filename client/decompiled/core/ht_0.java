/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from HT
 */
final class ht_0
implements hm_0 {
    private final zm_1 bfH;

    ht_0(zm_1 zm_12) {
        this.bfH = zm_12;
    }

    public final boolean a(short s, Object object) {
        int n2 = this.bfH.ab(s);
        return n2 >= 0 && this.eq(object, this.bfH.an(s));
    }

    private final boolean eq(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }
}

