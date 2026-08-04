/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aob
 */
class aob_0
extends ff_2 {
    private final ms_2[] cKA;
    private final asn[] cKB;
    private final zh_2 rW;

    aob_0(zh_2 zh_22, asn asn2, ms_2[] ms_2Array, asn[] asnArray) {
        asn asn3 = asn2;
        asn3.getClass();
        super(asn3);
        this.rW = zh_22;
        this.cKA = ms_2Array;
        this.cKB = asnArray;
    }

    public boolean isStatic() {
        return true;
    }

    public boolean isAbstract() {
        return false;
    }

    public asn ix() {
        return asn.cRB;
    }

    public String getName() {
        return ((ff_2)this.cKA[0]).getName();
    }

    public amf ib() {
        return amf.cGt;
    }

    public asn[] iy() {
        return this.cKB;
    }

    public asn[] iz() {
        return new asn[0];
    }
}

