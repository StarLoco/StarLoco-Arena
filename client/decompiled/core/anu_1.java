/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anU
 */
class anu_1
extends ff_2 {
    private final ff_2 cKi;
    private final asn[] cKj;
    private final zh_2 rW;

    anu_1(zh_2 zh_22, asn asn2, ff_2 ff_22, asn[] asnArray) {
        asn asn3 = asn2;
        asn3.getClass();
        super(asn3);
        this.rW = zh_22;
        this.cKi = ff_22;
        this.cKj = asnArray;
    }

    public String getName() {
        return this.cKi.getName();
    }

    public asn ix() {
        return this.cKi.ix();
    }

    public boolean isAbstract() {
        return this.cKi.isAbstract();
    }

    public boolean isStatic() {
        return this.cKi.isStatic();
    }

    public amf ib() {
        return this.cKi.ib();
    }

    public asn[] iy() {
        return this.cKi.iy();
    }

    public asn[] iz() {
        return this.cKj;
    }
}

