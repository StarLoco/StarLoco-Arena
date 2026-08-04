/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anY
 */
class any_0
extends ff_2 {
    private final String UM;
    private final asn[] cKz;
    private final zh_2 rW;

    any_0(zh_2 zh_22, asn asn2, String string, asn[] asnArray) {
        asn asn3 = asn2;
        asn3.getClass();
        super(asn3);
        this.rW = zh_22;
        this.UM = string;
        this.cKz = asnArray;
    }

    public String getName() {
        return this.UM;
    }

    public asn ix() {
        return asn.cRB;
    }

    public boolean isStatic() {
        return false;
    }

    public boolean isAbstract() {
        return false;
    }

    public asn[] iy() {
        return this.cKz;
    }

    public asn[] iz() {
        return new asn[0];
    }

    public amf ib() {
        return amf.cGt;
    }
}

