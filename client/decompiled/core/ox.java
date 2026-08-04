/*
 * Decompiled with CFR 0.152.
 */
final class ox
implements aDN {
    private final sa_1 aap;

    ox(sa_1 sa_12) {
        this.aap = sa_12;
    }

    public final boolean a(Object object, int n2) {
        int n3 = this.aap.index(object);
        return n3 >= 0 && this.a(n2, this.aap.get(object));
    }

    private final boolean a(int n2, int n3) {
        return n2 == n3;
    }
}

