/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aoV
 */
public abstract class aov_0 {
    protected short EL;
    protected short EM;

    public aov_0() {
        this(0, 0);
    }

    public aov_0(short s, short s2) {
        this.EL = s;
        this.EM = s2;
    }

    public final short pi() {
        return this.EL;
    }

    public final short pj() {
        return this.EM;
    }

    public final void u(short s, short s2) {
        this.EL = s;
        this.EM = s2;
    }

    public final boolean F(int n2, int n3) {
        int n4 = this.EL * 18;
        int n5 = this.EM * 18;
        return n2 >= n4 && n2 < n4 + 18 && n3 >= n5 && n3 < n5 + 18;
    }

    public abstract void clear();

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/AbstractClientEnvironmentMap.load must not be null");
        }
        this.EL = acf2.readShort();
        this.EM = acf2.readShort();
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/AbstractClientEnvironmentMap.save must not be null");
        }
        aij_12.writeShort(this.EL);
        aij_12.writeShort(this.EM);
    }

    public final int lI(int n2) {
        assert (n2 >= 0 && n2 < 18);
        return n2 + this.EL * 18;
    }

    public final int lJ(int n2) {
        assert (n2 >= 0 && n2 < 18);
        return n2 + this.EM * 18;
    }
}

