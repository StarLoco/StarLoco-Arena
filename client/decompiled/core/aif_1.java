/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIF
 */
final class aif_1
implements zD {
    private final lb_0 dQe;

    aif_1(lb_0 lb_02) {
        this.dQe = lb_02;
    }

    public final boolean b(int n2, Object object) {
        int n3 = this.dQe.hJ(n2);
        return n3 >= 0 && this.eq(object, this.dQe.get(n2));
    }

    private final boolean eq(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }
}

