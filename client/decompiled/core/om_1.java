/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from om
 */
class om_1
extends adi_0 {
    private final asn[] UT;
    private final asn[] UU;
    private final amf UP;
    private final yy_2 UQ;

    om_1(yy_2 yy_22, asn[] asnArray, asn[] asnArray2, amf amf2) {
        super(yy_22);
        this.UQ = yy_22;
        this.UT = asnArray;
        this.UU = asnArray2;
        this.UP = amf2;
    }

    public asn[] iy() {
        asn asn2 = this.UQ.aFp();
        if (asn2 != null) {
            if (this.UT.length < 1) {
                throw new aHY("Inner class constructor lacks magic first parameter");
            }
            if (this.UT[0] != asn2) {
                throw new aHY("Magic first parameter of inner class constructor has type \"" + this.UT[0].toString() + "\" instead of that of its enclosing instance (\"" + asn2.toString() + "\")");
            }
            asn[] asnArray = new asn[this.UT.length - 1];
            System.arraycopy(this.UT, 1, asnArray, 0, asnArray.length);
            return asnArray;
        }
        return this.UT;
    }

    public asn[] iz() {
        return this.UU;
    }

    public amf ib() {
        return this.UP;
    }
}

