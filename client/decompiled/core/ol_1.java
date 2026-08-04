/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ol
 */
class ol_1
extends ff_2 {
    private final String UM;
    private final asn UR;
    private final adz US;
    private final asn[] UT;
    private final asn[] UU;
    private final amf UP;
    private final yy_2 UQ;

    ol_1(yy_2 yy_22, String string, asn asn2, adz adz2, asn[] asnArray, asn[] asnArray2, amf amf2) {
        super(yy_22);
        this.UQ = yy_22;
        this.UM = string;
        this.UR = asn2;
        this.US = adz2;
        this.UT = asnArray;
        this.UU = asnArray2;
        this.UP = amf2;
    }

    public String getName() {
        return this.UM;
    }

    public asn ix() {
        return this.UR;
    }

    public boolean isStatic() {
        return (this.US.asX() & 8) != 0;
    }

    public boolean isAbstract() {
        return (this.US.asX() & 0x400) != 0;
    }

    public asn[] iy() {
        return this.UT;
    }

    public asn[] iz() {
        return this.UU;
    }

    public amf ib() {
        return this.UP;
    }
}

