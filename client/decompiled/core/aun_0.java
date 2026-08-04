/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from auN
 */
final class aun_0
implements aom_1 {
    private final afj_0 cWT;

    aun_0(afj_0 afj_02) {
        this.cWT = afj_02;
    }

    public final boolean a(byte by, Object object) {
        int n2 = this.cWT.D(by);
        return n2 >= 0 && this.eq(object, this.cWT.bk(by));
    }

    private final boolean eq(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }
}

