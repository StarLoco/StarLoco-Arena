/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Eo
 */
final class eo_0
implements aoU {
    private final cp_2 aQs;

    eo_0(cp_2 cp_22) {
        this.aQs = cp_22;
    }

    public final boolean b(long l2, Object object) {
        int n2 = this.aQs.az(l2);
        return n2 >= 0 && this.eq(object, this.aQs.t(l2));
    }

    private final boolean eq(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }
}

